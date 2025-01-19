package est.wordwise.common.util;

import est.wordwise.common.entity.Member;
import est.wordwise.common.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Optional<Member> findMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    // 임시 멤버 반환
    public Member getCurrentMember() {
        return memberRepository.findByEmail("member1@email.com").orElse(null);
    }
}
