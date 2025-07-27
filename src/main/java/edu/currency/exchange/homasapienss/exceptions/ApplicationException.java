package edu.currency.exchange.homasapienss.exceptions;

public class ApplicationException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public ApplicationException(ErrorMessage message) {
        this.errorMessage = message;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
