package est.wordwise.domain.alanapi.service;

import static est.wordwise.domain.alanapi.constants.Constants.PARAM_CLIENT_ID;
import static est.wordwise.domain.alanapi.constants.Constants.PARAM_QUESTION;
import static est.wordwise.domain.alanapi.constants.Constants.QUESTION;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import est.wordwise.domain.alanapi.config.AlanApiClientConfig;
import est.wordwise.domain.alanapi.dto.Response;
import est.wordwise.domain.alanapi.dto.ResponseContent;
import est.wordwise.domain.alanapi.exception.AlanApiErrorCode;
import est.wordwise.domain.alanapi.exception.AlanApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlanApiService {

    private final AlanApiClientConfig alanApiClientConfig;

    public ResponseContent getResponseContentFromApiWithQuery(String query) {
        return parseJsonToResponseContent(
            getContentFromApiWithQuery(query)
        );
    }

    public String getContentFromApiWithQuery(String query) throws AlanApiException {
        RestClient restClientWithBaseUrl = RestClient.create(alanApiClientConfig.getUrl());

        Response response;

        try {
            response = restClientWithBaseUrl.get()
                .uri(uriBuilder -> uriBuilder
                    .path(QUESTION)
                    .queryParam(PARAM_QUESTION, query)
                    .queryParam(PARAM_CLIENT_ID, alanApiClientConfig.getId())
                    .build())
                .retrieve()
                .body(Response.class);
        } catch (Exception e) {
            throw new AlanApiException(AlanApiErrorCode.API_ERROR);
        }

        if (response == null) {
            throw new AlanApiException(AlanApiErrorCode.NULL_RESPONSE_ERROR);
        }

        return response.getContent();
    }

    public ResponseContent parseJsonToResponseContent(String jsonContent) {

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseContent responseContent = null;

        try {
            responseContent = objectMapper.readValue(jsonContent, ResponseContent.class);
        } catch (JsonProcessingException e) {
            log.info("e.getMessage() = {}", e.getMessage());
        }

        return responseContent;
    }
}
