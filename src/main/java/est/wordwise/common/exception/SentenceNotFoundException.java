package est.wordwise.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SentenceNotFoundException extends RuntimeException {
    public SentenceNotFoundException(String message) {
        super(message);
    }

    public SentenceNotFoundException() {
        super();
    }

    public SentenceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SentenceNotFoundException(Throwable cause) {
        super(cause);
    }

    protected SentenceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
