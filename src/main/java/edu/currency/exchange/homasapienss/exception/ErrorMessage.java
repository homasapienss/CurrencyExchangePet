package edu.currency.exchange.homasapienss.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {
    private String errorMessage;

    public static ErrorMessage create(ApplicationException ex) {
        return new ErrorMessage(ex.getErrorMessage());
    }
}
