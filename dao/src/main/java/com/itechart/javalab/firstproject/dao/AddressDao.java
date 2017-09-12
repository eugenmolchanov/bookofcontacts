package com.itechart.javalab.firstproject.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 12.09.2017.
 */
public interface AddressDao<T> extends GenericDao<T> {
    /**
     * Method checks address in database
     * @param entity
     * @return id
     */
    long checkAddress(T entity, Connection connection) throws SQLException;
}
