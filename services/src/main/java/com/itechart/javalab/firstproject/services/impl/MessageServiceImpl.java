package com.itechart.javalab.firstproject.services.impl;

import com.itechart.javalab.firstproject.dao.MessageDao;
import com.itechart.javalab.firstproject.dao.impl.MessageDaoImpl;
import com.itechart.javalab.firstproject.entities.Message;
import com.itechart.javalab.firstproject.services.MessageService;
import com.itechart.javalab.firstproject.services.database.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 24.09.2017.
 */
public class MessageServiceImpl implements MessageService {

    private static MessageServiceImpl instance;
    private MessageDao messageDao = MessageDaoImpl.getInstance();

    private MessageServiceImpl() {
    }

    public static MessageService getInstance() {
        if (instance == null) {
            instance = new MessageServiceImpl();
        }
        return instance;
    }

    @Override
    public void delete(long id) throws SQLException {
        Connection connection = Database.getConnection();
        messageDao.delete(id, connection);
        connection.close();
    }

    @Override
    public void save(Message message, Set<Long> contactIds) throws SQLException {
        Connection connection = Database.getConnection();
        connection.setAutoCommit(false);
        try {
            long messageId = messageDao.save(message, connection);
            for (Long id : contactIds) {
                messageDao.addDependencyFromContact(messageId, id, connection);
            }
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
    }

    @Override
    public Message findById(long id) throws SQLException {
        Connection connection = Database.getConnection();
        Message message = messageDao.findById(id, connection);
        connection.close();
        return message;
    }

    @Override
    public Set<Message> getMessages(long startContactNumber, long quantityOfContacts) throws SQLException {
        Connection connection = Database.getConnection();
        Set<Message> messages = messageDao.getMessages(startContactNumber, quantityOfContacts, connection);
        connection.close();
        return messages;
    }

    @Override
    public long getNumberOfAllMessages() throws SQLException {
        Connection connection = Database.getConnection();
        long number = messageDao.getNumberOfAllMessages(connection);
        connection.close();
        return number;
    }

    @Override
    public Set<Message> getDeletedMessages(long startContactNumber, long quantityOfContacts) throws SQLException {
        Connection connection = Database.getConnection();
        Set<Message> messages = messageDao.getDeletedMessages(startContactNumber, quantityOfContacts, connection);
        connection.close();
        return messages;
    }

    @Override
    public long getNumberOfAllDeletedMessages() throws SQLException {
        Connection connection = Database.getConnection();
        long number = messageDao.getNumberOfAllDeletedMessages(connection);
        connection.close();
        return number;
    }
}
