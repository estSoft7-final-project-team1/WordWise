package est.wordwise.domain.security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberSignupRequest {
    private String nickname;
    private String email;
    private String password;
}
