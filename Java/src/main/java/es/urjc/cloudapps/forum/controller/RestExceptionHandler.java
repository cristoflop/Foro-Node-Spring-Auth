package es.urjc.cloudapps.forum.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Error> fieldErrorException(ConstraintViolationException ex) {
        return ResponseEntity
                .badRequest()
                .body(Error.from(ex));
    }

    static class Error {

        private final String reason;

        public Error(String reason) {
            this.reason = reason;
        }

        public static Error from(Exception ex) {
            return new Error(ex.getMessage());
        }

        public String getReason() {
            return reason;
        }
    }

}