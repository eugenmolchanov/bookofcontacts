package com.itechart.javalab.firstproject.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public interface GenericDao<T> {
    /**
     * Method saves entity.
     *
     * @param entity     object which should be saved
     * @param connection connection to database
     * @return generated id after adding a new row
     * @throws SQLException
     */
    long save(T entity, Connection connection) throws SQLException;

    /**
     * Method finds entity by id.
     *
     * @param id         id of entity
     * @param connection connection to database
     * @return entity object
     * @throws SQLException
     */
    T findById(long id, Connection connection) throws SQLException;

    /**
     * Method updates entity.
     *
     * @param entity     object which should be updated
     * @param connection connection to database
     * @throws SQLException
     */
    void update(T entity, Connection connection) throws SQLException;

    /**
     * Method deletes entity by it's id.
     *
     * @param id         id of entity
     * @param connection connection to database
     * @throws SQLException
     */
    void delete(long id, Connection connection) throws SQLException;
}
