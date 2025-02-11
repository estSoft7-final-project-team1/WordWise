package est.wordwise.domain.conversation.service;

import static est.wordwise.common.exception.AlanApiErrorCode.RESPONSE_ERROR;
import static est.wordwise.domain.conversation.constants.ConversationConstants.PARAM_CLIENT_ID;
import static est.wordwise.domain.conversation.constants.ConversationConstants.PARAM_QUESTION;
import static est.wordwise.domain.conversation.constants.ConversationConstants.PROMPT;
import static est.wordwise.domain.conversation.constants.ConversationConstants.QUESTION;

import est.wordwise.common.exception.AlanApiErrorCode;
import est.wordwise.common.exception.AlanApiException;
import est.wordwise.domain.alanapi.config.AlanApiClientConfig;
import est.wordwise.domain.alanapi.dto.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConversationService {

    private final AlanApiClientConfig alanApiClientConfig;

    public String getContentFromApiWithQuery(String query) throws AlanApiException {
        RestClient restClientWithBaseUrl = RestClient.create(alanApiClientConfig.getUrl());
        Response response;
        log.info("getContentFromApiWithQuery: {}", query);
        try {
            response = restClientWithBaseUrl.get()
                .uri(uriBuilder -> uriBuilder
                    .path(QUESTION)
                    .queryParam(PARAM_QUESTION, query)
                    .queryParam(PARAM_CLIENT_ID, alanApiClientConfig.getChatId())
                    .build())
                .retrieve()
                .body(Response.class);
        } catch (Exception e) {
            log.info("엘런 api오류");
            throw new AlanApiException(AlanApiErrorCode.API_ERROR);
        }
        if (response == null) {
            throw new AlanApiException(RESPONSE_ERROR);
        }

        return response.getContent();
    }

    public String getContentFromApiWithPrompt() throws AlanApiException {
        RestClient restClientWithBaseUrl = RestClient.create(alanApiClientConfig.getUrl());
        Response response;

        try {
            response = restClientWithBaseUrl.get()
                .uri(uriBuilder -> uriBuilder
                    .path(QUESTION)
                    .queryParam(PARAM_QUESTION, PROMPT)
                    .queryParam(PARAM_CLIENT_ID, alanApiClientConfig.getChatId())
                    .build())
                .retrieve()
                .body(Response.class);
        } catch (Exception e) {
            log.info("엘런 api오류");
            throw new AlanApiException(AlanApiErrorCode.API_ERROR);
        }
        if (response == null) {
            throw new AlanApiException(RESPONSE_ERROR);
        }

        return response.getContent();
    }
}
