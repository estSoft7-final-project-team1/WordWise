package est.wordwise.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WordBookNotFoundException extends RuntimeException {
    public WordBookNotFoundException(String message) {
        super(message);
    }

    public WordBookNotFoundException() {
        super();
    }

    public WordBookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WordBookNotFoundException(Throwable cause) {
        super(cause);
    }

    protected WordBookNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
