package edu.currency.exchange.homasapienss.dao;

import edu.currency.exchange.homasapienss.entities.ExchangeRate;
import edu.currency.exchange.homasapienss.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ExchangeRateDAO implements BaseDAO<ExchangeRate> {

    private static final String SQL_INSERT = "INSERT INTO ExchangeRates (BaseCurrencyId, " +
                                             "TargetCurrencyId, Rate) VALUES (?,?,?)";
    private static final String SQL_SELECT_ALL = "SELECT * FROM ExchangeRates";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM ExchangeRates WHERE id=?";
    private static final String SQL_SELECT_BY_CODE = "SELECT * FROM exchangeRates WHERE " +
                                                     "BaseCurrencyId = ? AND TargetCurrencyId = ?";
    private static final String SQL_UPDATE = "UPDATE ExchangeRates SET BaseCurrencyId=?, " +
                                             "TargetCurrencyId=?, Rate=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM ExchangeRates WHERE id=?";

    public Optional<ExchangeRate> getByIdPair(Integer baseCurrency, Integer targetCurrency)
            throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_CODE)) {
            stmt.setInt(1, baseCurrency);
            stmt.setInt(2, targetCurrency);
            try (ResultSet resultSet = stmt.executeQuery()) {
                return getExchangeRate(resultSet);
            }
        }
    }

    @Override public Optional<ExchangeRate> getById(Integer id)
            throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                return getExchangeRate(resultSet);
            }
        }
    }

    private Optional<ExchangeRate> getExchangeRate(ResultSet resultSet)
            throws SQLException {
        return resultSet.next() ? Optional.of(extractExchangeRate(resultSet)) : Optional.empty();
    }

    @Override public List<ExchangeRate> getAll()
            throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet resultSet = stmt.executeQuery()) {
            List<ExchangeRate> exchangeRates = new ArrayList<>();
            while (resultSet.next()) exchangeRates.add(extractExchangeRate(resultSet));
            return exchangeRates;
        }
    }

    private static ExchangeRate extractExchangeRate(ResultSet resultSet)
            throws SQLException {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setId(resultSet.getInt("id"));
        exchangeRate.setBaseCurrency(resultSet.getInt("BaseCurrencyId"));
        exchangeRate.setTargetCurrency(resultSet.getInt("TargetCurrencyId"));
        exchangeRate.setRate(resultSet.getBigDecimal("Rate"));
        return exchangeRate;
    }

    @Override public Integer save(ExchangeRate entity)
            throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            stmt.setInt(1, entity.getBaseCurrency());
            stmt.setInt(2, entity.getTargetCurrency());
            stmt.setBigDecimal(3, entity.getRate());
            return stmt.executeUpdate();
        }
    }

    @Override public Integer update(ExchangeRate entity)
            throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            stmt.setInt(1, entity.getBaseCurrency());
            stmt.setInt(2, entity.getTargetCurrency());
            stmt.setBigDecimal(3, entity.getRate());
            stmt.setInt(4, entity.getId());
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
