package est.wordwise.common.exception;

public class DuplicatedPhoneNumberException extends RuntimeException {
    public DuplicatedPhoneNumberException(String message) {
        super(message);
    }
}
