package est.wordwise.domain.word.service;

import static est.wordwise.domain.alanapi.constants.Constants.PARAM_CLIENT_ID;
import static est.wordwise.domain.alanapi.constants.Constants.PARAM_QUESTION;
import static est.wordwise.domain.alanapi.constants.Constants.QUESTION;

import est.wordwise.common.repository.WordRepository;
import est.wordwise.domain.alanapi.config.AlanApiClientConfig;
import est.wordwise.domain.alanapi.exception.AlanApiErrorCode;
import est.wordwise.domain.alanapi.exception.AlanApiException;
import est.wordwise.domain.alanapi.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class WordService {

    private final WordRepository wordRepository;
    private final AlanApiClientConfig alanApiClientConfig;

    public String getContentFromApiWithQuestion(String question) throws AlanApiException {
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

        return response.getContent();
    }

}
