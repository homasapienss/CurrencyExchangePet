package utils;

import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcConnection {

    public static void main(String[] args) {
        Class<Driver> driverClass = Driver.class;
        ResultSet setResult = null;
        try (var statementSelectAll = ConnectionManager.
                prepareStatement("SELECT * FROM actor ORDER " +
                                                                         "BY id;");
             var statementUpdateMaksym = ConnectionManager.
                     prepareStatement("UPDATE actor SET name = ?" +
                                                                            " WHERE id=1;");
        ){

            statementUpdateMaksym.setString(1,"Ilya");
            statementUpdateMaksym.executeUpdate();
            setResult = statementSelectAll.executeQuery();
            while (setResult.next()) {
                System.out.printf("Human name = %s, age = %d, id= %d\n",
                                  setResult.getString(2),
                                  setResult.getLong(3),
                                  setResult.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
