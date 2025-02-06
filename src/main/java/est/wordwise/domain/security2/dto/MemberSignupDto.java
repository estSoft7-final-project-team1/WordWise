package est.wordwise.domain.security2.dto;

import est.wordwise.common.entity.Member;
import est.wordwise.common.entity.commonEnum.memberEnums.AuthType;
import est.wordwise.common.entity.commonEnum.memberEnums.SocialType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class MemberSignupDto implements UserDetails {

    private String email;

    @Setter
    private String password;

    private String nickname;
    private String phonenumber;
    private SocialType provider;
    private AuthType role;

    public MemberSignupDto(Member member) {
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
        this.phonenumber = member.getPhonenumber();
        this.provider = member.getProvider();
        this.role = member.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(role.toString())
        );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
