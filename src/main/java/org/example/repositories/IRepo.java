package org.example.repositories;

import java.util.List;

public interface IRepo<T> {

    /* CRUD */
    void Add(T entity);
    T Find(long id);
    List<T> getAll();
    void Update(T entity);
    void Delete(T entity);


}
