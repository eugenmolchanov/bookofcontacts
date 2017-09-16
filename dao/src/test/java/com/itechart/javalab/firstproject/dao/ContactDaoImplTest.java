package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.dao.database.Database;
import com.itechart.javalab.firstproject.dao.impl.AddressDaoImpl;
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

    private ContactDao<Contact> dao = ContactDaoImpl.getInstance();
    private Connection connection;
    private Contact contact;

    @Before
    public void beforeTesting() throws SQLException {
        connection = Database.getConnection();
        Address address = new Address(0, "Belarus", "city", "street", 10, 10, 10);
        Phone mobilePhone = new Phone(0, 1, 375, 222222222, "mobile", "actual number");
        Phone homePhone = new Phone(0, 1, 209, 32312312, "home", "some comment");
        Set<Phone> phones = new HashSet<>();
        phones.add(mobilePhone);
        phones.add(homePhone);
        Attachment firstAttachment = new Attachment(0, "passport data", "comment", Timestamp.valueOf(LocalDateTime.now()), "path", UUID.randomUUID().toString());
        Attachment secondAttachment = new Attachment(0, "another data", "comment", Timestamp.valueOf(LocalDateTime.now()), "anotherPath", UUID.randomUUID().toString());
        Set<Attachment> attachments = new HashSet<>();
        attachments.add(firstAttachment);
        attachments.add(secondAttachment);
        Photo photo = new Photo(0, "path", UUID.randomUUID().toString());
        PhotoDao<Photo> photoDao = PhotoDaoImpl.getInstance();
        AddressDao<Address> addressDao = AddressDaoImpl.getInstance();
        address.setId(addressDao.save(address, connection));
        photo.setId(photoDao.save(photo, connection));
        contact = new Contact(0, "FirstName", "LastName14", "MiddleName", Date.valueOf(LocalDate.of(1980, 10, 10)), "gender", "nationality", "maritalStatus", "webSite", "emal10",
                "employmentPlace", address, phones, attachments, photo);
    }

    @After
    public void afterTesting() throws SQLException {
//        dao.deleteAll(connection);
    }

    @Test
    public void shouldSaveAndFindContact() throws SQLException {
        dao.save(contact, connection);
        Contact actualContact = dao.findById(1, connection);
        Assert.assertEquals(contact.getFirstName(), actualContact.getFirstName());
    }

    @Test
    public void shouldCountContacts() throws SQLException {
        System.out.println(dao.getNumberOfContacts(connection));
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
        long startContactNumber = 0;
        long quantityOfContacts = 4;
//        StringBuilder name = new StringBuilder("1");
//        for (int i = 0; i < 10; i++) {
//            Contact contact = new Contact();
//            contact.setFirstName(name.toString());
//            contact.setLastName(name.toString());
//            name.append(i);
//            dao.save(contact, connection);
//        }
        Set<Contact> contacts = dao.getSetOfContacts(startContactNumber, quantityOfContacts, connection);
        Assert.assertEquals(1, contacts.size());
    }

    @Test
    public void shouldGetSearchedContacts() throws SQLException {
        long startContactNumber = 0;
        long quantityOfContacts = 4;
        dao.save(contact, connection);
        Date lowerLimit = Date.valueOf(LocalDate.of(1970, 10, 10));
        Date upperLimit = Date.valueOf(LocalDate.of(1997, 10, 10));
        Contact conditionContact = new Contact();
        conditionContact.setFirstName("FirstName");
        Set<Contact> contacts = dao.searchContacts(conditionContact, lowerLimit, upperLimit, startContactNumber, quantityOfContacts, connection);
        Assert.assertEquals(1, contacts.size());
    }
}
