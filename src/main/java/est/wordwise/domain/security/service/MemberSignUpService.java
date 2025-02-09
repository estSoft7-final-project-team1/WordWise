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

@Service
@RequiredArgsConstructor
public class MemberSignUpService {
    public final MemberRepository memberRepository;
    public final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signup(MemberSignupRequest request) {
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

    public Member login(SignInRequest signInRequest) {
        Member loginMember = memberService.findMemberByEmail(signInRequest.getEmail());
        if(passwordEncoder.matches(signInRequest.getPassword(), loginMember.getPassword())) {
            return loginMember;
        } else {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }
    }

}
