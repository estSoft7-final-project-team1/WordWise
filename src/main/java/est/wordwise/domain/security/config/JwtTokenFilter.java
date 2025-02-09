package est.wordwise.domain.security.config;


import est.wordwise.domain.security.dto.MemberDetails;
import est.wordwise.domain.security.dto.TokenBody;
import est.wordwise.domain.security.service.JwtTokenProvider;
import est.wordwise.domain.security.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )   throws ServletException, IOException {
        log.info("request : {}", request );
        String token = resolveToken(request);
        log.info("token : {}", token );
        if (token != null && jwtTokenProvider.validateToken(token)) {
            TokenBody claims = jwtTokenProvider.parseJwt(token);
            log.info("claims : {}", claims);
            MemberDetails memberDetails = memberService.loadMemberDetailById(claims.getMemberId());

            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                    claims.getMemberId(), // 사용자 아이디 또는 로그인 폼에서 입력한 사용자 정보전달
                    token, // 보통 비밀번호를 제출하지만 다른 값이 들어갈 수 있음
                    List.of(new SimpleGrantedAuthority("ROLE_"+claims.getToken())) // role을 직접 설정
//                    memberDetails.getAuthorities() // ?
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);

    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");//Authorization, Content-Type, Accept,Cookie, Host 등이 들어갈 수 있음
        // -> "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6..."과 같은 값으로 추출됨
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
