package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.dao.impl.ContactDaoImpl;
import com.itechart.javalab.firstproject.dao.impl.PhotoDaoImpl;
import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.entities.Photo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 10.09.2017.
 */
public class PhotoDaoImplTest {
    private GenericDao<Photo> photoDao = PhotoDaoImpl.getInstance();
    private ContactDao<Contact> contactDao = ContactDaoImpl.getInstance();
    private Contact contact = new Contact();

    @Before
    public void beforeTesting() throws SQLException {
        Photo photo = new Photo(0, "path");
        contact.setFirstName("FirstName");
        contact.setLastName("LastName");
        contact.setPhoto(photo);
        contactDao.save(contact);
    }

    @After
    public void afterTesting() throws SQLException {
        contactDao.deleteAll();
    }


    @Test
    public void shouldDeletePhone() throws SQLException {
        Contact beforeDeleteContact = contactDao.findById(1);
        photoDao.delete(beforeDeleteContact.getId());
        Contact afterDeleteContact = contactDao.findById(1);
        Assert.assertNotEquals(beforeDeleteContact.getPhoto(), afterDeleteContact.getPhoto());
    }
}
