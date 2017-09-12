package com.itechart.javalab.firstproject.services.impl;

import com.itechart.javalab.firstproject.dao.*;
import com.itechart.javalab.firstproject.dao.impl.*;
import com.itechart.javalab.firstproject.entities.*;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.database.Database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public class ContactServiceImpl implements ContactService<Contact> {

    private static volatile ContactServiceImpl INSTANCE;
    private ContactDao<Contact> contactDao = ContactDaoImpl.getInstance();
    private AddressDao<Address> addressDao = AddressDaoImpl.getInstance();
    private PhotoDao<Photo> photoDao = PhotoDaoImpl.getInstance();
    private AttachmentDao<Attachment> attachmentDao = AttachmentDaoImpl.getInstance();
    private PhoneDao<Phone> phoneDao = PhoneDaoImpl.getInstance();

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
        contactDao.delete(id);
    }

    @Override
    public void save(Contact entity) throws SQLException {
        Connection connection = Database.getConnection();
        connection.setAutoCommit(false);
        try {
            long addressId = addressDao.checkAddress(entity.getAddress(), connection);
            if (addressId == 0) {
                addressId = addressDao.save(entity.getAddress(), connection);
                entity.getAddress().setId(addressId);
            }
            long photoId = photoDao.save(entity.getPhoto(), connection);
            entity.getPhoto().setId(photoId);
            long contactId = contactDao.save(entity, connection);
            for (Attachment attachment : entity.getAttachments()) {
                long attachmentId = attachmentDao.save(attachment, connection);
                contactDao.addDependencyFromAttachment(contactId, attachmentId, connection);
            }
            for (Phone phone : entity.getPhones()) {
                long phoneId = phoneDao.save(phone, connection);
                contactDao.addDependencyFromPhone(contactId, phoneId, connection);
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
    }

    @Override
    public Contact findById(long id) throws SQLException {
        Connection connection = Database.getConnection();
        Contact contact = contactDao.findById(id, connection);
        connection.close();
        return contact;
    }

    @Override
    public void update(Contact entity) throws SQLException {
        Connection connection = Database.getConnection();
        connection.setAutoCommit(false);
        try {
            contactDao.update(entity, connection);
            if (entity.getAddress().getId() != 0) {
                addressDao.update(entity.getAddress(), connection);
            } else {
                addressDao.save(entity.getAddress(), connection);
            }

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
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
