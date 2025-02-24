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

    private static PreparedStatement StatementGetAll = ConnectionManager.prepareStatement(
            "SELECT * FROM ExchangeRates"
    );
    private static PreparedStatement StatementGetById = ConnectionManager.prepareStatement(
            "SELECT * FROM ExchangeRates WHERE id=?"
    );
    private static PreparedStatement StatementCreate = ConnectionManager.prepareStatement(
            "INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (?,?,?)"
    );
    private static PreparedStatement StatementUpdate = ConnectionManager.prepareStatement(
            "UPDATE ExchangeRates SET BaseCurrencyId=?, TargetCurrencyId=?, Rate=? WHERE id=?"
    );
    private static PreparedStatement StatementDelete = ConnectionManager.prepareStatement(
            "DELETE FROM ExchangeRates WHERE id=?"
    );


    @Override public Optional<ExchangeRate> getById(int id) {
        return Optional.empty();
    }

    @Override public List<ExchangeRate> getAll() {
        List<ExchangeRate> listOfRates = new ArrayList<>(List.of());
        try {
            ResultSet resultSet = StatementGetAll.executeQuery();
            while (resultSet.next()) {
                listOfRates.add(extractExchangeRate(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfRates;
    }

    private static ExchangeRate extractExchangeRate(ResultSet resultSet) {
        ExchangeRate exchangeRate;
        try {
            exchangeRate = new ExchangeRate(
                    resultSet.getInt("Id"),
                    resultSet.getInt("BaseCurrencyId"),
                    resultSet.getInt("TargetCurrencyId"),
                    resultSet.getBigDecimal("Rate")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exchangeRate;
    }

    @Override public ExchangeRate create(ExchangeRate entity) {
        return null;
    }

    @Override public ExchangeRate update(ExchangeRate entity) {
        return null;
    }

    @Override public void delete(int id) {}


}
