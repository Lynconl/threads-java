package br.com.teste.weon.dao;

import java.util.List;

public interface DAO<T> {

    T get(long id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}
