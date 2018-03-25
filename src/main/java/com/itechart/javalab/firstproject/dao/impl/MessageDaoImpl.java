package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.MessageDao;
import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.entities.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import static com.itechart.javalab.firstproject.dao.util.DatabaseOperation.*;
import static com.itechart.javalab.firstproject.dao.util.FieldNameConstant.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Created by Yauhen Malchanau on 23.09.2017.
 */
public class MessageDaoImpl implements MessageDao {

    private MessageDaoImpl() {
    }

    private static MessageDaoImpl instance;
    private static final Object lock = new Object();

    private static final String SAVE_MESSAGE = "insert into message (topic, message, sending_date, contact_id) " +
            "values (?, ?, ?, ?);";
    private static final String GET_MESSAGE_BY_ID = "select m.id, m.topic, m.message, m.sending_date, c.id, c.first_name, " +
            "c.last_name, c.email from message as m inner join contact as c on m.contact_id=c.id where m.id = ?;";
    private static final String REMOVE_MESSAGE = "update message set sending_date = sending_date, is_deleted = 1 where id = ?";
    private static final String GET_ALL_NOT_IN_BUCKET_MESSAGES = "select m.id, m.topic, m.message, m.sending_date, c.id, " +
            "c.first_name, c.last_name, c.email from message as m inner join contact as c on m.contact_id=c.id " +
            "where m.is_deleted = 0 order by m.id desc limit ?, ?;";
    private static final String GET_NUMBER_OF_ALL_NOT_DELETED_MESSAGES = "select count(id) from message where is_deleted = 0";
    private static final String GET_ALL_DELETED_MESSAGES = "select m.id, m.topic, m.message, m.sending_date, c.id, " +
            "c.first_name, c.last_name, c.email from message as m inner join contact as c on m.contact_id=c.id " +
            "where m.is_deleted = 1 order by m.id desc limit ?, ?;";
    private static final String GET_NUMBER_OF_ALL_DELETED_MESSAGES = "select count(id) from message where is_deleted = 1";
    private static final String DELETE_MESSAGE = "delete from message where id = ?";
    private static final String RESTORE_MESSAGE = "update message set sending_date = sending_date, is_deleted = 0 where id = ?";

    public static MessageDao getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new MessageDaoImpl();
            }
        }
        return instance;
    }

    @Override
    public long save(Message entity, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_MESSAGE, RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getTopic());
            statement.setString(2, entity.getText());
            statement.setTimestamp(3, entity.getSendingDate());
            statement.setLong(4, entity.getAddressee().getId());
            statement.executeUpdate();
            return getGeneratedId(statement);
        }
    }

    @Override
    public Message findById(long id, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_MESSAGE_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                Message message = new Message();
                while (resultSet.next()) {
                    initializeMessage(message, resultSet);
                }
                return message;
            }
        }
    }

    @Override
    public void update(Message entity, Connection connection) throws SQLException {
        //message shouldn't be updated
    }

    @Override
    public void delete(long id, Connection connection) throws SQLException {
        executeUpdateById(id, REMOVE_MESSAGE, connection);
    }

    @Override
    public Set<Message> getNotDeletedMessages(long startContactNumber, long quantityOfContacts, Connection connection) throws SQLException {
        return getMessages(startContactNumber, quantityOfContacts, GET_ALL_NOT_IN_BUCKET_MESSAGES, connection);
    }

    @Override
    public long getNotDeletedMessagesNumber(Connection connection) throws SQLException {
        return getRecordsNumber(GET_NUMBER_OF_ALL_NOT_DELETED_MESSAGES, connection);
    }

    @Override
    public Set<Message> getDeletedMessages(long startContactNumber, long quantityOfContacts,
                                           Connection connection) throws SQLException {
        return getMessages(startContactNumber, quantityOfContacts, GET_ALL_DELETED_MESSAGES, connection);
    }

    @Override
    public long getNumberOfAllDeletedMessages(Connection connection) throws SQLException {
        return getRecordsNumber(GET_NUMBER_OF_ALL_DELETED_MESSAGES, connection);
    }

    @Override
    public void remove(long id, Connection connection) throws SQLException {
        executeUpdateById(id, DELETE_MESSAGE, connection);
    }

    @Override
    public void restore(long id, Connection connection) throws SQLException {
        executeUpdateById(id, RESTORE_MESSAGE, connection);
    }

    private void initializeMessage(Message message, ResultSet resultSet) throws SQLException {
        Contact contact = new Contact();
        contact.setId(resultSet.getLong(CONTACT_ID));
        contact.setFirstName(resultSet.getString(FIRST_NAME));
        contact.setLastName(resultSet.getString(LAST_NAME));
        contact.setEmail(resultSet.getString(EMAIL));

        message.setId(resultSet.getLong(MESSAGE_ID));
        message.setTopic(resultSet.getString(TOPIC));
        message.setText(resultSet.getString(MESSAGE));
        message.setSendingDate(resultSet.getTimestamp(SENDING_DATE));
        message.setAddressee(contact);
    }

    private Set<Message> getMessages(long startContactNumber, long quantityOfContacts, String query,
                                     Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, startContactNumber);
            statement.setLong(2, quantityOfContacts);
            try (ResultSet resultSet = statement.executeQuery()) {
                TreeSet<Message> messages = new TreeSet<>(Comparator.comparing(Message::getId).reversed());
                while (resultSet.next()) {
                    Message message = new Message();
                    initializeMessage(message, resultSet);
                    messages.add(message);
                }
                return messages;
            }
        }
    }
}
