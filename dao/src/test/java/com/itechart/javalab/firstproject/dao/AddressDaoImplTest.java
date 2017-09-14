package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.dao.database.Database;
import com.itechart.javalab.firstproject.dao.impl.AddressDaoImpl;
import com.itechart.javalab.firstproject.dao.impl.ContactDaoImpl;
import com.itechart.javalab.firstproject.entities.Address;
import com.itechart.javalab.firstproject.entities.Contact;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public class AddressDaoImplTest {
    private AddressDao<Address> addressDao = AddressDaoImpl.getInstance();
    private ContactDao<Contact> contactDao = ContactDaoImpl.getInstance();
    private Connection connection;
    private Address address;

    @Before
    public void beforeTesting() throws SQLException {
        connection = Database.getConnection();
        address = new Address(0, "Belarus", "city", "street", 10, 10, 10);
    }

    @After
    public void afterTesting() throws SQLException {
        contactDao.deleteAll(connection);
    }


    @Test
    public void shouldSaveAndFindAddress() throws SQLException {
        addressDao.save(address, connection);
        Address actualAddress = addressDao.findById(1, connection);
        Assert.assertEquals(address.getCountry(), actualAddress.getCountry());
    }


    @Test
    public void shouldCheckAddress() throws SQLException {
        long id = addressDao.save(address, connection);
        long checkAddressId = addressDao.checkAddress(address, connection);
        Assert.assertEquals(id, checkAddressId);
    }

    @Test
    public void shouldUpdateAddress() throws SQLException {
        addressDao.save(address, connection);
        Address beforeUpdateAddress = addressDao.findById(1, connection);
        beforeUpdateAddress.setHouseNumber(20);
        addressDao.update(beforeUpdateAddress, connection);
        Address afterUpdateAddress = addressDao.findById(1, connection);
        Assert.assertEquals(address.getFlatNumber(), afterUpdateAddress.getFlatNumber());
        Assert.assertNotEquals(address.getHouseNumber(), afterUpdateAddress.getHouseNumber());
    }
}
