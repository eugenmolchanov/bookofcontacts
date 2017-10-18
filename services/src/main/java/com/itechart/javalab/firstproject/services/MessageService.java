package com.itechart.javalab.firstproject.services;

import com.itechart.javalab.firstproject.entities.Message;

import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 24.09.2017.
 */
public interface MessageService extends GenericService<Message> {
    /**
     * Method saves message.
     *
     * @param message message object which should be saved
     * @throws SQLException
     */
    void save(Message message) throws SQLException;

    /**
     * Method finds message by id.
     *
     * @param id id of message
     * @return message object
     * @throws SQLException
     */
    Message findById(long id) throws SQLException;

    /**
     * Method returns not deleted messages stored in database.
     *
     * @param startContactNumber the first id number of collection for pagination will start after this id
     * @param quantityOfContacts the quantity of messages which is necessary for pagination
     * @return collection of messages
     * @throws SQLException
     */
    Set<Message> getMessages(long startContactNumber, long quantityOfContacts) throws SQLException;

    /**
     * Method returns number of all not deleted messages in database. Used for pagination.
     *
     * @return number of all message objects
     * @throws SQLException
     */
    long getNumberOfAllMessages() throws SQLException;

    /**
     * Method returns deleted messages stored in data base.
     *
     * @param startContactNumber the first id number of collection for pagination will start after this id
     * @param quantityOfContacts the quantity of messages which is necessary for pagination
     * @return collection of deleted messages
     * @throws SQLException
     */
    Set<Message> getDeletedMessages(long startContactNumber, long quantityOfContacts) throws SQLException;

    /**
     * Method returns number of all deleted messages in database. Used for pagination.
     *
     * @return number of all deleted message objects
     * @throws SQLException
     */
    long getNumberOfAllDeletedMessages() throws SQLException;

    /**
     * Method sends messages to bucket by their ids. Soft delete.
     *
     * @param messageIds ids of message objects
     * @throws SQLException
     */
    void sendMessagesToBucket(Set<Long> messageIds) throws SQLException;

    /**
     * Method deletes message form bucket by it's id.
     *
     * @param messageIds ids of message objects
     * @throws SQLException
     */
    void fullDelete(Set<Long> messageIds) throws SQLException;

    /**
     * Method restores message from bucket by it's id.
     *
     * @param messageIds ids of message objects
     * @throws SQLException
     */
    void restore(Set<Long> messageIds) throws SQLException;
}
