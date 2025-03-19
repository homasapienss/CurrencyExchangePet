package edu.currency.exchange.homasapienss.servlets;

import edu.currency.exchange.homasapienss.dto.ExchangeRateDTO;
import edu.currency.exchange.homasapienss.entities.ExchangeRate;
import edu.currency.exchange.homasapienss.service.ExchangeRateService;
import edu.currency.exchange.homasapienss.utils.StringUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends BaseServlet {
    ExchangeRateService exchangeRateService = new ExchangeRateService();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pair = StringUtil.parsePathInfo(req);
        String base = pair.substring(0, 3);
        String target = pair.substring(3);
        ExchangeRateDTO rate = exchangeRateService.convertToExchangeRateDTO(
                exchangeRateService.getByCodePair(base, target));
        sendJsonResponse(resp, rate);
    }

    @Override protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pair = StringUtil.parsePathInfo(req);
        String base = pair.substring(0, 3);
        String target = pair.substring(3);
        String rate = req.getParameter("rate");
        ExchangeRate exchangeRate = exchangeRateService.getByCodePair(base, target);
        exchangeRate.setRate(BigDecimal.valueOf(Double.parseDouble(rate)));
        exchangeRateService.update(exchangeRate);
        ExchangeRateDTO rateDTO = exchangeRateService.convertToExchangeRateDTO(exchangeRate);
        sendJsonResponse(resp, rateDTO);
    }
}
