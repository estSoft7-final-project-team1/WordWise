package est.wordwise.domain.member.service;

import est.wordwise.common.entity.Member;
import est.wordwise.common.repository.MemberRepository;
import est.wordwise.domain.member.dto.MemberSignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSignUpService {
    public final MemberRepository memberRepository;

    public void signup(MemberSignupDto Info) {
        Member signUp = Member.from(Info);
        memberRepository.save(signUp);
    }
}
