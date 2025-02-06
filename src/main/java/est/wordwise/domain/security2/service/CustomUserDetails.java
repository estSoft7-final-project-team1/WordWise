package est.wordwise.domain.security2.service;

import est.wordwise.common.entity.Member;
import est.wordwise.common.repository.MemberRepository;
import est.wordwise.domain.security2.dto.MemberSignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        Member findMember = memberOptional.orElseThrow();

        return new MemberSignupDto(findMember);
    }
}
