package est.wordwise.common.handler;

import est.wordwise.common.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity memberNotFound() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(WordBookNotFoundException.class)
    public ResponseEntity wordbookNotFound() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SentenceNotFoundException.class)
    public ResponseEntity sentenceNotFound() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity duplicatedEmail() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicatedNicknameException.class)
    public ResponseEntity duplicatedNickName() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(AlanApiException.class)
    public ResponseEntity apanApiException() {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .build();
    }

}
