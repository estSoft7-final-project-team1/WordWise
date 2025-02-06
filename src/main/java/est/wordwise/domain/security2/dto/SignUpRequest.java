package est.wordwise.domain.security2.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String email;
    private String password;
}
