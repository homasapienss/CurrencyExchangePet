package edu.currency.exchange.homasapienss.dao;

import edu.currency.exchange.homasapienss.exceptions.ApplicationException;

import java.util.List;
import java.util.Optional;

public interface BaseDAO<T> {
    Optional<T> getById(int id);
    List<T> getAll()
            throws ApplicationException;
    T create(T entity);
    T update(T entity);
    void delete(int id);
}
