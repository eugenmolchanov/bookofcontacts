package com.itechart.javalab.firstproject.service.impl;

import com.itechart.javalab.firstproject.dao.MessageDao;
import com.itechart.javalab.firstproject.dao.impl.MessageDaoImpl;
import com.itechart.javalab.firstproject.entities.Message;
import com.itechart.javalab.firstproject.service.MessageService;
import com.itechart.javalab.firstproject.service.database.Database;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import static com.itechart.javalab.firstproject.service.database.Database.*;

/**
 * Created by Yauhen Malchanau on 24.09.2017.
 */
public class MessageServiceImpl implements MessageService {

    private static Logger logger = Logger.getLogger(MessageServiceImpl.class);
    private static MessageServiceImpl instance;
    private MessageDao messageDao = MessageDaoImpl.getInstance();
    private static final Object lock = new Object();

    private MessageServiceImpl() {
    }

    public static MessageService getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new MessageServiceImpl();
            }
        }
        return instance;
    }

    @Override
    public void delete(long id) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            messageDao.delete(id, connection);
        } catch (SQLException e) {
            logger.error("Can't delete message by id.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void save(Message message) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            messageDao.save(message, connection);
        } catch (SQLException e) {
            logger.error("Can't save message.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Message findById(long id) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            return messageDao.findById(id, connection);
        } catch (SQLException e) {
            logger.error("Can't find message by id.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Set<Message> getNotDeletedMessages(long startContactNumber, long quantityOfContacts) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            return messageDao.getNotDeletedMessages(startContactNumber, quantityOfContacts, connection);
        } catch (SQLException e) {
            logger.error("Can't get messages.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public long getNotDeletedMessagesNumber() throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            return messageDao.getNotDeletedMessagesNumber(connection);
        } catch (SQLException e) {
            logger.error("Can't get number of all messages.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Set<Message> getDeletedMessages(long startContactNumber, long quantityOfContacts) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            return messageDao.getDeletedMessages(startContactNumber, quantityOfContacts, connection);
        } catch (SQLException e) {
            logger.error("Can't get deleted messages.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public long getDeletedMessagesNumber() throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            return messageDao.getNumberOfAllDeletedMessages(connection);
        } catch (SQLException e) {
            logger.error("Can't get number of all deleted messages.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void sendMessagesToBucket(Set<Long> messageIds) throws SQLException {
        Connection connection = null;
        try {
            connection = getDisabledAutoCommitConnection();
            for (Long id : messageIds) {
                messageDao.delete(id, connection);
            }
            commitConnection(connection);
        } catch (SQLException e) {
            rollbackConnection(connection);
            logger.error("Can't delete group of messages.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void remove(Set<Long> messageIds) throws SQLException {
        Connection connection = null;
        try {
            connection = getDisabledAutoCommitConnection();
            for (Long id : messageIds) {
                messageDao.remove(id, connection);
            }
            commitConnection(connection);
        } catch (SQLException e) {
            rollbackConnection(connection);
            logger.error("Can't delete group of messages from bucket.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void restore(Set<Long> messageIds) throws SQLException {
        Connection connection = null;
        try {
            connection = getDisabledAutoCommitConnection();
            for (Long id : messageIds) {
                messageDao.restore(id, connection);
            }
            commitConnection(connection);
        } catch (SQLException e) {
            rollbackConnection(connection);
            logger.error("Can't restore group of messages.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }
}
