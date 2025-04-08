package edu.currency.exchange.homasapienss.exceptions;

import jakarta.servlet.http.HttpServletResponse;

public enum ErrorMessage {
    ERROR("Ошибка Сервера", HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
    NO_CODE_URL("Отсутствует код валюты в адресе", HttpServletResponse.SC_BAD_REQUEST),
    NO_CODE_DB("Валюта не найдена в базе", HttpServletResponse.SC_NOT_FOUND),
    NO_DATA_FIELD("Отсутствует поле формы", HttpServletResponse.SC_BAD_REQUEST),
    ALREADY_EXISTS("Уже существует", HttpServletResponse.SC_CONFLICT),
    INVALID_DATA("Данные не верно введенны", HttpServletResponse.SC_BAD_REQUEST),
    NO_CODE_PAIR_DB("Валютная пара не найдена в базе", HttpServletResponse.SC_NOT_FOUND),;
    private final String message;
    private final int statusCode;

    ErrorMessage(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
