package com.pulsar.habitica.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<I, T> {

    List<T> findAll();

    Optional<T> findById(I id);

    T save(T entity);

    T update(T entity);

    boolean deleteById(I id);

    boolean delete(T entity);
}
