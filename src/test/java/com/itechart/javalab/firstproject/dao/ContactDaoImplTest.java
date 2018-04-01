package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.dao.impl.ContactDaoImpl;
import com.itechart.javalab.firstproject.dao.impl.PhotoDaoImpl;
import com.itechart.javalab.firstproject.entities.*;
import com.itechart.javalab.firstproject.service.database.Database;
import org.junit.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;

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
        contact = createContact();
        contact.setPhoto(photo);
        dao.save(contact, connection);
    }

    @After
    public void afterTesting() throws SQLException {
        dao.deleteAll(connection);
    }

    @Test
    public void shouldSaveAndFindContact() throws SQLException {
        Contact actualContact = dao.findById(1, connection);
        Assert.assertEquals(contact.getFirstName(), actualContact.getFirstName());
    }

    @Test
    public void shouldFindContactByEmail() throws SQLException {
        Contact actualContact = dao.findByEmail("email@gmail.com", connection);
        Assert.assertEquals("name", actualContact.getFirstName());
    }

    @Test
    public void shouldCountContacts() throws SQLException {
        Assert.assertEquals(1, dao.getNumberOfContacts(connection));
    }

    @Test
    public void shouldUpdateContact() throws SQLException {
        contact = dao.findById(1, connection);
        contact.setFirstName("changedName");
        dao.update(contact, connection);
        Contact actualContact = dao.findById(1, connection);
        Assert.assertEquals(contact.getFirstName(), actualContact.getFirstName());
        Assert.assertEquals(contact.getLastName(), actualContact.getLastName());
    }

    @Test
    public void shouldDeleteContact() throws SQLException {
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
        long startContactNumber = 0;
        long quantityOfContacts = 10;
        Set<Contact> contacts = dao.getContactsList(startContactNumber, quantityOfContacts, connection);
        Assert.assertEquals(1, contacts.size());
    }

    @Test
    public void shouldGetSearchedContacts() throws SQLException {
        long startContactNumber = 0;
        long quantityOfContacts = 10;
        Date lowerLimit = Date.valueOf(LocalDate.of(1970, 10, 10));
        Date upperLimit = Date.valueOf(LocalDate.now());
        Contact conditionContact = new Contact();
        conditionContact.setLastName("surname");
        Set<Contact> contacts = dao.searchContacts(conditionContact, lowerLimit, upperLimit, startContactNumber, quantityOfContacts, connection);
        Assert.assertEquals(1, contacts.size());
    }

    @Test
    public void shouldGetTotalQuantityOfSearchedContacts() throws SQLException {
        Date lowerLimit = Date.valueOf(LocalDate.of(1970, 10, 10));
        Date upperLimit = Date.valueOf(LocalDate.now());
        Contact conditionContact = new Contact();
        long count = dao.getNumberOfSearchContacts(conditionContact, lowerLimit, upperLimit, connection);
        Assert.assertEquals(1, count);
    }

    @Test
    public void shouldFindContactsByBirthday() throws SQLException {
        Set<Contact> contacts = dao.findContactsByBirthday(Date.valueOf("1990-10-10"), connection);
        Assert.assertEquals(1, contacts.size());
    }

    @Test
    public void shouldSearchContact() throws SQLException {
        long startContactNumber = 0;
        long quantityOfContacts = 10;
        Date lowerLimit = Date.valueOf(LocalDate.of(1970, 10, 10));
        Date upperLimit = Date.valueOf(LocalDate.now());
        Contact searchedContact = new Contact();
        searchedContact.setFirstName("name");
        Set<Contact> searchedContacts = dao.searchContacts(searchedContact, lowerLimit, upperLimit,
                startContactNumber, quantityOfContacts, connection);
        Set<Contact> nextSearchedContacts = dao.searchContacts(searchedContact, lowerLimit, upperLimit,
                startContactNumber, 20, connection);
        Assert.assertEquals(searchedContacts.size(), nextSearchedContacts.size());
    }

    private Contact createContact() {
        contact = new Contact();
        contact.setId(0);
        contact.setFirstName("name");
        contact.setLastName("surname");
        contact.setBirthday(Date.valueOf(LocalDate.of(1990, 10, 10)));
        contact.setGender("male");
        contact.setEmail("email@gmail.com");
        return contact;
    }
}
