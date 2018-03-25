package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.entities.Message;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public interface MessageDao extends GenericDao<Message> {
    Set<Message> getNotDeletedMessages(long startContactNumber, long quantityOfContacts,
                                       Connection connection) throws SQLException;

    long getNotDeletedMessagesNumber(Connection connection) throws SQLException;

    Set<Message> getDeletedMessages(long startContactNumber, long quantityOfContacts,
                                    Connection connection) throws SQLException;

    long getNumberOfAllDeletedMessages(Connection connection) throws SQLException;

    void remove(long id, Connection connection) throws SQLException;

    void restore(long id, Connection connection) throws SQLException;
}
