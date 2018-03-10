package com.itechart.javalab.firstproject.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public interface GenericDao<T> {
    long save(T entity, Connection connection) throws SQLException;

    T findById(long id, Connection connection) throws SQLException;

    void update(T entity, Connection connection) throws SQLException;

    void delete(long id, Connection connection) throws SQLException;
}
