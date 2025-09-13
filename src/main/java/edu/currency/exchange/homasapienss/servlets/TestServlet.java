package edu.currency.exchange.homasapienss.servlets;

import edu.currency.exchange.homasapienss.dao.ExchangeRateDAO;
import edu.currency.exchange.homasapienss.entities.ExchangeRate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/test")
public class TestServlet extends BaseServlet {
    ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer first = Integer.parseInt(req.getParameter("1"));
        Integer second = Integer.parseInt(req.getParameter("2"));
        try {
            ExchangeRate byIdPair = exchangeRateDAO.getByIdPair(first, second).get();
            sendJsonResponse(resp, byIdPair, 203);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
