package est.wordwise.domain.security.controller;


import est.wordwise.common.entity.Member;
import est.wordwise.domain.security.dto.MemberSignupRequest;
import est.wordwise.domain.security.dto.SignInRequest;
import est.wordwise.domain.security.service.JwtTokenProvider;
import est.wordwise.domain.security.service.MemberSignUpService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class MemberSignupController {

    public final MemberSignUpService memberSignupService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<String> formSignUp(@RequestBody MemberSignupRequest request) {
        log.info("received signUpInfo: {}", request);
        // 여기도 현재 로그인된 정보를 담아서 service로 전달
        try {
            memberSignupService.signup(request);
            return ResponseEntity.ok().body("Success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestBody Map<String, String> req) {
        log.info("received checkEmailInfo: {}", req);
        String email = req.get("email");
        return ResponseEntity.ok().body(memberSignupService.checkEmail(email));
    }

    @PostMapping("check-nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestBody Map<String, String> req) {
        log.info("received checkNicknameInfo: {}", req);
        String nickname = req.get("nickname");
        return ResponseEntity.ok().body(memberSignupService.checkNickname(nickname));
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> login(@RequestBody SignInRequest object) {
        try {
            log.info("received loginInfo: {}", object);
            Member loginMember = memberSignupService.login(object);
            String token = jwtTokenProvider.generateJwtToken(loginMember);

            return ResponseEntity.ok().body(Map.of("accessToken", token)); // 아마 여기에 토큰 발급
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
