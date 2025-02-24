package edu.currency.exchange.homasapienss.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.currency.exchange.homasapienss.entities.Currency;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import edu.currency.exchange.homasapienss.utils.ConnectionManager;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/currencies/{name}")
public class CurrencyServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Currency> currencies = new ArrayList<>();
        try (var allCurrenciesStatement = ConnectionManager.prepareStatement(
                "SELECT * FROM Currencies WHERE code=?")) {
            allCurrenciesStatement.setString(1, req.getPathInfo());
            try {
                ResultSet setOfAllCurrencies = allCurrenciesStatement.executeQuery();
                while (setOfAllCurrencies.next()) {
                    currencies.add(new Currency(
                            setOfAllCurrencies.getInt("id"),
                            setOfAllCurrencies.getString("code"),
                            setOfAllCurrencies.getString("fullname"),
                            setOfAllCurrencies.getString("sign")
                    ));
                }
            } catch (SQLException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"error\": \"Database error\"}");
                e.printStackTrace();
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(currencies);
        resp.getWriter().write(jsonResponse);
    }
}
