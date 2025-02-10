package est.wordwise.domain.security.service;

import est.wordwise.common.entity.Member;
import est.wordwise.common.exception.MemberNotFoundException;
import est.wordwise.common.repository.MemberRepository;
import java.util.Optional;

import est.wordwise.domain.security.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
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

    // 현재 로그인되어 있는 사용자 정보 반환
    public Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return findMemberById(Long.parseLong(authentication.getName()));
    }
}
