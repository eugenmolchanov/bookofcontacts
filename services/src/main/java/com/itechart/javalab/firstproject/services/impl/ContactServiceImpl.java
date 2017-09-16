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
    private AddressServiceImpl addressService = AddressServiceImpl.getInstance();
    private PhotoServiceImpl photoService = PhotoServiceImpl.getInstance();
    private AttachmentServiceImpl attachmentService = AttachmentServiceImpl.getInstance();
    private PhoneServiceImpl phoneService = PhoneServiceImpl.getInstance();

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
        Connection connection = Database.getConnection();
        connection.setAutoCommit(false);
        try {
            contactDao.deleteDependenceFromAttachment(id, connection);
            contactDao.deleteDependenceFromPhone(id, connection);
            contactDao.delete(id, connection);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
    }

    @Override
    public void create(Contact entity) throws SQLException {
        Connection connection = Database.getConnection();
        connection.setAutoCommit(false);
        try {
            long addressId = addressService.checkAddress(entity.getAddress(), connection);
            if (addressId == 0) {
                addressId = addressService.create(entity.getAddress(), connection);
                entity.getAddress().setId(addressId);
            }
            long photoId = photoService.create(entity.getPhoto(), connection);
            entity.getPhoto().setId(photoId);
            long contactId = contactDao.save(entity, connection);
            for (Attachment attachment : entity.getAttachments()) {
                long attachmentId = attachmentService.create(attachment, connection);
                contactDao.addDependenceFromAttachment(contactId, attachmentId, connection);
            }
            for (Phone phone : entity.getPhones()) {
                long phoneId = phoneService.create(phone, connection);
                contactDao.addDependenceFromPhone(contactId, phoneId, connection);
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
                addressService.update(entity.getAddress(), connection);
            } else {
                addressService.create(entity.getAddress(), connection);
            }
            Set<Attachment> attachments = attachmentService.getAllAttachmentsOfContact(entity.getId(), connection);
            if (attachments.size() != 0) {
                Iterator<Attachment> entityIterator = entity.getAttachments().iterator();
                while (entityIterator.hasNext()) {
                    Attachment potentialNewcomer = entityIterator.next();
                    Iterator<Attachment> databaseContactIterator = attachments.iterator();
                    while (databaseContactIterator.hasNext()) {
                        Attachment databaseAttachment = databaseContactIterator.next();
                        if (databaseAttachment.getId() == potentialNewcomer.getId()) {
                            attachmentService.update(potentialNewcomer, connection);
                            entityIterator.remove();
                        }
                    }
                }
                for (Attachment attachment : entity.getAttachments()) {
                    long attachmentId = attachmentService.create(attachment, connection);
                    contactDao.addDependenceFromAttachment(entity.getId(), attachmentId, connection);
                }
            } else if (entity.getAttachments().size() != 0) {
                for (Attachment attachment : entity.getAttachments()) {
                    long attachmentId = attachmentService.create(attachment, connection);
                    contactDao.addDependenceFromAttachment(entity.getId(), attachmentId, connection);
                }
            }
            Set<Phone> phones = phoneService.getAllPhonesOfContact(entity.getId(), connection);
            if (phones.size() != 0) {
                Iterator<Phone> entityIterator = entity.getPhones().iterator();
                while (entityIterator.hasNext()) {
                    Phone potentialNewcomer = entityIterator.next();
                    Iterator<Phone> databaseContactIterator = phones.iterator();
                    while (databaseContactIterator.hasNext()) {
                        Phone databasePhone = databaseContactIterator.next();
                        if (databasePhone.getId() == potentialNewcomer.getId()) {
                            phoneService.update(potentialNewcomer, connection);
                            entityIterator.remove();
                        }
                    }
                }
                for (Phone phone : entity.getPhones()) {
                    long phoneId = phoneService.create(phone, connection);
                    contactDao.addDependenceFromPhone(entity.getId(), phoneId, connection);
                }
            } else if (entity.getPhones().size() != 0) {
                for (Phone phone : entity.getPhones()) {
                    long phoneId = phoneService.create(phone, connection);
                    contactDao.addDependenceFromPhone(entity.getId(), phoneId, connection);
                }
            }
            if (entity.getPhoto().getId() != 0) {
                photoService.update(entity.getPhoto(), connection);
            } else {
                photoService.create(entity.getPhoto(), connection);
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
    public Set<Contact> getSetOfContacts(long startContactNumber, long quantityOfContacts) throws SQLException {
        Connection connection = Database.getConnection();
        Set<Contact> contacts = contactDao.getSetOfContacts(startContactNumber, quantityOfContacts, connection);
        connection.close();
        return contacts;
    }

    @Override
    public Set<Contact> searchContacts(Contact entity, Date lowerLimit, Date upperLimit, long startContactNumber, long quantityOfContacts) throws SQLException {
        Connection connection = Database.getConnection();
        Set<Contact> contacts = contactDao.searchContacts(entity, lowerLimit, upperLimit, startContactNumber, quantityOfContacts, connection);
        connection.close();
        return contacts;
    }

    @Override
    public void deleteContacts(Set<Long> contactIds) throws SQLException {
        Connection connection = Database.getConnection();
        connection.setAutoCommit(false);
        try {
            for (Long id : contactIds) {
                contactDao.deleteDependenceFromAttachment(id, connection);
                contactDao.deleteDependenceFromPhone(id, connection);
                contactDao.delete(id, connection);
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
    public long countContacts() throws SQLException {
        Connection connection = Database.getConnection();
        long number = contactDao.getNumberOfContacts(connection);
        connection.close();
        return number;
    }

    protected void deleteAll() throws SQLException {
        Connection connection = Database.getConnection();
        connection.setAutoCommit(false);
        try {
            contactDao.deleteAll(connection);
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.close();
        }
    }
}
