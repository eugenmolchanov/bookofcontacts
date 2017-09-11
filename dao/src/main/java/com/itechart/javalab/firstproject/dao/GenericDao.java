package com.itechart.javalab.firstproject.dao;

import java.sql.SQLException;

/**
 * Created by Евгений Молчанов on 06.09.2017.
 */
public interface GenericDao<T> {
    /**
     * Method deletes entity from util by id.
     * @param id
     */
    void delete(long id) throws SQLException;
}
