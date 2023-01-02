package com.Models.Repositories;


import java.sql.SQLException;
import java.util.Set;

public interface Repository<T> {

    Set<T> getAll();

    T get(int id);

    Integer insert(T object);

    void delete(Integer id);

    void deleteALL();

}
