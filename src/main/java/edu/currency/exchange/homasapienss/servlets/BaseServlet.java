package edu.currency.exchange.homasapienss.servlets;

import edu.currency.exchange.homasapienss.exceptions.ExceptionMessage;
import edu.currency.exchange.homasapienss.utils.JsonUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BaseServlet extends HttpServlet {
    protected <T> void sendJsonResponse(HttpServletResponse resp, T payload, int status)
            throws IOException {
        resp.getWriter().write(JsonUtil.writeJson(payload));
        resp.setStatus(status);
    }
    protected void sendJsonResponseSuccess(HttpServletResponse resp)
            throws IOException {
        resp.getWriter().write(JsonUtil.writeJson(new ExceptionMessage("Операция прошла успешно")));
        resp.setStatus(202);
    }
}
