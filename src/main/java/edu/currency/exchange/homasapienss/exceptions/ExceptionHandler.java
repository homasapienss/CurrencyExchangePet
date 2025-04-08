package edu.currency.exchange.homasapienss.exceptions;

import edu.currency.exchange.homasapienss.servlets.BaseServlet;
import edu.currency.exchange.homasapienss.utils.JsonUtil;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ExceptionHandler extends BaseServlet {
    public void handleException(HttpServletResponse resp, ApplicationException e)
            throws IOException {
        ErrorMessage errorMessage = e.getErrorMessage();
        sendJsonResponse(resp, new ExceptionMessage(errorMessage.getMessage()),
                         errorMessage.getStatusCode());
    }
}
