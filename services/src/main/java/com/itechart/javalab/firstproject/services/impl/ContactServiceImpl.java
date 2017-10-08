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
public class ContactServiceImpl implements ContactService {

    private static ContactServiceImpl instance;
    private ContactDao contactDao = ContactDaoImpl.getInstance();
    private PhotoServiceImpl photoService = PhotoServiceImpl.getInstance();
    private AttachmentServiceImpl attachmentService = AttachmentServiceImpl.getInstance();
    private PhoneServiceImpl phoneService = PhoneServiceImpl.getInstance();

    private ContactServiceImpl() {
    }

    public static ContactService getInstance() {
        if (instance == null) {
            instance = new ContactServiceImpl();
        }
        return instance;
    }

    @Override
    public void delete(long id) throws SQLException {
        Connection connection = Database.getConnection();
        connection.setAutoCommit(false);
        try {
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
    public void create(Contact contact) throws SQLException {
        Connection connection = Database.getConnection();
        connection.setAutoCommit(false);
        try {
            long photoId = photoService.create(contact.getPhoto(), connection);
            contact.getPhoto().setId(photoId);
            long contactId = contactDao.save(contact, connection);
            if (contact.getAttachments() != null) {
                for (Attachment attachment : contact.getAttachments()) {
                    attachment.setContactId(contactId);
                    attachmentService.create(attachment, connection);
                }
            }
            if (contact.getPhones()!= null) {
                for (Phone phone : contact.getPhones()) {
                    phone.setContactId(contactId);
                    phoneService.create(phone, connection);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
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
    public void update(Contact contact, Set<Long> phonesForDelete, Set<Long> attachmentsForDelete) throws SQLException {
        Connection connection = Database.getConnection();
        connection.setAutoCommit(false);
        try {
            contactDao.update(contact, connection);
            Set<Attachment> attachments = attachmentService.getAllAttachmentsOfContact(contact.getId(), connection);
            if (attachments.size() != 0) {
                Iterator<Attachment> entityIterator = contact.getAttachments().iterator();
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
                for (Attachment attachment : contact.getAttachments()) {
                    attachment.setContactId(contact.getId());
                    attachmentService.create(attachment, connection);
                }
            } else if (contact.getAttachments().size() != 0) {
                for (Attachment attachment : contact.getAttachments()) {
                    attachment.setContactId(contact.getId());
                    attachmentService.create(attachment, connection);
                }
            }
            if (attachmentsForDelete != null && attachmentsForDelete.size() > 0) {
                for (long id : attachmentsForDelete) {
                    attachmentService.delete(id);
                }
            }
            Set<Phone> phones = phoneService.getAllPhonesOfContact(contact.getId(), connection);
            if (phones.size() != 0) {
                Iterator<Phone> entityIterator = contact.getPhones().iterator();
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
                for (Phone phone : contact.getPhones()) {
                    phone.setContactId(contact.getId());
                    phoneService.create(phone, connection);
                }
            } else if (contact.getPhones().size() != 0) {
                for (Phone phone : contact.getPhones()) {
                    phone.setContactId(contact.getId());
                    phoneService.create(phone, connection);
                }
            }
            if (phonesForDelete != null && phonesForDelete.size() > 0) {
                for (long id : phonesForDelete) {
                    phoneService.delete(id);
                }
            }
            if (contact.getPhoto().getId() != 0 && contact.getPhoto().getPathToFile() != null) {
                photoService.update(contact.getPhoto(), connection);
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
    public Set<Contact> searchContacts(Contact contact, Date lowerLimit, Date upperLimit, long startContactNumber, long quantityOfContacts) throws SQLException {
        Connection connection = Database.getConnection();
        Set<Contact> contacts = contactDao.searchContacts(contact, lowerLimit, upperLimit, startContactNumber, quantityOfContacts, connection);
        connection.close();
        return contacts;
    }

    @Override
    public long getNumberOfSearchContacts(Contact contact, Date lowerLimit, Date upperLimit) throws SQLException {
        Connection connection = Database.getConnection();
        long totalQuantity = contactDao.getNumberOfSearchContacts(contact, lowerLimit, upperLimit, connection);
        connection.close();
        return totalQuantity;
    }

    @Override
    public void deleteContacts(Set<Long> contactIds) throws SQLException {
        Connection connection = Database.getConnection();
        connection.setAutoCommit(false);
        try {
            for (Long id : contactIds) {
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
    public long getNumberOfContacts() throws SQLException {
        Connection connection = Database.getConnection();
        long number = contactDao.getNumberOfContacts(connection);
        connection.close();
        return number;
    }

    @Override
    public Contact findByEmail(String email) throws SQLException {
        Connection connection = Database.getConnection();
        Contact contact = contactDao.findByEmail(email, connection);
        connection.close();
        return contact;
    }

    @Override
    public Set<Contact> findContactsByBirthday(Date date) throws SQLException {
        Connection connection = Database.getConnection();
        Set<Contact> contacts = contactDao.findContactsByBirthday(date, connection);
        connection.close();
        return contacts;
    }
}
