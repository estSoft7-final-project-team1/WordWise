package est.wordwise.domain.security2.dto;

import est.wordwise.common.entity.Member;
import est.wordwise.common.entity.commonEnum.memberEnums.AuthType;
import est.wordwise.common.entity.commonEnum.memberEnums.SocialType;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthMemberDetails implements OAuth2User {

    @Setter
    private Long id;

    private String email;
    private String password;
    private String nickname;
    private String phonenumber;

    @Setter
    private AuthType role = AuthType.MEMBER;

    private SocialType provider;

    private Map<String, Object> attributes;

    public static OauthMemberDetails from(Member member){
        OauthMemberDetails details = new OauthMemberDetails();
        details.id = member.getId();
        details.email = member.getEmail();
        details.password = member.getPassword();
        details.nickname = member.getNickname();
        details.phonenumber = member.getPhonenumber();
        return details;
    }

    @Builder
    public OauthMemberDetails(Long id, String email, String password, String nickname, Map<String, Object> attributes) {
        this.email = email;
        this.nickname = nickname;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(role.toString())
        );
    }

    @Override
    public String getName() {
        return nickname;
    }
}
