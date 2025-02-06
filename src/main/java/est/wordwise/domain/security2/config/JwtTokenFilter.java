package est.wordwise.domain.security2.config;

import est.wordwise.domain.security2.dto.OauthMemberDetails;
import est.wordwise.domain.security2.dto.TokenBody;
import est.wordwise.domain.security2.service.JwtTokenProvider;
import est.wordwise.domain.security2.service.OauthMemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final OauthMemberService oauthMemberService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = resolveToken(request);

        if (token != null && jwtTokenProvider.validate(token)) {
            TokenBody claims = jwtTokenProvider.parseJwt(token);
            OauthMemberDetails memberDetails = oauthMemberService.loadMemberDetailById(claims.getMemberId());

            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                    memberDetails,
                    token,
                    memberDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
