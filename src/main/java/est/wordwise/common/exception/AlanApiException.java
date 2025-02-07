package est.wordwise.common.exception;

import lombok.Getter;

@Getter
public class AlanApiException extends RuntimeException {

    private final AlanApiErrorCode errorCode;

    public AlanApiException(AlanApiErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
