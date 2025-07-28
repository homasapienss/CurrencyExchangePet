package edu.currency.exchange.homasapienss.dao;

import edu.currency.exchange.homasapienss.entities.Currency;
import edu.currency.exchange.homasapienss.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDAO implements BaseDAO<Currency> {

    private static final String SQL_INSERT = "INSERT INTO Currencies (Code, FullName, Sign) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT * FROM Currencies";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM Currencies WHERE id = ?";
    private static final String SQL_SELECT_BY_CODE = "SELECT * FROM Currencies WHERE Code = ?";
    private static final String SQL_UPDATE = "UPDATE Currencies SET Code = ?, FullName = ?, Sign = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM Currencies WHERE id = ?";

    private Optional<Currency> getCurrency(ResultSet resultSet)
            throws SQLException {
        return resultSet.next() ? Optional.of(extractCurrency(resultSet)) : Optional.empty();
    }

    @Override public Optional<Currency> getById(Integer id)
            throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                return getCurrency(resultSet);
            }
        }
    }

    public Optional<Currency> getByCode(String code)
            throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_CODE)) {
            stmt.setString(1, code);
            try (ResultSet resultSet = stmt.executeQuery()) {
                return getCurrency(resultSet);
            }
        }
    }

    @Override public List<Currency> getAll()
            throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet resultSet = stmt.executeQuery()) {
            List<Currency> currencies = new ArrayList<>();
            while (resultSet.next()) currencies.add(extractCurrency(resultSet));
            return currencies;
        }
    }

    private Currency extractCurrency(ResultSet resultSet)
            throws SQLException {
        Currency currency = new Currency();
        currency.setCode(resultSet.getString("Code"));
        currency.setName(resultSet.getString("FullName"));
        currency.setSign(resultSet.getString("Sign"));
        currency.setId(resultSet.getInt("id"));
        return currency;
    }

    @Override public Integer save(Currency currency)
            throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            stmt.setString(1, currency.getCode());
            stmt.setString(2, currency.getName());
            stmt.setString(3, currency.getSign());
            return stmt.executeUpdate();
        }
    }

    @Override public Integer update(Currency currency)
            throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            stmt.setString(1, currency.getCode());
            stmt.setString(2, currency.getName());
            stmt.setString(3, currency.getSign());
            stmt.setInt(4, currency.getId());
            return stmt.executeUpdate();
        }
    }

    @Override public Integer delete(Integer id)
            throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        }
    }
}