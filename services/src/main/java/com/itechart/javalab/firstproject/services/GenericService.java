package com.itechart.javalab.firstproject.services;

import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public interface GenericService<T> {
    /**
     * Method deletes entity from util by id.
     *
     * @param id id of entity which should be deleted
     * @throws SQLException
     */
    void delete(long id) throws SQLException;
}
