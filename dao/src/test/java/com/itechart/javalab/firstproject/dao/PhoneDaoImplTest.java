package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.dao.database.Database;
import com.itechart.javalab.firstproject.dao.impl.ContactDaoImpl;
import com.itechart.javalab.firstproject.dao.impl.PhoneDaoImpl;
import com.itechart.javalab.firstproject.dao.impl.PhotoDaoImpl;
import com.itechart.javalab.firstproject.entities.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Yauhen Malchanau on 08.09.2017.
 */
public class PhoneDaoImplTest {

    private PhoneDao phoneDao = PhoneDaoImpl.getInstance();
    private ContactDao contactDao = ContactDaoImpl.getInstance();
    private PhotoDao photoDao = PhotoDaoImpl.getInstance();
    private Connection connection;
    private Phone phone;
    private long contactId;

    @Before
    public void beforeTesting() throws SQLException {
        connection = Database.getConnection();
        Photo photo = new Photo();
        long photoId = photoDao.save(photo, connection);
        photo.setId(photoId);
        Contact contact = new Contact(0, "name", "surname", null, null, null, null, null, null, null, null, null, null, null, null, null, 0, 0, new HashSet<>(), new HashSet<>(),
                photo);
        contactId = contactDao.save(contact, connection);
        phone = new Phone(0, "1-1", 375, 222222222, "домашний", "actual number", contactId);
    }

    @After
    public void afterTesting() throws SQLException {
        contactDao.deleteAll(connection);
    }


    @Test
    public void shouldSaveAndFindPhone() throws SQLException {
        phoneDao.save(phone, connection);
        Phone actualPhone = phoneDao.findById(1, connection);
        Assert.assertEquals(phone.getNumber(), actualPhone.getNumber());
    }


    @Test
    public void shouldDeletePhone() throws SQLException {
        phoneDao.save(phone, connection);
        Phone beforeDeletePhone = phoneDao.findById(1, connection);
        Phone afterDeletePhone = null;
        phoneDao.delete(1, connection);
        try {
            afterDeletePhone = phoneDao.findById(1, connection);
        } catch (Exception e) {

        }
        Assert.assertNotEquals(beforeDeletePhone, afterDeletePhone);
    }

    @Test
    public void shouldUpdatePhone() throws SQLException {
        phoneDao.save(phone, connection);
        Phone beforeUpdatePhone = phoneDao.findById(1, connection);
        beforeUpdatePhone.setNumber(122222);
        phoneDao.update(beforeUpdatePhone, connection);
        Phone afterUpdatePhone = phoneDao.findById(1, connection);
        Assert.assertEquals(phone.getCountryCode(), afterUpdatePhone.getCountryCode());
        Assert.assertNotEquals(phone.getNumber(), afterUpdatePhone.getNumber());
    }

    @Test
    public void shouldGetAllAttachmentsOfContact() throws SQLException {
        phoneDao.save(phone, connection);
        Set<Phone> phones = phoneDao.getAllPhonesOfContact(contactId, connection);
        Assert.assertEquals(1, phones.size());
    }
}
