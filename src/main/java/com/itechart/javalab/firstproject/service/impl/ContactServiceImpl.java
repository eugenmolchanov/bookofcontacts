package com.itechart.javalab.firstproject.service.impl;

import com.itechart.javalab.firstproject.dao.ContactDao;
import com.itechart.javalab.firstproject.dao.impl.ContactDaoImpl;
import com.itechart.javalab.firstproject.entities.Attachment;
import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.entities.Phone;
import com.itechart.javalab.firstproject.service.ContactService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import static com.itechart.javalab.firstproject.service.database.Database.*;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public class ContactServiceImpl implements ContactService {

    private static Logger logger = Logger.getLogger(ContactServiceImpl.class);
    private static ContactServiceImpl instance;
    private ContactDao contactDao = ContactDaoImpl.getInstance();
    private PhotoServiceImpl photoService = PhotoServiceImpl.getInstance();
    private AttachmentServiceImpl attachmentService = AttachmentServiceImpl.getInstance();
    private PhoneServiceImpl phoneService = PhoneServiceImpl.getInstance();
    private static final Object lock = new Object();

    private ContactServiceImpl() {
    }

    public static ContactService getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new ContactServiceImpl();
            }
        }
        return instance;
    }

    @Override
    public void delete(long id) throws SQLException {
        Connection connection = null;
        try {
            connection = getDisabledAutoCommitConnection();
            contactDao.delete(id, connection);
            commitConnection(connection);
        } catch (SQLException e) {
            rollbackConnection(connection);
            logger.error("Can't delete contact by id.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void create(Contact contact) throws SQLException {
        Connection connection = null;
        try {
            connection = getDisabledAutoCommitConnection();
            long photoId = photoService.create(contact.getPhoto(), connection);

            contact.getPhoto().setId(photoId);
            long contactId = contactDao.save(contact, connection);

            contact.setId(contactId);
            if (contact.getAttachments() != null && !contact.getAttachments().isEmpty()) {
                saveAttachments(contact, connection);
            }
            if (contact.getPhones() != null && !contact.getPhones().isEmpty()) {
                savePhones(contact, connection);
            }

            commitConnection(connection);
        } catch (SQLException e) {
            rollbackConnection(connection);
            logger.error("Can't create contact.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Contact findById(long id) throws SQLException {
        try (Connection connection = getConnection()) {
            return contactDao.findById(id, connection);
        } catch (SQLException e) {
            logger.error("Can't find contact by id.");
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void update(Contact contact, Set<Long> phonesForDelete, Set<Long> attachmentsForDelete) throws SQLException {
        Connection connection = null;
        try {
            connection = getDisabledAutoCommitConnection();
            contactDao.update(contact, connection);

            Set<Attachment> attachments = attachmentService.getAllAttachmentsOfContact(contact.getId(), connection);
            if (!attachments.isEmpty()) {
                updateExistedAttachments(contact, attachments, connection);
                saveAttachments(contact, connection);
            } else if (!contact.getAttachments().isEmpty()) {
                saveAttachments(contact, connection);
            }

            if (attachmentsForDelete != null && !attachmentsForDelete.isEmpty()) {
                for (long id : attachmentsForDelete) {
                    attachmentService.delete(id);
                }
            }

            Set<Phone> phones = phoneService.getAllPhonesOfContact(contact.getId(), connection);
            if (!phones.isEmpty()) {
                updateExistedPhones(contact, phones, connection);
                savePhones(contact, connection);
            } else if (!contact.getPhones().isEmpty()) {
                savePhones(contact, connection);
            }

            if (phonesForDelete != null && !phonesForDelete.isEmpty()) {
                for (long id : phonesForDelete) {
                    phoneService.delete(id);
                }
            }

            if (contact.getPhoto().getId() != 0 && contact.getPhoto().getPathToFile() != null) {
                photoService.update(contact.getPhoto(), connection);
            }

            commitConnection(connection);
        } catch (SQLException e) {
            rollbackConnection(connection);
            logger.error("Can't update contact.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Set<Contact> getContacts(long startContactNumber, long quantityOfContacts) throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            return contactDao.getContactsList(startContactNumber, quantityOfContacts, connection);
        } catch (SQLException e) {
            logger.error("Can't get set of contacts.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Set<Contact> searchContacts(Contact contact, Date lowerLimit, Date upperLimit, long startContactNumber, long quantityOfContacts) throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            return contactDao.searchContacts(contact, lowerLimit, upperLimit, startContactNumber, quantityOfContacts, connection);
        } catch (SQLException e) {
            logger.error("Can't search contacts.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public long getSearchedContactsNumber(Contact contact, Date lowerLimit, Date upperLimit) throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            return contactDao.getNumberOfSearchContacts(contact, lowerLimit, upperLimit, connection);
        } catch (SQLException e) {
            logger.error("Can't get number of searched contacts.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void deleteContacts(Set<Long> contactIds) throws SQLException {
        Connection connection = null;
        try {
            connection = getDisabledAutoCommitConnection();
            for (Long id : contactIds) {
                contactDao.delete(id, connection);
            }
            commitConnection(connection);
        } catch (SQLException e) {
            rollbackConnection(connection);
            logger.error("Can't delete group of contacts.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public long getContactsNumber() throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            return contactDao.getNumberOfContacts(connection);
        } catch (SQLException e) {
            logger.error("Can't get number of all contacts.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Contact findByEmail(String email) throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            return contactDao.findByEmail(email, connection);
        } catch (SQLException e) {
            logger.error("Can't find contact by email.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Set<Contact> findContactsByBirthday(Date date) throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
            return contactDao.findContactsByBirthday(date, connection);
        } catch (SQLException e) {
            logger.error("Can't find contacts by birth date.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    private void saveAttachments(Contact contact, Connection connection) throws SQLException {
        for (Attachment attachment : contact.getAttachments()) {
            attachment.setContactId(contact.getId());
            attachmentService.create(attachment, connection);
        }
    }

    private void savePhones(Contact contact, Connection connection) throws SQLException {
        for (Phone phone : contact.getPhones()) {
            phone.setContactId(contact.getId());
            phoneService.create(phone, connection);
        }
    }

    private void updateExistedAttachments(Contact contact, Set<Attachment> attachments, Connection connection)
            throws SQLException {
        Iterator<Attachment> entityIterator = contact.getAttachments().iterator();
        while (entityIterator.hasNext()) {
            Attachment potentialNewcomer = entityIterator.next();
            for (Attachment databaseAttachment : attachments) {
                if (databaseAttachment.getId() == potentialNewcomer.getId()) {
                    attachmentService.update(potentialNewcomer, connection);
                    entityIterator.remove();
                }
            }
        }
    }

    private void updateExistedPhones(Contact contact, Set<Phone> phones, Connection connection)
            throws SQLException {
        Iterator<Phone> entityIterator = contact.getPhones().iterator();
        while (entityIterator.hasNext()) {
            Phone potentialNewcomer = entityIterator.next();
            for (Phone databasePhone : phones) {
                if (databasePhone.getId() == potentialNewcomer.getId()) {
                    phoneService.update(potentialNewcomer, connection);
                    entityIterator.remove();
                }
            }
        }
    }
}
