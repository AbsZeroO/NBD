package org.example.repositories;

import java.util.List;

public interface IRepo<T> {

    /* CRUD */
    boolean Add(T entity);
    T Find(long id);
    List<T> getAll();
    boolean Update(T entity);
    boolean Delete(int id);


}
