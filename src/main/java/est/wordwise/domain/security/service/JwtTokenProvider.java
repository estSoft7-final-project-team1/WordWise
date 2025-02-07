package est.wordwise.domain.security.service;

import est.wordwise.common.entity.Member;
import est.wordwise.domain.security.config.JwtConfig;
import est.wordwise.domain.security.domain.RefreshToken;
import est.wordwise.domain.security.dto.KeyPair;
import est.wordwise.domain.security.dto.TokenBody;
import est.wordwise.common.entity.commonEnum.memberEnums.AuthType;
import est.wordwise.domain.security.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;
    private final TokenRepository refreshTokenRepositoryAdapter;

    public KeyPair generateKeyPair(Member member) {
        String accessToken = issueAccessToken(member.getId(), member.getRole());
        String refreshToken = issueRefreshToken(member.getId(), member.getRole());

        refreshTokenRepositoryAdapter.save(member, refreshToken);

        return KeyPair.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberId(member.getId().toString())
                .build();
    }

    public String issueAccessToken(Long id, AuthType role){
        return issue(id, role, jwtConfig.getValidation().getAccess());
    }

    public String issueRefreshToken(Long id, AuthType role){
        return issue(id, role, jwtConfig.getValidation().getRefresh());
    }

    public RefreshToken validateRefreshToken(Long memberId) {
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepositoryAdapter.findValidRefreshTokenByMemberId(memberId);
        return refreshTokenOptional.orElse(null);
    }

    public String issue(Long memberId, AuthType role, Long validTime){
        //JWT 유효시간
        Date endDate = new Date(new Date().getTime() + validTime);

        //header.payload.signature
        return Jwts.builder()
                .subject(memberId.toString())
                .claim("role", role.toString())
                .issuedAt(new Date())
                .expiration(endDate)
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getAppKey().getBytes());
    }

    public boolean validate(String token) {
        try{
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch ( JwtException e ){
            log.info("Invalid JWT token detected. msg={}", e.getMessage());
            log.info("token = {}", token);
        } catch ( IllegalArgumentException e ){
            log.info("JWT claims String is empty, msg={}", e.getMessage());
            log.info("token = {}", token);
        } catch ( Exception e ){
            log.error("an error occurred while validating token, msg = {}", e.getMessage());
        }
        return false;
    }

    public TokenBody parseJwt(String token) {
        Jws<Claims> parsed = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token);
        return new TokenBody(
                Long.parseLong(parsed.getPayload().getSubject()),
                parsed.getPayload().get("role").toString());
    }
}
