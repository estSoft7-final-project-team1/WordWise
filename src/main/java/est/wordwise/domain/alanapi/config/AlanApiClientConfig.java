package est.wordwise.domain.alanapi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AlanApiClientConfig {

    private final String url;
    private final String wordSearchId;
    private final String chatId;

    public AlanApiClientConfig(@Value("${spring.alan-api.client.url}") String url,
        @Value("${spring.alan-api.client.word-search-id}") String wordSearchId,
        @Value("${spring.alan-api.client.chat-id}") String chatId) {
        this.url = url;
        this.wordSearchId = wordSearchId;
        this.chatId = chatId;
    }
}
