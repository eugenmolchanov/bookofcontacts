package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.MessageDao;
import com.itechart.javalab.firstproject.dao.util.Util;
import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.entities.Message;

import java.sql.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static com.itechart.javalab.firstproject.dao.util.Util.getRecordsNumber;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Created by Yauhen Malchanau on 23.09.2017.
 */
public class MessageDaoImpl implements MessageDao {

    private MessageDaoImpl() {
    }

    private static MessageDaoImpl instance;

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
        if (instance == null) {
            instance = new MessageDaoImpl();
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
            return Util.getGeneratedIdAfterCreate(statement);
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
        try (PreparedStatement statement = connection.prepareStatement(REMOVE_MESSAGE)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
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
        try (PreparedStatement statement = connection.prepareStatement(DELETE_MESSAGE)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public void restore(long id, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(RESTORE_MESSAGE)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    private void initializeMessage(Message message, ResultSet resultSet) throws SQLException {
        Contact contact = new Contact();
        contact.setId(resultSet.getLong("c.id"));
        contact.setFirstName(resultSet.getString("c.first_name"));
        contact.setLastName(resultSet.getString("c.last_name"));
        contact.setEmail(resultSet.getString("c.email"));

        message.setId(resultSet.getLong("m.id"));
        message.setTopic(resultSet.getString("m.topic"));
        message.setText(resultSet.getString("m.message"));
        message.setSendingDate(resultSet.getTimestamp("m.sending_date"));
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
