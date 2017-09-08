package com.itechart.javalab.firstproject.dao;

import java.sql.SQLException;

/**
 * Created by Евгений Молчанов on 06.09.2017.
 */
public interface GenericDao<T> {
    /**
     * Method saves entity in database.
     * @param entity
     * @throws SQLException
     */
    void save(T entity) throws SQLException;

    /**
     * Method finds entity by id.
     * @param id
     * @return
     */
    T findById(long id) throws SQLException;

    /**
     * Method updates entity.
     * @param entity
     */
    void update(T entity) throws SQLException;

    /**
     * Method deletes entity from database by id.
     * @param id
     */
    void delete(long id) throws SQLException;
}
