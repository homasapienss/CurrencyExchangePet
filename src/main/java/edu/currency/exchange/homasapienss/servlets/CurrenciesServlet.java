package edu.currency.exchange.homasapienss.servlets;

import edu.currency.exchange.homasapienss.dao.CurrencyDAO;
import edu.currency.exchange.homasapienss.utils.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import edu.currency.exchange.homasapienss.entities.Currency;

import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {

    CurrencyDAO currencyDAO = new CurrencyDAO();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Currency> currencies = currencyDAO.getAll();
        resp.getWriter().write(JsonUtil.writeJson(currencies));
        resp.getWriter().write(JsonUtil.writeJson(currencyDAO.getById(1)));

    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String sign = req.getParameter("sign");
        Currency currency = new Currency(code, name, sign);
        resp.getWriter().write(JsonUtil.writeJson(currencyDAO.create(currency)));
    }

    @Override protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        currencyDAO.delete(id);
    }

    @Override protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String sign = req.getParameter("sign");
        int id = Integer.parseInt(req.getParameter("id"));
        Currency currency = new Currency(code, name, sign);
        currency.setId(id);
        resp.getWriter().write(JsonUtil.writeJson(currencyDAO.update(currency)));
    }
}
