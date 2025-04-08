package edu.currency.exchange.homasapienss.utils;

public class CurrencyUtil {
    public static boolean isNotValid(String code, String name, String sign) {
        return code.length() != 3 || name.length() > 100 || sign.length() > 5;
    }
}
