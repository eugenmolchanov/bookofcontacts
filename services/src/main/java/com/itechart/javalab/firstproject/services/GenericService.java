package com.itechart.javalab.firstproject.services;

import java.sql.SQLException;

/**
 * Created by Евгений Молчанов on 11.09.2017.
 */
public interface GenericService<T> {
    /**
     * Method deletes entity from util by id.
     * @param id
     */
    void delete(long id) throws SQLException;
}
