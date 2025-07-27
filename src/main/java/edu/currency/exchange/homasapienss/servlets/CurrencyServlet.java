package edu.currency.exchange.homasapienss.servlets;

import edu.currency.exchange.homasapienss.dao.CurrencyDAO;
import edu.currency.exchange.homasapienss.exceptions.ApplicationException;
import edu.currency.exchange.homasapienss.exceptions.ExceptionHandler;
import edu.currency.exchange.homasapienss.service.CurrencyService;
import edu.currency.exchange.homasapienss.utils.StringUtil;
import edu.currency.exchange.homasapienss.utils.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/currency/*")
public class CurrencyServlet extends BaseServlet {
    CurrencyService currencyService = new CurrencyService(new CurrencyDAO());

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String code = StringUtil.parsePathInfo(req);
        try {
            ValidationUtil.validateCurrency(code);
            sendJsonResponse(resp, currencyService.getByCode(code), 200);
        } catch (ApplicationException e) {
            new ExceptionHandler().handleException(resp, e);
        }

    }
}
