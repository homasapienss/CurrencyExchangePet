package edu.currency.exchange.homasapienss.servlets;


import com.fasterxml.jackson.core.io.BigDecimalParser;
import edu.currency.exchange.homasapienss.dto.ExchangeRateDTO;
import edu.currency.exchange.homasapienss.entities.ExchangeRate;
import edu.currency.exchange.homasapienss.service.ExchangeRateService;
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
        var exchangeRateDTOS =
                exchangeRateService.convertToListExchangeRateDTO(exchangeRateService.getAll());
        sendJsonResponse(resp, exchangeRateDTOS);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String base = req.getParameter("base");
        String target = req.getParameter("target");
        String rate = req.getParameter("rate");
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setRate(BigDecimal.valueOf(Double.parseDouble(rate)));
        System.out.println(BigDecimal.valueOf(Double.parseDouble(rate)));
        exchangeRate.setBaseCurrency(Integer.parseInt(base));
        exchangeRate.setTargetCurrency(Integer.parseInt(target));
        ExchangeRate rateObj = exchangeRateService.create(exchangeRate);
        sendJsonResponse(resp, exchangeRateService.convertToExchangeRateDTO(rateObj));
    }
}
