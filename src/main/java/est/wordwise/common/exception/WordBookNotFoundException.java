package est.wordwise.common.exception;

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
