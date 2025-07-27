package edu.currency.exchange.homasapienss.servlets;

import edu.currency.exchange.homasapienss.dto.ExchangeDTO;
import edu.currency.exchange.homasapienss.exceptions.ApplicationException;
import edu.currency.exchange.homasapienss.exceptions.ExceptionHandler;
import edu.currency.exchange.homasapienss.service.CurrencyService;
import edu.currency.exchange.homasapienss.service.ExchangeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/exchange")
public class ExchangeServlet extends BaseServlet {
    ExchangeService exchangeService = new ExchangeService();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        String amount = req.getParameter("amount");
        try {
            sendJsonResponse(resp, exchangeService.doExchange(from, to, amount),200);
        } catch (ApplicationException e) {
            new ExceptionHandler().handleException(resp, e);
        }
    }
}
