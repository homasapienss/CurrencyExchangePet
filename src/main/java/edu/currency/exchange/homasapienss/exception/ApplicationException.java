package edu.currency.exchange.homasapienss.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final String errorMessage;

    public ApplicationException(String message) {
        super(message);
        this.errorMessage = message;
    }
}
