package est.wordwise.domain.alanapi.service;

import static est.wordwise.common.exception.AlanApiErrorCode.RESPONSE_ERROR;
import static est.wordwise.domain.alanapi.constants.Constants.PARAM_CLIENT_ID;
import static est.wordwise.domain.alanapi.constants.Constants.PARAM_QUESTION;
import static est.wordwise.domain.alanapi.constants.Constants.QUESTION;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import est.wordwise.common.exception.AlanApiErrorCode;
import est.wordwise.common.exception.AlanApiException;
import est.wordwise.domain.alanapi.config.AlanApiClientConfig;
import est.wordwise.domain.alanapi.dto.Response;
import est.wordwise.domain.alanapi.dto.ResponseContent;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlanApiService {

    private final AlanApiClientConfig alanApiClientConfig;

    // 입력된 쿼리로 word 도메인 객체 반환
    public ResponseContent getResponseContentFromApiWithQuery(String query)
        throws AlanApiException {
        return parseJsonToResponseContent(
            getContentFromApiWithQuery(query)
        );
    }

    // 입력된 쿼리로 alan api로부터 요청-응답 수신
    public String getContentFromApiWithQuery(String query) {
        RestClient restClientWithBaseUrl = RestClient.create(alanApiClientConfig.getUrl());

        Response response;

        try {
            response = restClientWithBaseUrl.get()
                .uri(uriBuilder -> {
                    return uriBuilder
                        .path(QUESTION)
                        .queryParam(PARAM_QUESTION,
                            URLEncoder.encode(query, StandardCharsets.UTF_8))
                        .queryParam(PARAM_CLIENT_ID, alanApiClientConfig.getId())
                        .build();
                })
                .retrieve()
                .body(Response.class);
        } catch (Exception e) {
            throw new AlanApiException(AlanApiErrorCode.API_ERROR);
        }

        if (response == null) {
            throw new AlanApiException(RESPONSE_ERROR);
        }

        return response.getContent();
    }

    // json을 word 도메인 객체로 변환
    public ResponseContent parseJsonToResponseContent(String jsonContent) {

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseContent responseContent = null;

        int startIndex = jsonContent.indexOf('{');
        int endIndex = jsonContent.lastIndexOf('}');

        try {
            responseContent = objectMapper.readValue(
                jsonContent.substring(startIndex, endIndex + 1), ResponseContent.class);
        } catch (JsonProcessingException e) {
            throw new AlanApiException(RESPONSE_ERROR);
        }

        return responseContent;
    }
}
