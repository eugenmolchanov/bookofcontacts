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
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 23.09.2017.
 */
public class MessageDaoImplTest {

    private ContactDao dao = ContactDaoImpl.getInstance();
    private PhotoDao photoDao = PhotoDaoImpl.getInstance();
    private ContactDao contactDao = ContactDaoImpl.getInstance();
    private MessageDao messageDao = MessageDaoImpl.getInstance();
    private Connection connection;
    private Message message;
    private Contact firstContact;
    private Contact secondContact;

    @Before
    public void beforeTesting() throws SQLException {
        connection = Database.getConnection();
        Photo photo = new Photo();
        long photoId = photoDao.save(photo, connection);
        photo.setId(photoId);
        firstContact = createContact("name", "email@gmail.com");
        firstContact.setPhoto(photo);
        secondContact = createContact("another_name", "anotheremail@gmail.com");
        secondContact.setPhoto(photo);
        long firstContactId = contactDao.save(firstContact, connection);
        long secondContactId = contactDao.save(secondContact, connection);
        firstContact.setId(firstContactId);
        secondContact.setId(secondContactId);
        message = createMessage(firstContact, "some text");
    }

    @After
    public void afterTesting() throws SQLException {
        dao.deleteAll(connection);
    }

    @Test
    public void shouldSaveAndAddDependencyFromContactAndFindMessage() throws SQLException {
        long id = messageDao.save(message, connection);
        Message message = messageDao.findById(id, connection);
        Assert.assertEquals("topic", message.getTopic());
    }

    @Test
    public void shouldDeleteMessage() throws SQLException {
        long id = messageDao.save(message, connection);
        messageDao.delete(id, connection);
        Message message = messageDao.findById(1, connection);
        Assert.assertEquals("topic", message.getTopic());
    }

    @Test
    public void shouldGetNumberOfAllMessages() throws SQLException {
        messageDao.save(message, connection);
        long number = messageDao.getNotDeletedMessagesNumber(connection);
        Assert.assertEquals(1, number);
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
        messageDao.save(message, connection);
        Message secondMessage = createMessage(secondContact, "text of the message");
        messageDao.save(secondMessage, connection);
        Set<Message> messages = messageDao.getNotDeletedMessages(0, 10, connection);
        Assert.assertEquals(2, messages.size());
    }

    @Test
    public void shouldGetDeletedMessages() throws SQLException {
        long id = messageDao.save(message, connection);
        messageDao.delete(id, connection);
        Message secondMessage = createMessage(firstContact, "another message");
        long anId = messageDao.save(secondMessage, connection);
        messageDao.delete(anId, connection);
        Set<Message> messages = messageDao.getDeletedMessages(0, 10, connection);
        Assert.assertEquals(2, messages.size());
    }

    private Contact createContact(String firstName, String email) {
        Contact contact = new Contact();
        contact.setId(0);
        contact.setFirstName(firstName);
        contact.setLastName("surname");
        contact.setBirthday(Date.valueOf(LocalDate.of(1990, 10, 10)));
        contact.setGender("male");
        contact.setEmail(email);
        return contact;
    }

    private Message createMessage(Contact addressee, String text) {
        message = new Message();
        message.setId(0);
        message.setTopic("topic");
        message.setAddressee(addressee);
        message.setText(text);
        message.setSendingDate(Timestamp.valueOf(LocalDateTime.now()));
        message.setIsDeleted(0);
        return message;
    }
}
