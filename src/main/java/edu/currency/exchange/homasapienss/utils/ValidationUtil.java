package edu.currency.exchange.homasapienss.utils;

import edu.currency.exchange.homasapienss.exceptions.ApplicationException;
import edu.currency.exchange.homasapienss.exceptions.ErrorMessage;
import edu.currency.exchange.homasapienss.exceptions.validation.EmptyFormFieldException;
import edu.currency.exchange.homasapienss.exceptions.validation.InvalidDataException;

public class ValidationUtil {
    public static void validateCurrency(String code, String name, String sign) {
        if (StringUtil.isEmptyField(code, name, sign)) {
            throw new EmptyFormFieldException();
        } else if (CurrencyUtil.isNotValid(code, name, sign)) {
            throw new InvalidDataException();
        }
    }

    public static void validateCurrency(String code) {
        if (code.length() != 3) {
            throw new ApplicationException(ErrorMessage.NO_CODE_URL);
        }
    }

    public static void validateExchangeRate(String pair) {
        if (pair.isBlank()){
            throw new ApplicationException(ErrorMessage.NO_CODE_URL);
        }else if (!ExchangeRateUtil.isCorrectPair(pair)) {
            throw new ApplicationException(ErrorMessage.INVALID_DATA);
        }
    }

    public static void validateExchangeRate(String base, String target, String rate) {
        if (StringUtil.isEmptyField(base, target, rate)){
            throw new ApplicationException(ErrorMessage.NO_DATA_FIELD);
        }else if (!ExchangeRateUtil.isCorrectArgs(base, target)) {
            throw new ApplicationException(ErrorMessage.INVALID_DATA);
        }else if (Double.parseDouble(rate)<=0){
            throw new ApplicationException(ErrorMessage.INVALID_DATA);
        }
    }

    public static void validateExchangeRate(String pair, String rate) {
        if (pair.isBlank()){
            throw new ApplicationException(ErrorMessage.NO_CODE_URL);
        }else if (!ExchangeRateUtil.isCorrectPair(pair)) {
            throw new ApplicationException(ErrorMessage.INVALID_DATA);
        }else if (Double.parseDouble(rate)<=0){
            throw new ApplicationException(ErrorMessage.INVALID_DATA);
        }
    }
}
