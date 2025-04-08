package edu.currency.exchange.homasapienss.utils;

public class ExchangeRateUtil {
    public static boolean isCorrectPair(String pair){
        return pair.length()==6;
    }
    public static boolean isCorrectArgs(String base, String target) {
        return base.length()==3 && target.length()==3;
    }
}
