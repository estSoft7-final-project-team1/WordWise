package est.wordwise.domain.security.dto;

import est.wordwise.common.entity.Member;
import est.wordwise.common.entity.commonEnum.memberEnums.AuthType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetails {

    private Long id;
    private String nickname;
    private String email;
    private AuthType role;

    public static MemberDetails from(Member member) {
        MemberDetails memberDetails = new MemberDetails();
        memberDetails.id = member.getId();
        memberDetails.email = member.getEmail();
        memberDetails.nickname = member.getNickname();
        memberDetails.role = member.getRole();
        return memberDetails;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 회원의 role 필드를 기반으로 권한을 생성하여 반환
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }
}
