package edu.currency.exchange.homasapienss.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDAO<T> {
    Optional<T> getById(int id);
    List<T> getAll();
    T create(T entity);
    T update(T entity);
    void delete(int id);
}
