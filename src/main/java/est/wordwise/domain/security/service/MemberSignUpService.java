package est.wordwise.domain.security.service;

import est.wordwise.common.entity.Member;
import est.wordwise.common.exception.DuplicatedEmailException;
import est.wordwise.common.exception.DuplicatedNicknameException;

import est.wordwise.common.repository.MemberRepository;
import est.wordwise.domain.security.dto.MemberSignupDto;
import est.wordwise.domain.security.dto.SignUpRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberSignUpService {
    public final MemberRepository memberRepository;
    public final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @Transactional
    public void signup(MemberSignupDto request) {
        // 이메일 중복 여부 확인
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicatedEmailException("이미 가입된 이메일 입니다.");
        }

        // 닉네임 중복 여부 확인
        if (memberRepository.findByNickname(request.getNickname()).isPresent()) {
            throw new DuplicatedNicknameException("이미 가입된 닉네임 입니다.");
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        Member signUp = Member.from(request);

        memberRepository.save(signUp);
    }

    public void login(SignUpRequest signUpRequest) {
        Member loginMember = memberService.findMemberByEmail(signUpRequest.getEmail());
        // 여기서 토큰 찾아서 사용?

    }

}
