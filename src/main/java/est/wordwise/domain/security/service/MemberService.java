package est.wordwise.domain.security.service;

import est.wordwise.common.entity.Member;
import est.wordwise.common.exception.MemberNotFoundException;
import est.wordwise.common.repository.MemberRepository;
import est.wordwise.domain.security.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(
                () -> new MemberNotFoundException("해당 회원을 찾을 수 없습니다.")
            );
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(
                () -> new MemberNotFoundException("해당 회원을 찾을 수 없습니다.")
            );
    }

    public MemberDetails loadMemberDetailById(Long id) {
        return MemberDetails.from(findMemberById(id));
    }

    public Member findMemberByIdDev(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    // 임시 멤버 반환
    public Member getCurrentMember() {
        return findMemberById(51L);
    }
}
