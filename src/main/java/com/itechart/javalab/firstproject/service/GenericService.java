package com.itechart.javalab.firstproject.service;

import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public interface GenericService<T> {
    void delete(long id) throws SQLException;
}
