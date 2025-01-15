package est.wordwise.domain.alanapi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AlanApiClientConfig {

    private final String url;
    private final String id;

    public AlanApiClientConfig(@Value("${spring.alan-api.client.url}") String url,
        @Value("${spring.alan-api.client.id}") String id) {
        this.url = url;
        this.id = id;
    }
}
