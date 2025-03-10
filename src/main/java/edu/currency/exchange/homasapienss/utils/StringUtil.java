package edu.currency.exchange.homasapienss.utils;

import jakarta.servlet.http.HttpServletRequest;

public class StringUtil {
    public static String parsePathInfo(HttpServletRequest req) {
        return req.getPathInfo().replaceAll("/", "").toUpperCase();
    }
}
