package est.wordwise.domain.security2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyPair {
    private String accessToken;
    private String refreshToken;
    private String memberId;

}
