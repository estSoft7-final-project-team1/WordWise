package est.wordwise.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlanApiErrorCode {
    API_ERROR("API 호출 중 문제가 발생하였습니다."),
    RESPONSE_ERROR("API에서 유효한 응답을 받지 못했습니다.");

    private final String message;

}
