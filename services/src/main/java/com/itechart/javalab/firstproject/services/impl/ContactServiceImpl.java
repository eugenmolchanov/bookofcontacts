package com.itechart.javalab.firstproject.services.impl;

import com.itechart.javalab.firstproject.dao.ContactDao;
import com.itechart.javalab.firstproject.dao.impl.ContactDaoImpl;
import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Евгений Молчанов on 11.09.2017.
 */
public class ContactServiceImpl implements ContactService<Contact> {

    private static ContactServiceImpl INSTANCE;
    private ContactDao<Contact> contactDao;

    private ContactServiceImpl() {
    }

    public static ContactService<Contact> getInstance() {
        if (INSTANCE == null) {
            synchronized (ContactServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ContactServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void delete(long id) throws SQLException {
        contactDao = ContactDaoImpl.getInstance();
        contactDao.delete(id);
    }

    @Override
    public void save(Contact entity) throws SQLException {
        contactDao = ContactDaoImpl.getInstance();
        contactDao.save(entity);
    }

    @Override
    public Contact findById(long id) throws SQLException {
        contactDao = ContactDaoImpl.getInstance();
        return contactDao.findById(id);
    }

    @Override
    public void update(Contact entity) throws SQLException {
        contactDao = ContactDaoImpl.getInstance();
        contactDao.update(entity);
    }

    @Override
    public void deleteAll() throws SQLException {
        contactDao = ContactDaoImpl.getInstance();
        contactDao.deleteAll();
    }

    @Override
    public Set<Contact> getSetOfContacts(long startContactNumber, long quantityOfContacts) throws SQLException {
        contactDao = ContactDaoImpl.getInstance();
        return contactDao.getSetOfContacts(startContactNumber, quantityOfContacts);
    }

    @Override
    public Set<Contact> searchContacts(Contact entity, Date lowerLimit, Date upperLimit, long startContactNumber, long quantityOfContacts) throws SQLException {
        contactDao = ContactDaoImpl.getInstance();
        return contactDao.searchContacts(entity, lowerLimit, upperLimit, startContactNumber, quantityOfContacts);
    }
}
