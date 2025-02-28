package edu.currency.exchange.homasapienss.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.currency.exchange.homasapienss.dao.CurrencyDAO;
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
            throws ServletException, IOException{

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<Currency> currencies = currencyDAO.getAll();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(currencies);
        resp.getWriter().write(jsonResponse);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String sign = req.getParameter("sign");
        Currency currency = new Currency(code, name, sign);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(new CurrencyDAO().create(currency));
        resp.getWriter().write(jsonResponse);
    }
}
