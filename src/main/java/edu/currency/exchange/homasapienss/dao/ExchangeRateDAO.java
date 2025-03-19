package edu.currency.exchange.homasapienss.dao;

import edu.currency.exchange.homasapienss.entities.ExchangeRate;
import edu.currency.exchange.homasapienss.utils.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDAO implements BaseDAO<ExchangeRate> {
    CurrencyDAO currencyDAO = new CurrencyDAO();

    private PreparedStatement statementGetAll = ConnectionManager.prepareStatement(
            "SELECT * FROM ExchangeRates"
    );
    private PreparedStatement statementGetById = ConnectionManager.prepareStatement(
            "SELECT * FROM ExchangeRates WHERE id=?"
    );
    private PreparedStatement statementGetByCodePair = ConnectionManager.prepareStatement(
            "SELECT * FROM exchangeRates WHERE BaseCurrencyId = ? AND TargetCurrencyId = ?;"
    );
    private PreparedStatement statementCreate = ConnectionManager.prepareStatement(
            "INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (?,?,?)"
    );
    private PreparedStatement statementUpdate = ConnectionManager.prepareStatement(
            "UPDATE ExchangeRates SET BaseCurrencyId=?, TargetCurrencyId=?, Rate=? WHERE id=?"
    );
    private PreparedStatement statementDelete = ConnectionManager.prepareStatement(
            "DELETE FROM ExchangeRates WHERE id=?"
    );

    public Optional<ExchangeRate> getByCodePair(String baseCurrency, String targetCurrency){
        try {
            statementGetByCodePair.setInt(1, currencyDAO.getByCode(baseCurrency).get().getId());
            statementGetByCodePair.setInt(2, currencyDAO.getByCode(targetCurrency).get().getId());
            return getExchangeRate(statementGetByCodePair.executeQuery());
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override public Optional<ExchangeRate> getById(int id) {
        try {
            statementGetById.setInt(1, id);
            return  getExchangeRate(statementGetById.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<ExchangeRate> getExchangeRate(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return Optional.empty();
        } else {
            return Optional.of(extractExchangeRate(resultSet));
        }
    }

    @Override public List<ExchangeRate> getAll() {
        List<ExchangeRate> listOfRates = new ArrayList<>();
        try {
            ResultSet resultSet = statementGetAll.executeQuery();
            while (resultSet.next()) {
                listOfRates.add(extractExchangeRate(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfRates;
    }

    private static ExchangeRate extractExchangeRate(ResultSet resultSet) {
        ExchangeRate exchangeRate = new ExchangeRate();
        try {
            exchangeRate.setId(resultSet.getInt("id"));
            exchangeRate.setBaseCurrency(resultSet.getInt("BaseCurrencyId"));
            exchangeRate.setTargetCurrency(resultSet.getInt("TargetCurrencyId"));
            exchangeRate.setRate(resultSet.getBigDecimal("Rate"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exchangeRate;
    }

    @Override public ExchangeRate create(ExchangeRate entity) {
        try {
            statementCreate.setInt(1, entity.getBaseCurrency());
            statementCreate.setInt(2, entity.getTargetCurrency());
            statementCreate.setBigDecimal(3, entity.getRate());
            statementCreate.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override public ExchangeRate update(ExchangeRate entity) {
        try {
            statementUpdate.setInt(1, entity.getBaseCurrency());
            statementUpdate.setInt(2, entity.getTargetCurrency());
            statementUpdate.setBigDecimal(3, entity.getRate());
            statementUpdate.setInt(4, entity.getId());
            statementUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override public void delete(int id) {
        try {
            statementDelete.setInt(1, id);
            statementDelete.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
