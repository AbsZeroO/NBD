package org.example.repo;

import java.util.List;

public interface IRepo<T> {

    /* CRUD */
    boolean add(T entity);
    T findById(int id);
    List<T> findAll();
    boolean update(T entity);
    boolean delete(T entity);
}
