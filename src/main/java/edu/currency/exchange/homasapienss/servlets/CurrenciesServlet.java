package edu.currency.exchange.homasapienss.servlets;

import edu.currency.exchange.homasapienss.dao.CurrencyDAO;
import edu.currency.exchange.homasapienss.exceptions.ApplicationException;
import edu.currency.exchange.homasapienss.exceptions.ErrorMessage;
import edu.currency.exchange.homasapienss.exceptions.ExceptionHandler;
import edu.currency.exchange.homasapienss.exceptions.ExceptionMessage;
import edu.currency.exchange.homasapienss.service.CurrencyService;
import edu.currency.exchange.homasapienss.utils.JsonUtil;
import edu.currency.exchange.homasapienss.utils.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import edu.currency.exchange.homasapienss.entities.Currency;

import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends BaseServlet {


    CurrencyService currencyService = new CurrencyService();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Currency> currencies = currencyService.getAll();
            sendJsonResponse(resp, currencies, 200);
        } catch (ApplicationException e) {
            new ExceptionHandler().handleException(resp, e);
        }
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String sign = req.getParameter("sign");
        try {
            ValidationUtil.validateCurrency(code, name, sign);
            Currency currency = new Currency(code, name, sign);
            sendJsonResponse(resp, currencyService.create(currency), 201);
        }catch (ApplicationException e) {
            new ExceptionHandler().handleException(resp, e);
        }

    }
}