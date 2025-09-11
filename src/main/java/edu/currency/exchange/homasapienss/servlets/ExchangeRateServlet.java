package edu.currency.exchange.homasapienss.servlets;

import edu.currency.exchange.homasapienss.dao.CurrencyDAO;
import edu.currency.exchange.homasapienss.dao.ExchangeRateDAO;
import edu.currency.exchange.homasapienss.dto.ExchangeRateDTO;
import edu.currency.exchange.homasapienss.exceptions.ApplicationException;
import edu.currency.exchange.homasapienss.exceptions.ErrorMessage;
import edu.currency.exchange.homasapienss.exceptions.ExceptionHandler;
import edu.currency.exchange.homasapienss.service.CurrencyService;
import edu.currency.exchange.homasapienss.service.ExchangeRateService;
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
                sendJsonResponse(resp, exchangeRateService.getByCodePair(base, target), 200);
            }catch (ApplicationException e) {
                new ExceptionHandler().handleException(resp, e);
            }
        }else {
            new ExceptionHandler().handleException(resp, new ApplicationException(ErrorMessage.INVALID_DATA));
        }


    }


    @Override protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pair = StringUtil.parsePathInfo(req);
        if (!pair.isBlank()){
            String base = pair.substring(0, 3);
            String target = pair.substring(3);
            String rate = req.getReader().readLine().split("=")[1];
            try {
                ValidationUtil.validateExchangeRate(base,target,rate);
                ExchangeRateDTO exchangeRateDTO = exchangeRateService.getByCodePair(base, target);
                exchangeRateDTO.setRate(BigDecimal.valueOf(Double.parseDouble(rate)));
                exchangeRateService.update(exchangeRateDTO);
                sendJsonResponseSuccess(resp);
            }catch (ApplicationException e){
                new ExceptionHandler().handleException(resp, e);
            }
        } else {
            new ExceptionHandler().handleException(resp, new ApplicationException(ErrorMessage.INVALID_DATA));
        }

    }

}
