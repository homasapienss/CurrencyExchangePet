package edu.currency.exchange.homasapienss.servlets;

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
}
