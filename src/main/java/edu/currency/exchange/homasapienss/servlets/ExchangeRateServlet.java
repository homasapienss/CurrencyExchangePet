package edu.currency.exchange.homasapienss.servlets;

import edu.currency.exchange.homasapienss.dto.ExchangeRateDTO;
import edu.currency.exchange.homasapienss.entities.ExchangeRate;
import edu.currency.exchange.homasapienss.exceptions.ApplicationException;
import edu.currency.exchange.homasapienss.exceptions.ErrorMessage;
import edu.currency.exchange.homasapienss.exceptions.ExceptionHandler;
import edu.currency.exchange.homasapienss.service.ExchangeRateService;
import edu.currency.exchange.homasapienss.utils.ExchangeRateUtil;
import edu.currency.exchange.homasapienss.utils.StringUtil;
import edu.currency.exchange.homasapienss.utils.ValidationUtil;
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
        if (!pair.isBlank()){
            String base = pair.substring(0, 3);
            String target = pair.substring(3);
            try {
                ValidationUtil.validateExchangeRate(pair);
                ExchangeRateDTO rateDTO = exchangeRateService.convertToExchangeRateDTO(
                        exchangeRateService.getByCodePair(base, target));
                sendJsonResponse(resp, rateDTO, 200);
            }catch (ApplicationException e) {
                new ExceptionHandler().handleException(resp, e);
            }
        }else {
            try {
                throw new ApplicationException(ErrorMessage.INVALID_DATA);
            }catch (ApplicationException e) {
                new ExceptionHandler().handleException(resp, e);
            }
        }


    }

    @Override protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pair = StringUtil.parsePathInfo(req);
        if (!pair.isBlank()){
            String base = pair.substring(0, 3);
            String target = pair.substring(3);
            String rate = req.getParameter("rate");
            try {
                ValidationUtil.validateExchangeRate(base,target,rate);
                ExchangeRate exchangeRate = exchangeRateService.getByCodePair(base, target);
                exchangeRate.setRate(BigDecimal.valueOf(Double.parseDouble(rate)));
                exchangeRateService.update(exchangeRate);
                ExchangeRateDTO rateDTO = exchangeRateService.convertToExchangeRateDTO(exchangeRate);
                sendJsonResponse(resp, rateDTO, 200);
            }catch (ApplicationException e){
                new ExceptionHandler().handleException(resp, e);
            }
        } else {
            try {
                throw new ApplicationException(ErrorMessage.INVALID_DATA);
            }catch (ApplicationException e){
                new ExceptionHandler().handleException(resp, e);
            }
        }

    }
}
