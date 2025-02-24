package edu.currency.exchange.homasapienss.dao;

import edu.currency.exchange.homasapienss.entities.Currency;
import edu.currency.exchange.homasapienss.utils.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDAO implements BaseDAO<Currency>{

    private static PreparedStatement StateCurrencyGetAll = ConnectionManager.prepareStatement(
            "SELECT * FROM Currencies"
    );
    private static PreparedStatement StateCurrencyGetById = ConnectionManager.prepareStatement(
            "SELECT * FROM Currencies WHERE id=?"
    );
    private static PreparedStatement StateCurrencyCreate = ConnectionManager.prepareStatement(
            "INSERT INTO Currencies (Code, FullName, Sign) VALUES (?,?,?)"
    );
    private static PreparedStatement StateCurrencyUpdate = ConnectionManager.prepareStatement(
            "UPDATE Currencies SET Code=?, FullName=?, Sign=? WHERE id=?"
    );
    private static PreparedStatement StateCurrencyDelete = ConnectionManager.prepareStatement(
            "DELETE FROM Currencies WHERE id=?"
    );

    @Override public Optional<Currency> getById(int id) {
        return Optional.empty();
    }

    @Override public List<Currency> getAll() {
        List<Currency> listOfCurrencies = new ArrayList<>();
        try {
            ResultSet resultSet= StateCurrencyGetAll.executeQuery();
            while (resultSet.next()) {
                listOfCurrencies.add(new Currency(
                        resultSet.getInt("Id"),
                        resultSet.getString("Code"),
                        resultSet.getString("FullName"),
                        resultSet.getString("Sign")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfCurrencies;
    }

    @Override public Currency create(Currency entity) {
        return null;
    }

    @Override public Currency update(Currency entity) {
        return null;
    }

    @Override public void delete(int id) {

    }
}
