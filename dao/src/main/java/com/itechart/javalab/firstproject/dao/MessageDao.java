package com.itechart.javalab.firstproject.dao;

import java.sql.Connection;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public interface MessageDao<T> extends GenericDao<MessageDao> {
    /**
     * Method returns all messages stored in util.
     * @return
     */
    Set<T> getAll(Connection connection);
}
