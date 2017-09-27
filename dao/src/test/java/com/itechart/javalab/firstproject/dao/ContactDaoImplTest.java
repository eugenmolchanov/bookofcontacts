package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.dao.database.Database;
import com.itechart.javalab.firstproject.dao.impl.ContactDaoImpl;
import com.itechart.javalab.firstproject.dao.impl.PhotoDaoImpl;
import com.itechart.javalab.firstproject.entities.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public class ContactDaoImplTest {

    private ContactDao dao = ContactDaoImpl.getInstance();
    private PhotoDao photoDao = PhotoDaoImpl.getInstance();
    private Connection connection;
    private Contact contact;

    @Before
    public void beforeTesting() throws SQLException {
        connection = Database.getConnection();
        Photo photo = new Photo();
        long photoId = photoDao.save(photo, connection);
        photo.setId(photoId);
        contact = new Contact(0, "name", "surname", null, Date.valueOf(LocalDate.of(1990, 10, 10)), "Мужчина", null, null, null, null, null, null, null, null, null, null, 0, 0, new HashSet<>(), new HashSet<>(),
                photo);
    }

    @After
    public void afterTesting() throws SQLException {
        dao.deleteAll(connection);
    }

    @Test
    public void shouldSaveAndFindContact() throws SQLException {
        dao.save(contact, connection);
        Contact actualContact = dao.findById(1, connection);
        Assert.assertEquals(contact.getFirstName(), actualContact.getFirstName());
    }

    @Test
    public void shouldCountContacts() throws SQLException {
        dao.save(contact, connection);
        Assert.assertEquals(2, dao.getNumberOfContacts(connection));
    }

    @Test
    public void shouldUpdateContact() throws SQLException {
        dao.save(contact, connection);
        contact = dao.findById(1, connection);
        contact.setFirstName("changedName");
        dao.update(contact, connection);
        Contact actualContact = dao.findById(1, connection);
        Assert.assertEquals(contact.getFirstName(), actualContact.getFirstName());
        Assert.assertEquals(contact.getLastName(), actualContact.getLastName());
    }

    @Test
    public void shouldDeleteContact() throws SQLException {
        dao.save(contact, connection);
        dao.delete(1, connection);
        Contact afterDeleteContact = null;
        try {
            afterDeleteContact = dao.findById(1, connection);
        } catch (Exception e) {

        }
        Assert.assertNotEquals(contact, afterDeleteContact);
    }

    @Test
    public void shouldGetContacts() throws SQLException {
        dao.save(contact, connection);
        long startContactNumber = 0;
        long quantityOfContacts = 4;
        Set<Contact> contacts = dao.getSetOfContacts(startContactNumber, quantityOfContacts, connection);
        Assert.assertEquals(1, contacts.size());
    }

    @Test
    public void shouldGetSearchedContacts() throws SQLException {
        long startContactNumber = 0;
        long quantityOfContacts = 10;
        dao.save(contact, connection);
        Date lowerLimit = Date.valueOf(LocalDate.of(1970, 10, 10));
        Date upperLimit = Date.valueOf(LocalDate.now());
        Contact conditionContact = new Contact();
        conditionContact.setFirstName("name");
        Set<Contact> contacts = dao.searchContacts(conditionContact, lowerLimit, upperLimit, startContactNumber, quantityOfContacts, connection);
        Assert.assertEquals(1, contacts.size());
    }

    @Test
    public void shouldGetTotalQuantityOfSearchedContacts() throws SQLException {
        dao.save(contact, connection);
        Date lowerLimit = Date.valueOf(LocalDate.of(1970, 10, 10));
        Date upperLimit = Date.valueOf(LocalDate.now());
        Contact conditionContact = new Contact();
        long count = dao.getNumberOfSearchContacts(conditionContact, lowerLimit, upperLimit, connection);
        Assert.assertEquals(1, count);
    }
}
