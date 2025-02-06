package est.wordwise.domain.security2.service;

import est.wordwise.common.entity.Member;
import est.wordwise.common.repository.MemberRepository;
import est.wordwise.domain.security2.dto.OauthMemberDetails;
import est.wordwise.common.entity.commonEnum.memberEnums.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OauthMemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase();

        SocialType eSocialType = SocialType.valueOf(provider);

        OauthMemberDetails memberDetails = MemberDetailsFactory.memberDetails(provider, oAuth2User);

        Optional<Member> memberOptional = memberRepository.findByEmail(memberDetails.getEmail());

        Member findMember = memberOptional.orElseGet(
                () -> {
                    Member member = Member.builder()
                            .provider(eSocialType)
                            .email(memberDetails.getEmail())
                            .nickname(memberDetails.getNickname())
                            .password(memberDetails.getPassword())
                            .build();
                    return memberRepository.save(member);
                }
        );

        if (findMember.getProvider().equals(eSocialType)) {
            memberDetails.setId(findMember.getId()).setRole(memberDetails.getRole());
            memberDetails.setRole(findMember.getRole());
            return memberDetails;
        } else {
            throw new RuntimeException();
        }
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Member getById(Long id) {
        return findById(id).orElseThrow(
                () -> new NoSuchElementException("Member not found")
        );
    }

    public OauthMemberDetails loadMemberDetailById(Long id) {
        Member findMember = getById(id);
        return OauthMemberDetails.from(findMember);
    }
}
