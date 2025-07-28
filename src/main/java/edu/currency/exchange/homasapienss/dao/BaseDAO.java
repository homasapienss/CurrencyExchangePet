package edu.currency.exchange.homasapienss.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BaseDAO<T> {
    Optional<T> getById(Integer id) throws SQLException;
    List<T> getAll() throws SQLException ;
    Integer save(T entity) throws SQLException;
    Integer update(T entity) throws SQLException ;
    Integer delete(Integer id) throws SQLException;
}