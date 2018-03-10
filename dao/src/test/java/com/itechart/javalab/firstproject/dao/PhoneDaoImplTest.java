package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.dao.database.Database;
import com.itechart.javalab.firstproject.dao.impl.ContactDaoImpl;
import com.itechart.javalab.firstproject.dao.impl.PhoneDaoImpl;
import com.itechart.javalab.firstproject.dao.impl.PhotoDaoImpl;
import com.itechart.javalab.firstproject.entities.*;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

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
        Contact contact = new Contact();
        contact.setFirstName("name");
        contact.setLastName("surname");
        contact.setPhoto(photo);
        contactId = contactDao.save(contact, connection);
        phone = createPhone();
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
    public void shouldGetAllPhonesOfContact() throws SQLException {
        phoneDao.save(phone, connection);
        Set<Phone> phones = phoneDao.getAllPhonesOfContact(4, connection);
        Assert.assertEquals(0, phones.size());
    }

    private Phone createPhone() {
        phone = new Phone();
        phone.setId(0);
        phone.setCountryCode("1-1");
        phone.setOperatorCode(375);
        phone.setNumber(222222222);
        phone.setType("home");
        phone.setComment("actual number");
        phone.setContactId(contactId);
        return phone;
    }
}
