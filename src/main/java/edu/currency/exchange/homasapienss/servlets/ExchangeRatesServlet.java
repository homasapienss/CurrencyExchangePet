package edu.currency.exchange.homasapienss.servlets;

import edu.currency.exchange.homasapienss.entities.ExchangeRate;
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
    ExchangeRateService exchangeRateService = new ExchangeRateService();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            var exchangeRateDTOS =
                    exchangeRateService.convertToListExchangeRateDTO(exchangeRateService.getAll());
            sendJsonResponse(resp, exchangeRateDTOS, 200);
        }catch (ApplicationException e){
            new ExceptionHandler().handleException(resp, e);
        }
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        CurrencyService currencyService= new CurrencyService();
        String base = req.getParameter("baseCurrencyCode");
        String target = req.getParameter("targetCurrencyCode");
        String rate = req.getParameter("rate");
        try {
            ValidationUtil.validateExchangeRate(base,target,rate);
            ExchangeRate exchangeRate = new ExchangeRate();
            exchangeRate.setRate(BigDecimal.valueOf(Double.parseDouble(rate)));
            exchangeRate.setBaseCurrency(currencyService.getByCode(base).get().getId());
            exchangeRate.setTargetCurrency(currencyService.getByCode(target).get().getId());
            ExchangeRate rateObj = exchangeRateService.create(exchangeRate);
            sendJsonResponse(resp, exchangeRateService.convertToExchangeRateDTO(rateObj),200);
        } catch (ApplicationException e) {
            new ExceptionHandler().handleException(resp, e);
        }
    }
}
