package est.wordwise.domain.security2.service;

import est.wordwise.domain.security2.dto.OauthMemberDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class MemberDetailsFactory {
    public static OauthMemberDetails memberDetails(
            String provider, OAuth2User oAuth2User
    ){
        Map<String, Object> attributes = oAuth2User.getAttributes();

        switch (provider){
            case "GOOGLE" -> {
                return OauthMemberDetails.builder()
                        .nickname(attributes.get("name").toString())
                        .email(attributes.get("email").toString())
                        .attributes(attributes)
                        .build();
            }
            case "KAKAO" -> {
                Map<String, String> properties = (Map<String, String>) attributes.get("properties");
                return OauthMemberDetails.builder()
                        .nickname(properties.get("nickname").toString())
                        .email(attributes.get("id").toString()+"@kakao.com")
                        .attributes(attributes)
                        .build();
            }
            case "NAVER" -> {
                Map<String, String> properties = (Map<String, String>) attributes.get("response");
                return OauthMemberDetails.builder()
                        .nickname(properties.get("name").toString())
                        .email(properties.get("email").toString())
                        .attributes(attributes)
                        .build();
            }
            default -> throw new IllegalStateException("Unexpected value: " + provider);
        }
    }
}
