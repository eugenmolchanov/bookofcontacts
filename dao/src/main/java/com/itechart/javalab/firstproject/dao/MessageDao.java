package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.entities.Message;

import java.sql.Connection;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public interface MessageDao extends GenericDao<Message> {
    /**
     * Method returns all messages stored in util.
     *
     * @param connection connection to database
     * @return collection of messages
     */
    Set<Message> getAll(Connection connection);
}
