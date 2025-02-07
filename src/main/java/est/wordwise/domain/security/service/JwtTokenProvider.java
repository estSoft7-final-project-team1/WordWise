package est.wordwise.domain.security.service;

import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
//    @Value("${jwt.secret}")
    private String jwtSecret;


}
