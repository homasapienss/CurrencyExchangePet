package edu.currency.exchange.homasapienss.exceptions;

public class ApplicationException extends RuntimeException {
    private ErrorMessage errorMessage;

    public ApplicationException(ErrorMessage message) {
        this.errorMessage = message;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
