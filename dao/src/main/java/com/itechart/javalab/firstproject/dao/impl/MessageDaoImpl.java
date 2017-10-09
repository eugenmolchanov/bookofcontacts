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

/**
 * Created by Yauhen Malchanau on 23.09.2017.
 */
public class MessageDaoImpl implements MessageDao {

    private static MessageDaoImpl instance;

    private MessageDaoImpl() {
    }

    public static MessageDao getInstance() {
        if (instance == null) {
            instance = new MessageDaoImpl();
        }
        return instance;
    }

    @Override
    public long save(Message entity, Connection connection) throws SQLException {
        final String SAVE_MESSAGE = "insert into message (topic, message, sending_date) values (?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(SAVE_MESSAGE, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, entity.getTopic());
        statement.setString(2, entity.getText());
        statement.setTimestamp(3, entity.getSendingDate());
        statement.executeUpdate();
        long id = Util.getGeneratedIdAfterCreate(statement);
        statement.close();
        return id;
    }

    @Override
    public Message findById(long id, Connection connection) throws SQLException {
        final String GET_MESSAGE_BY_ID = "select m.topic, m.message, m.sending_date, c.id, c.first_name, c.last_name, c.email from message as m inner join contact_message " +
                "as cm on m.id=cm.message_id inner join contact as c on cm.contact_id=c.id where m.id = ? and m.is_deleted = 0;";
        PreparedStatement statement = connection.prepareStatement(GET_MESSAGE_BY_ID);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        Message message = null;
        Set<Contact> contacts = new HashSet<>();
        while (resultSet.next()) {
            Contact contact = new Contact();
            contact.setId(resultSet.getLong("c.id"));
            contact.setFirstName(resultSet.getString("c.first_name"));
            contact.setLastName(resultSet.getString("c.last_name"));
            contact.setEmail(resultSet.getString("c.email"));
            contacts.add(contact);
            message = new Message();
            message.setId(id);
            message.setTopic(resultSet.getString("m.topic"));
            message.setAddressees(contacts);
            message.setText(resultSet.getString("m.message"));
            message.setSendingDate(resultSet.getTimestamp("m.sending_date"));
        }
        statement.close();
        return message;
    }

    @Override
    public void update(Message entity, Connection connection) throws SQLException {

    }

    @Override
    public void delete(long id, Connection connection) throws SQLException {
        final String DELETE_MESSAGE = "update message set is_deleted = 1 where id = ?";
        PreparedStatement statement = connection.prepareStatement(DELETE_MESSAGE);
        statement.setLong(1, id);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public Set<Message> getMessages(long startContactNumber, long quantityOfContacts, Connection connection) throws SQLException {
        final String GET_ALL_NOT_IN_BUCKET_MESSAGES = "select m.id, m.topic, m.message, m.sending_date, c.id, c.first_name, c.last_name, c.email from message as m inner join " +
                "contact_message as cm on m.id=cm.message_id inner join contact as c on cm.contact_id=c.id where is_deleted = 0 order by sending_date desc limit ?, ?;";
        PreparedStatement statement = connection.prepareStatement(GET_ALL_NOT_IN_BUCKET_MESSAGES);
        statement.setLong(1, startContactNumber);
        statement.setLong(2, quantityOfContacts);
        ResultSet resultSet = statement.executeQuery();
        Message message = new Message();
        TreeSet<Message> messages = new TreeSet<>(Comparator.comparing(Message::getId).reversed());
        Set<Contact> contacts = new HashSet<>();
        long messageId = 0;
        while (resultSet.next()) {
            if (resultSet.getLong("m.id") != messageId) {
                if (message.getId() != 0) {
                    messages.add(message);
                }
                message = new Message();
                contacts = new HashSet<>();
            }
            Contact contact = new Contact();
            contact.setId(resultSet.getLong("c.id"));
            contact.setFirstName(resultSet.getString("c.first_name"));
            contact.setLastName(resultSet.getString("c.last_name"));
            contact.setEmail(resultSet.getString("c.email"));
            contacts.add(contact);
            message.setId(resultSet.getLong("id"));
            message.setTopic(resultSet.getString("topic"));
            message.setText(resultSet.getString("m.message"));
            message.setSendingDate(resultSet.getTimestamp("m.sending_date"));
            message.setAddressees(contacts);
            messageId = message.getId();
        }
        messages.add(message);
        statement.close();
        return messages;
    }

    @Override
    public void addDependencyFromContact(long messageId, long contactId, Connection connection) throws SQLException {
        final String ADD_DEPENDENCY_FROM_CONTACT = "insert into contact_message values (?, ?);";
        PreparedStatement statement = connection.prepareStatement(ADD_DEPENDENCY_FROM_CONTACT);
        statement.setLong(1, contactId);
        statement.setLong(2, messageId);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public long getNumberOfAllMessages(Connection connection) throws SQLException {
        final String GET_NUMBER_OF_ALL_NOT_DELETED_MESSAGES = "select count(id) from message where is_deleted = 0 group by id";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_NUMBER_OF_ALL_NOT_DELETED_MESSAGES);
        long number = 0;
        while (resultSet.next()) {
            number = resultSet.getLong("count(id)");
        }
        statement.close();
        return number;
    }

    @Override
    public Set<Message> getDeletedMessages(long startContactNumber, long quantityOfContacts, Connection connection) throws SQLException {
        final String GET_ALL_DELETED_MESSAGES = "select m.id, m.topic, m.message, m.sending_date, c.id, c.first_name, c.last_name, c.email from message as m inner join " +
                "contact_message as cm on m.id=cm.message_id inner join contact as c on cm.contact_id=c.id where is_deleted = 1 order by sending_date desc limit ?, ?;";
        PreparedStatement statement = connection.prepareStatement(GET_ALL_DELETED_MESSAGES);
        statement.setLong(1, startContactNumber);
        statement.setLong(2, quantityOfContacts);
        ResultSet resultSet = statement.executeQuery();
        Message message = new Message();
        TreeSet<Message> messages = new TreeSet<>(Comparator.comparing(Message::getId).reversed());
        Set<Contact> contacts = new HashSet<>();
        long messageId = 0;
        while (resultSet.next()) {
            if (resultSet.getLong("m.id") != messageId) {
                if (message.getId() != 0) {
                    messages.add(message);
                }
                message = new Message();
                contacts.clear();
            }
            Contact contact = new Contact();
            contact.setId(resultSet.getLong("c.id"));
            contact.setFirstName(resultSet.getString("c.first_name"));
            contact.setLastName(resultSet.getString("c.last_name"));
            contact.setEmail(resultSet.getString("c.email"));
            contacts.add(contact);
            message.setId(resultSet.getLong("id"));
            message.setTopic(resultSet.getString("topic"));
            message.setText(resultSet.getString("m.message"));
            message.setSendingDate(resultSet.getTimestamp("m.sending_date"));
            message.setAddressees(contacts);
            messageId = message.getId();
        }
        messages.add(message);
        statement.close();
        return messages;
    }

    @Override
    public long getNumberOfAllDeletedMessages(Connection connection) throws SQLException {
        final String GET_NUMBER_OF_ALL_DELETED_MESSAGES = "select count(id) from message where is_deleted = 1 group by id";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_NUMBER_OF_ALL_DELETED_MESSAGES);
        long number = 0;
        while (resultSet.next()) {
            number = resultSet.getLong("count(id)");
        }
        statement.close();
        return number;
    }
}
