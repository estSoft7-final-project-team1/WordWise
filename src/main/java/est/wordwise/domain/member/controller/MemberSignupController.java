package est.wordwise.domain.member.controller;


import est.wordwise.domain.member.dto.MemberSignupDto;

import est.wordwise.domain.member.service.MemberSignUpService;
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
@RequestMapping("/api")
public class MemberSignupController {
    public final MemberSignUpService memberSignupService;

    @PostMapping("/signup")
    public ResponseEntity<String> formSignUp(@RequestBody MemberSignupDto object) {
        log.info("received signUpInfo: {}", object);
        // 여기도 현재 로그인된 정보를 담아서 service로 전달
        memberSignupService.signup(object);
        return ResponseEntity.ok().body("Success");
    }
}
