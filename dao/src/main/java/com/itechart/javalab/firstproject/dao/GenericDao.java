package com.itechart.javalab.firstproject.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public interface GenericDao<T> {
    /**
     * Method saves entity in util.
     * @param entity
     * @return id
     * @throws SQLException
     */
    long save(T entity, Connection connection) throws SQLException;

    /**
     * Method finds entity by id.
     * @param id
     * @return
     * @throws SQLException
     */
    T findById(long id, Connection connection) throws SQLException;

    /**
     * Method updates entity.
     * @param entity
     * @throws SQLException
     */
    void update(T entity, Connection connection) throws SQLException;
    /**
     * Method deletes entity from util by id.
     * @param id
     */
    void delete(long id, Connection connection) throws SQLException;
}
