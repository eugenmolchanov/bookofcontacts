package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.dao.impl.ContactDaoImpl;
import com.itechart.javalab.firstproject.entities.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Евгений Молчанов on 06.09.2017.
 */
public class ContactDaoImplTest {

    private ContactDao<Contact> dao = new ContactDaoImpl();
    private Contact contact;

    @Before
    public void beforeTesting() {
        Address address = new Address(0, "Minsk", "SomeStreet", 10, 10, 2000);
        Phone mobilePhone = new Phone(0, 1, 375, 222222222, "mobile", "actual number");
        Phone homePhone = new Phone(0, 1, 209, 32312312, "home", "some comment");
        Set<Phone> phones = new HashSet<>();
        phones.add(mobilePhone);
        phones.add(homePhone);
        Attachment firstAttachment = new Attachment(0, "passport data", "comment", Date.valueOf(LocalDate.now()), "path");
        Attachment secondAttachment = new Attachment(0, "another data", "comment", Date.valueOf(LocalDate.now()), "anotherPath");
        Set<Attachment> attachments = new HashSet<>();
        attachments.add(firstAttachment);
        attachments.add(secondAttachment);
        Photo photo = new Photo(0, "path");
        contact = new Contact(0, "FirstName", "LastName", "MiddleName", Date.valueOf(LocalDate.of(1980, 10, 10)), "gender", "nationality", "maritalStatus", "webSite", "email",
                "employmentPlace", address, phones, attachments, photo);
    }

    @After
    public void afterTesting() throws SQLException {
        dao.deleteAll();
    }

    @Test
    public void shouldSaveAndFindContact() throws SQLException {
        dao.save(contact);
        Contact actualContact = dao.findById(1);
        Assert.assertEquals(contact.getFirstName(), actualContact.getFirstName());
    }

    @Test
    public void shouldUpdateContact() throws SQLException {
        dao.save(contact);
        contact = dao.findById(1);
        contact.setFirstName("changedName");
        for (Attachment attachment : contact.getAttachments()) {
            if (attachment.getId() == 1) {
                attachment.setPathToFile("changedPath");
            }
        }
        dao.update(contact);
        Contact actualContact = dao.findById(1);
        Assert.assertEquals(contact.getFirstName(), actualContact.getFirstName());
        Assert.assertEquals(contact.getLastName(), actualContact.getLastName());
        for (Attachment attachment : actualContact.getAttachments()) {
            if (attachment.getId() == 1) {
                Assert.assertEquals("changedPath", attachment.getPathToFile());
            }
        }
    }

    @Test
    public void shouldDeleteContact() throws SQLException {
        dao.save(contact);
        dao.delete(1);
        Assert.assertEquals(0, dao.findById(1).getId());
    }

    @Test
    public void shouldGetContacts() throws SQLException {
        long startContactNumber = 0;
        long quantityOfContacts = 4;
        StringBuilder name = new StringBuilder("1");
        for (int i = 0; i < 10; i++) {
            Contact contact = new Contact();
            contact.setFirstName(name.toString());
            contact.setLastName(name.toString());
            name.append(i);
            dao.save(contact);
        }
        Set<Contact> contacts = dao.getSetOfContacts(startContactNumber, quantityOfContacts);
        Assert.assertEquals((quantityOfContacts), contacts.size());
    }

    @Test
    public void shouldGetSearchedContacts() throws SQLException {
        dao.save(contact);
        Date lowerLimit = Date.valueOf(LocalDate.of(1970, 10, 10));
        Date upperLimit = Date.valueOf(LocalDate.of(1997, 10, 10));
        Contact conditionContact = new Contact();
        conditionContact.setFirstName("FirstName");
        Set<Contact> contacts = dao.searchContacts(conditionContact, lowerLimit, upperLimit);
        Assert.assertEquals(1, contacts.size());
    }
}
