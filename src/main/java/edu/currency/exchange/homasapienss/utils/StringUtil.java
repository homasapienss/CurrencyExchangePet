package edu.currency.exchange.homasapienss.utils;

import jakarta.servlet.http.HttpServletRequest;

public class StringUtil {
    public static String parsePathInfo(HttpServletRequest req) {
        return req.getPathInfo().replaceAll("/", "").toUpperCase();
    }
    public static boolean isEmptyField(String s1, String s2, String s3) {
        return s1.isEmpty() || s2.isEmpty() || s3.isEmpty();
    }
}
