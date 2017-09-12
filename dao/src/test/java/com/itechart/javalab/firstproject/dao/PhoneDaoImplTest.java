package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.dao.impl.ContactDaoImpl;
import com.itechart.javalab.firstproject.dao.impl.PhoneDaoImpl;
import com.itechart.javalab.firstproject.entities.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 08.09.2017.
 */
public class PhoneDaoImplTest {

    private GenericDao<Phone> phoneDao = PhoneDaoImpl.getInstance();
    private ContactDao<Contact> contactDao = ContactDaoImpl.getInstance();
    private Contact contact = new Contact();

    @Before
    public void beforeTesting() throws SQLException {
        Phone mobilePhone = new Phone(0, 1, 375, 222222222, "mobile", "actual number");
        Phone homePhone = new Phone(0, 1, 209, 32312312, "home", "some comment");
        Set<Phone> phones = new HashSet<>();
        phones.add(mobilePhone);
        phones.add(homePhone);
        contact.setFirstName("FirstName");
        contact.setLastName("LastName");
        contact.setPhones(phones);
        contactDao.save(contact);
    }

    @After
    public void afterTesting() throws SQLException {
        contactDao.deleteAll();
    }


    @Test
    public void shouldDeletePhone() throws SQLException {
        Contact contact = contactDao.findById(1);
        int numberOfPhonesBeforeDelete = contact.getPhones().size();
        for (Phone phone : contact.getPhones()) {
            phoneDao.delete(phone.getId());
        }
        contact = contactDao.findById(1);
        Assert.assertNotEquals(numberOfPhonesBeforeDelete, contact.getPhones().size());
    }
}
