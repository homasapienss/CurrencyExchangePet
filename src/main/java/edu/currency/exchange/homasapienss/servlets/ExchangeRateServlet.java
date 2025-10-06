package edu.currency.exchange.homasapienss.servlets;

import edu.currency.exchange.homasapienss.exceptions.ApplicationException;
import edu.currency.exchange.homasapienss.exceptions.ExceptionHandler;
import edu.currency.exchange.homasapienss.service.ExchangeRateService;
import edu.currency.exchange.homasapienss.utils.StringUtil;
import edu.currency.exchange.homasapienss.utils.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends BaseServlet {
    ExchangeRateService exchangeRateService = new ExchangeRateService();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pair = StringUtil.parsePathInfo(req);
        try {
            ValidationUtil.validateExchangeRate(pair);
            String base = pair.substring(0, 3);
            String target = pair.substring(3);
            sendJsonResponse(resp, exchangeRateService.getByCodePair(base, target), 200);
        } catch (ApplicationException e) {
            new ExceptionHandler().handleException(resp, e);
        }
    }

    @Override protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pair = StringUtil.parsePathInfo(req);
        try {
            String rate = req.getParameter("rate");
            System.out.println(rate);
            ValidationUtil.validateExchangeRate(pair, rate);
            String base = pair.substring(0, 3);
            String target = pair.substring(3);
            exchangeRateService.updateByCodePair(base, target, rate);
            sendJsonResponseSuccess(resp);
        } catch (ApplicationException e) {
            new ExceptionHandler().handleException(resp, e);
        }
    }
}