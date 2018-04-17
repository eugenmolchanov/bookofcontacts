package com.itechart.javalab.firstproject.service;

import com.itechart.javalab.firstproject.entities.Message;

import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 24.09.2017.
 */
public interface MessageService extends GenericService<Message> {
    void save(Message message) throws SQLException;

    Message findById(long id) throws SQLException;

    Set<Message> getNotDeletedMessages(long startContactNumber, long quantityOfContacts) throws SQLException;

    long getNotDeletedMessagesNumber() throws SQLException;

    Set<Message> getDeletedMessages(long startContactNumber, long quantityOfContacts) throws SQLException;

    long getDeletedMessagesNumber() throws SQLException;

    void sendMessagesToBucket(Set<Long> messageIds) throws SQLException;

    void remove(Set<Long> messageIds) throws SQLException;

    void restore(Set<Long> messageIds) throws SQLException;
}
