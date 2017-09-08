package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.dao.impl.ContactDaoImpl;
import com.itechart.javalab.firstproject.entities.Address;
import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.entities.Photo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import java.sql.SQLException;
import java.util.HashSet;

/**
 * Created by Евгений Молчанов on 06.09.2017.
 */
public class ContactDaoImplTest {

    private ContactDao<Contact> dao = new ContactDaoImpl();
    private Contact contact = new Contact();

    @After
    public void afterTesting() throws SQLException {
        dao.deleteAll();
    }

    @Test
    public void shouldSaveAndFindContact() throws SQLException {
        contact.setFirstName("FirstName");
        contact.setLastName("LastName");
        contact.setAddress(new Address());
        contact.setPhoto(new Photo());
        contact.setAttachments(new HashSet<>());
        contact.setPhones(new HashSet<>());
        dao.save(contact);
        Contact actualContact = dao.findById(1);
        Assert.assertEquals(contact.getFirstName(), actualContact.getFirstName());
    }
}
