package edu.currency.exchange.homasapienss.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.currency.exchange.homasapienss.dao.ExchangeRateDAO;
import edu.currency.exchange.homasapienss.entities.Currency;
import edu.currency.exchange.homasapienss.entities.ExchangeRate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<ExchangeRate> exchangeRates = exchangeRateDAO.getAll();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(exchangeRates);
        resp.getWriter().write(jsonResponse);
    }
}
