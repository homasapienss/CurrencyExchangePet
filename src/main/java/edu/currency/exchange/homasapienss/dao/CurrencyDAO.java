package edu.currency.exchange.homasapienss.dao;

import edu.currency.exchange.homasapienss.entities.Currency;
import edu.currency.exchange.homasapienss.utils.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDAO implements BaseDAO<Currency> {

    private PreparedStatement stateCurrencyGetAll = ConnectionManager.prepareStatement(
            "SELECT * FROM Currencies"
    );
    private PreparedStatement stateCurrencyGetByCode = ConnectionManager.prepareStatement(
            "SELECT * FROM Currencies WHERE Code=?"
    );
    private PreparedStatement stateCurrencyGetById = ConnectionManager.prepareStatement(
            "SELECT * FROM Currencies WHERE id=?"
    );
    private PreparedStatement stateCurrencyCreate = ConnectionManager.prepareStatement(
            "INSERT INTO Currencies (Code, FullName, Sign) VALUES (?,?,?)"
    );
    private PreparedStatement stateCurrencyUpdate = ConnectionManager.prepareStatement(
            "UPDATE Currencies SET Code=?, FullName=?, Sign=? WHERE id=?"
    );
    private PreparedStatement stateCurrencyDelete = ConnectionManager.prepareStatement(
            "DELETE FROM Currencies WHERE id=?"
    );

    public static Optional<Currency> getCurrency(ResultSet resultSet)
            throws SQLException {
        if (!resultSet.next()){
            return Optional.empty();
        } else {
            return Optional.of(extractCurrency(resultSet));
        }
    }

    @Override public Optional<Currency> getById(int id) {
        try {
            stateCurrencyGetById.setInt(1, id);
            return getCurrency(stateCurrencyGetById.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Optional<Currency> getByCode(String code) {
        try {
            stateCurrencyGetByCode.setString(1, code);
            ResultSet resultSet = stateCurrencyGetByCode.executeQuery();
            return getCurrency(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override public List<Currency> getAll() {
        List<Currency> listOfCurrencies = new ArrayList<>();
        try {
            ResultSet resultSet = stateCurrencyGetAll.executeQuery();
            while (resultSet.next()) {
                listOfCurrencies.add(extractCurrency(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfCurrencies;
    }

    private static Currency extractCurrency(ResultSet resultSet) {
        Currency currency = new Currency();
        try {
            currency.setCode(resultSet.getString("Code"));
            currency.setName(resultSet.getString("FullName"));
            currency.setSign(resultSet.getString("Sign"));
            currency.setId(resultSet.getInt("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currency;
    }

    @Override public Currency create(Currency entity) {
        try {
            stateCurrencyCreate.setString(1, entity.getCode());
            stateCurrencyCreate.setString(2, entity.getName());
            stateCurrencyCreate.setString(3, entity.getSign());
            stateCurrencyCreate.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override public Currency update(Currency entity) {
        try {
            stateCurrencyUpdate.setString(1, entity.getCode());
            stateCurrencyUpdate.setString(2, entity.getName());
            stateCurrencyUpdate.setString(3, entity.getSign());
            stateCurrencyUpdate.setInt(4, entity.getId());
            stateCurrencyUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override public void delete(int id) {
        try {
            stateCurrencyDelete.setInt(1, id);
            stateCurrencyDelete.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
