package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.dao.database.Database;
import com.itechart.javalab.firstproject.dao.impl.AddressDaoImpl;
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

    private PhoneDao<Phone> phoneDao = PhoneDaoImpl.getInstance();
    private ContactDao<Contact> contactDao = ContactDaoImpl.getInstance();
    private Connection connection;
    private Phone phone;

    @Before
    public void beforeTesting() throws SQLException {
        connection = Database.getConnection();
        phone = new Phone(0, 1, 375, 222222222, "mobile", "actual number");
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
        Photo photo = new Photo(0, "path", UUID.randomUUID().toString());
        Phone mobilePhone = new Phone(0, 1, 375, 222222222, "mobile", "actual number");
        Phone homePhone = new Phone(0, 1, 209, 32312312, "home", "some comment");
        Set<Phone> phones = new HashSet<>();
        phones.add(mobilePhone);
        phones.add(homePhone);
        Address address = new Address(0, "Belarus", "city", "street", 10, 10, 10);
        AddressDao<Address> addressDao = AddressDaoImpl.getInstance();
        PhotoDao<Photo> photoDao = PhotoDaoImpl.getInstance();
        photo.setId(photoDao.save(photo, connection));
        address.setId(addressDao.save(address, connection));
        Contact contact = new Contact(0, "FirstName", "LastName", "MiddleName", Date.valueOf(LocalDate.of(1980, 10, 10)), "gender", "nationality", "maritalStatus", "webSite", "email",
                "employmentPlace", address, phones, new HashSet<>(), photo);
        ContactDao<Contact> contactDao = ContactDaoImpl.getInstance();
        long contactId = contactDao.save(contact, connection);
        long mobilePhoneId = phoneDao.save(mobilePhone, connection);
        long homePhoneId = phoneDao.save(homePhone, connection);
        contactDao.addDependenceFromPhone(contactId, mobilePhoneId, connection);
        contactDao.addDependenceFromPhone(contactId, homePhoneId, connection);
        Set<Phone> contactPhones = phoneDao.getAllPhonesOfContact(1, connection);
        Assert.assertEquals(2, contactPhones.size());
    }
}
