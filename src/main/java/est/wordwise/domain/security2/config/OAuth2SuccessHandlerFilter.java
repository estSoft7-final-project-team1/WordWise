package est.wordwise.domain.security2.config;

import est.wordwise.common.entity.Member;
import est.wordwise.domain.security2.domain.RefreshToken;
import est.wordwise.domain.security2.dto.KeyPair;
import est.wordwise.domain.security2.dto.OauthMemberDetails;
import est.wordwise.domain.security2.service.JwtTokenProvider;
import est.wordwise.domain.security2.service.OauthMemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandlerFilter extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${custom.jwt.redirection.base}")
    private String baseUrl;

    private final JwtTokenProvider jwtTokenProvider;
    private final OauthMemberService oauthMemberService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        OauthMemberDetails principal = (OauthMemberDetails) authentication.getPrincipal();

        HashMap<String, String> params = new HashMap<>();

        RefreshToken findRefreshToken = jwtTokenProvider.validateRefreshToken(principal.getId());

        if(findRefreshToken == null) {
            Member findMember = oauthMemberService.getById(principal.getId());
            KeyPair keyPair = jwtTokenProvider.generateKeyPair(findMember);
            params.put("access", keyPair.getAccessToken());
            params.put("refresh", keyPair.getRefreshToken());
        } else {
            String accessToken = jwtTokenProvider.issueAccessToken(principal.getId(), principal.getRole());
            params.put("access", accessToken);
            params.put("refresh", findRefreshToken.getRefreshToken());
        }

        String targetUrl = genUrlStr(params);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }

    private String genUrlStr(HashMap<String, String> params) {
        return UriComponentsBuilder.fromUri(URI.create(baseUrl))
                .queryParam("access", params.get("access"))
                .queryParam("refresh", params.get("refresh"))
                .build()
                .toUriString();

    }
}