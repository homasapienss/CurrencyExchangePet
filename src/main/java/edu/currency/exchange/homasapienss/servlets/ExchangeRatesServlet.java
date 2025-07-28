package edu.currency.exchange.homasapienss.servlets;

import edu.currency.exchange.homasapienss.dao.CurrencyDAO;
import edu.currency.exchange.homasapienss.dao.ExchangeRateDAO;
import edu.currency.exchange.homasapienss.dto.ExchangeRateDTO;
import edu.currency.exchange.homasapienss.exceptions.ApplicationException;
import edu.currency.exchange.homasapienss.exceptions.ExceptionHandler;
import edu.currency.exchange.homasapienss.service.CurrencyService;
import edu.currency.exchange.homasapienss.service.ExchangeRateService;
import edu.currency.exchange.homasapienss.utils.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends BaseServlet {
    ExchangeRateService exchangeRateService = new ExchangeRateService(new ExchangeRateDAO(),
                                                                      new CurrencyService(new CurrencyDAO()));

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            sendJsonResponse(resp, exchangeRateService.getAllDTO(), 200);
        } catch (ApplicationException e) {
            new ExceptionHandler().handleException(resp, e);
        }
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String base = req.getParameter("baseCurrencyCode");
        String target = req.getParameter("targetCurrencyCode");
        String rate = req.getParameter("rate");
        try {
            ValidationUtil.validateExchangeRate(base, target, rate);
            ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
            exchangeRateDTO.setRate(BigDecimal.valueOf(Double.parseDouble(rate)));
            exchangeRateDTO.setBaseCurrency(exchangeRateService.getCurrencyIdByCode(base));
            exchangeRateDTO.setTargetCurrency(exchangeRateService.getCurrencyIdByCode(target));
            exchangeRateService.save(exchangeRateDTO);
            sendJsonResponseSuccess(resp);
        } catch (ApplicationException e) {
            new ExceptionHandler().handleException(resp, e);
        }
    }
}
