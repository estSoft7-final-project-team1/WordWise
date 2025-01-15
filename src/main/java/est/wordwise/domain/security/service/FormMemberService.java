package est.wordwise.domain.security.service;

import est.wordwise.common.entity.Member;
import est.wordwise.common.repository.MemberRepository;
import est.wordwise.domain.security.dto.MemberSignupDto;
import est.wordwise.domain.security.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FormMemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignUpRequest signupDto) {
        if (existsByEmail(signupDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        Member member = Member.builder()
                .email(signupDto.getEmail())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .build();
        memberRepository.save(member);
    }

    public String login(MemberSignupDto loginDto) {
        Member member = memberRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // JWT



        return "토큰";
    }

    public boolean existsByEmail(String email) {
        return memberRepository.findByEmail(email).isEmpty();
    }
}
