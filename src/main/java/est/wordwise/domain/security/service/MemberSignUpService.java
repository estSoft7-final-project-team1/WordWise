package est.wordwise.domain.security.service;

import est.wordwise.common.entity.Member;
import est.wordwise.common.exception.DuplicatedEmailException;
import est.wordwise.common.exception.DuplicatedNicknameException;

import est.wordwise.common.repository.MemberRepository;
import est.wordwise.domain.security.dto.MemberSignupRequest;
import est.wordwise.domain.security.dto.SignInRequest;
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
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signup(MemberSignupRequest request) {


        request.setPassword(passwordEncoder.encode(request.getPassword()));

        Member signUp = Member.from(request);

        memberRepository.save(signUp);
    }

    public Boolean checkEmail(String email) {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        return memberOptional.isEmpty();
    }

    public Boolean checkNickname(String nickname) {
        Optional<Member> memberOptional = memberRepository.findByNickname(nickname);
        return memberOptional.isEmpty();
    }

    public Member login(SignInRequest signInRequest) {
        Member loginMember = memberService.findMemberByEmail(signInRequest.getEmail());
        if(passwordEncoder.matches(signInRequest.getPassword(), loginMember.getPassword())) {
            return loginMember;
        } else {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }
    }

}
