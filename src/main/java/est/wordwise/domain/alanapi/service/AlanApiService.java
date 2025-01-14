package est.wordwise.domain.alanapi.service;

import static est.wordwise.domain.alanapi.constants.Constants.PARAM_CLIENT_ID;
import static est.wordwise.domain.alanapi.constants.Constants.PARAM_QUESTION;
import static est.wordwise.domain.alanapi.constants.Constants.QUESTION;

import est.wordwise.domain.alanapi.config.AlanApiClientConfig;
import est.wordwise.domain.alanapi.entity.ApiResponse;
import est.wordwise.domain.alanapi.entity.ResponseContent;
import est.wordwise.domain.alanapi.exception.AlanApiErrorCode;
import est.wordwise.domain.alanapi.exception.AlanApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class AlanApiService {

    private final AlanApiClientConfig alanApiClientConfig;

    public ResponseContent getContentFromApiWithQuestion(String question) throws AlanApiException {
        RestClient restClientWithBaseUrl = RestClient.create(alanApiClientConfig.getUrl());

        ApiResponse response;

        try {
            response = restClientWithBaseUrl.get()
                .uri(uriBuilder -> uriBuilder
                    .path(QUESTION)
                    .queryParam(PARAM_QUESTION, question)
                    .queryParam(PARAM_CLIENT_ID, alanApiClientConfig.getId())
                    .build())
                .retrieve()
                .body(ApiResponse.class);
        } catch (Exception e) {
            throw new AlanApiException(AlanApiErrorCode.API_ERROR);
        }

        if (response == null) {
            throw new AlanApiException(AlanApiErrorCode.NULL_RESPONSE_ERROR);
        }

        return parseJsonToContent(response.getContent());
    }

    private ResponseContent parseJsonToContent(String jsonContent) {

        return null;
    }
}
