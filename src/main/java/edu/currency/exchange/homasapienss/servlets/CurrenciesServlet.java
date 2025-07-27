package edu.currency.exchange.homasapienss.servlets;

import edu.currency.exchange.homasapienss.dao.CurrencyDAO;
import edu.currency.exchange.homasapienss.dto.CurrencyDTO;
import edu.currency.exchange.homasapienss.exceptions.ApplicationException;
import edu.currency.exchange.homasapienss.exceptions.ExceptionHandler;
import edu.currency.exchange.homasapienss.service.CurrencyService;
import edu.currency.exchange.homasapienss.utils.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/currencies")
public class CurrenciesServlet extends BaseServlet {

    CurrencyService currencyService = new CurrencyService(new CurrencyDAO());

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            sendJsonResponse(resp, currencyService.getAll(), 200);
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
            CurrencyDTO currencyDTO = new CurrencyDTO(code, name, sign);
            currencyService.save(currencyDTO);
            sendJsonResponseSuccess(resp);
        }catch (ApplicationException e) {
            new ExceptionHandler().handleException(resp, e);
        }

    }

    @Override protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        try {
            currencyService.delete(id);
            sendJsonResponseSuccess(resp);
        } catch (ApplicationException e) {
            new ExceptionHandler().handleException(resp, e);
        }

    }
}