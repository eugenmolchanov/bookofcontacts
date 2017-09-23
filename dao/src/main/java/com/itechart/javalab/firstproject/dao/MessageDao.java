package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.entities.Message;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public interface MessageDao extends GenericDao<Message> {
    /**
     * Method returns messages stored in database.
     *
     * @param startContactNumber the first id number of collection for pagination will start after this id
     * @param quantityOfContacts the quantity of messages which is necessary for pagination
     * @param connection         connection to database
     * @return collection of messages
     * @throws SQLException
     */
    Set<Message> getMessages(long startContactNumber, long quantityOfContacts, Connection connection) throws SQLException;

    /**
     * Method adds dependency between message and contact objects. Creates row in contact_message table.
     *
     * @param messageId  id of message object
     * @param contactId  id of contact object
     * @param connection connection to database
     * @throws SQLException
     */
    void addDependencyFromContact(long messageId, long contactId, Connection connection) throws SQLException;

    /**
     * Method returns number of all messages in database. Used for pagination.
     *
     * @param connection connection to database
     * @return number of all message objects
     * @throws SQLException
     */
    long getNumberOfAllMessages(Connection connection) throws SQLException;

    /**
     * Method returns deleted messages stored in data base.
     *
     * @param startContactNumber the first id number of collection for pagination will start after this id
     * @param quantityOfContacts the quantity of messages which is necessary for pagination
     * @param connection         connection to database
     * @return collection of deleted messages
     * @throws SQLException
     */
    Set<Message> getDeletedMessages(long startContactNumber, long quantityOfContacts, Connection connection) throws SQLException;

    /**
     * Method returns number of all deleted messages in database. Used for pagination.
     *
     * @param connection connection to database
     * @return number of all deleted message objects
     * @throws SQLException
     */
    long getNumberOfAllDeletedMessages(Connection connection) throws SQLException;
}
