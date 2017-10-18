package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.dao.database.Database;
import com.itechart.javalab.firstproject.dao.impl.ContactDaoImpl;
import com.itechart.javalab.firstproject.dao.impl.MessageDaoImpl;
import com.itechart.javalab.firstproject.dao.impl.PhotoDaoImpl;
import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.entities.Message;
import com.itechart.javalab.firstproject.entities.Photo;
import org.junit.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 23.09.2017.
 */
//@Ignore
public class MessageDaoImplTest {

    private ContactDao dao = ContactDaoImpl.getInstance();
    private PhotoDao photoDao = PhotoDaoImpl.getInstance();
    private ContactDao contactDao = ContactDaoImpl.getInstance();
    private MessageDao messageDao = MessageDaoImpl.getInstance();
    private Connection connection;
    private long firstContactId;
    private long secondContactId;
    private Message message;
    private Contact firstContact;
    private Contact secondContact;

    @Before
    public void beforeTesting() throws SQLException {
        connection = Database.getConnection();
        Photo photo = new Photo();
        long photoId = photoDao.save(photo, connection);
        photo.setId(photoId);
        firstContact = new Contact(0, "name", "surname", null, Date.valueOf(LocalDate.of(1990, 10, 10)), null, null, null, null, null, null, null, null, null, null, null,
                0, 0, new HashSet<>(), new HashSet<>(), photo);
        secondContact = new Contact(0, "an_name", "surname", null, Date.valueOf(LocalDate.of(1990, 10, 10)), null, null, null, null, null, null, null, null, null, null, null, 0, 0, new HashSet<>(), new HashSet<>(),
                photo);
        firstContactId = contactDao.save(firstContact, connection);
//        secondContactId = contactDao.save(secondContact, connection);
        message = new Message(0, "topic", firstContact, "text of message", Timestamp.valueOf(LocalDateTime.now()), 0);
    }

    @After
    public void afterTesting() throws SQLException {
//        dao.deleteAll(connection);
    }

    @Test
    public void shouldSaveAndAddDependencyFromContactAndFindMessage() throws SQLException {
        long id = messageDao.save(message, connection);
//        messageDao.addDependencyFromContact(id, firstContactId, connection);
//        messageDao.addDependencyFromContact(id, secondContactId, connection);
        Message message = messageDao.findById(id, connection);
        Assert.assertEquals("topic", message.getTopic());
    }

    @Test
    public void shouldDeleteMessage() throws SQLException {
        long id = messageDao.save(message, connection);
        messageDao.delete(id, connection);
        Message message = messageDao.findById(1, connection);
        Assert.assertNull(message);
    }

    @Test
    public void shouldGetNumberOfAllMessages() throws SQLException {
        messageDao.save(message, connection);
        long number = messageDao.getNumberOfAllMessages(connection);
        Assert.assertEquals(2, number);
    }

    @Test
    public void shouldGetNumberOfAllDeletedMessages() throws SQLException {
        long id = messageDao.save(message, connection);
        messageDao.delete(id, connection);
        long number = messageDao.getNumberOfAllDeletedMessages(connection);
        Assert.assertEquals(1, number);
    }

    @Test
    public void shouldGetMessages() throws SQLException {
//        long id = messageDao.save(message, connection);
//        messageDao.addDependencyFromContact(id, firstContactId, connection);
//        messageDao.addDependencyFromContact(id, secondContactId, connection);
//        Message secondMessage = new Message(0, "anotherTopic", contacts, "text of message1", Timestamp.valueOf(LocalDateTime.now()), 0);
//        long anId = messageDao.save(secondMessage, connection);
//        messageDao.addDependencyFromContact(anId, firstContactId, connection);
//        messageDao.addDependencyFromContact(anId, secondContactId, connection);
        Set<Message> messages = messageDao.getMessages(0, 10, connection);
        Assert.assertEquals(2, messages.size());
        for (Message message : messages) {
//            Assert.assertEquals(2, message.getAddressees().size());
        }
    }

    @Test
    public void shouldGetDeletedMessages() throws SQLException {
        long id = messageDao.save(message, connection);
//        messageDao.addDependencyFromContact(id, firstContactId, connection);
//        messageDao.addDependencyFromContact(id, secondContactId, connection);
        messageDao.delete(id, connection);
        Set<Contact> contacts = new HashSet<>();
        contacts.add(firstContact);
        contacts.add(secondContact);
//        Message secondMessage = new Message(0, "anotherTopic", contacts, "text of message1", Timestamp.valueOf(LocalDateTime.now()), 0);
//        long anId = messageDao.save(secondMessage, connection);
//        messageDao.addDependencyFromContact(anId, firstContactId, connection);
//        messageDao.addDependencyFromContact(anId, secondContactId, connection);
//        messageDao.delete(anId, connection);
        Set<Message> messages = messageDao.getDeletedMessages(0, 10, connection);
        Assert.assertEquals(2, messages.size());
        for (Message message : messages) {
//            Assert.assertEquals(2, message.getAddressees().size());
        }
    }
}
