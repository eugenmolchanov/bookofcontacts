package com.itechart.javalab.firstproject.services.impl;

import com.itechart.javalab.firstproject.dao.*;
import com.itechart.javalab.firstproject.dao.impl.*;
import com.itechart.javalab.firstproject.entities.*;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.database.Database;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

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
        Connection connection = null;
        try {
            connection = Database.getConnection();
            connection.setAutoCommit(false);
            contactDao.delete(id, connection);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
                logger.error("Connection rollback.");
            }
            logger.error("Can't delete contact by id. SqlException.");
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
                logger.error("Connection rollback.");
            }
            logger.error("Can't delete contact by id. Exception.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public void create(Contact contact) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            connection.setAutoCommit(false);
            long photoId = photoService.create(contact.getPhoto(), connection);
            contact.getPhoto().setId(photoId);
            long contactId = contactDao.save(contact, connection);
            if (contact.getAttachments() != null) {
                for (Attachment attachment : contact.getAttachments()) {
                    attachment.setContactId(contactId);
                    attachmentService.create(attachment, connection);
                }
            }
            if (contact.getPhones() != null) {
                for (Phone phone : contact.getPhones()) {
                    phone.setContactId(contactId);
                    phoneService.create(phone, connection);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
                logger.error("Connection rollback.");
            }
            logger.error("Can't create contact. SqlException.");
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
                logger.error("Connection rollback.");
            }
            logger.error("Can't create contact. Exception.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public Contact findById(long id) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            return contactDao.findById(id, connection);
        } catch (SQLException e) {
            logger.error("Can't find contact by id. SqlException.");
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Can't find contact by id. Exception.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public void update(Contact contact, Set<Long> phonesForDelete, Set<Long> attachmentsForDelete) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            connection.setAutoCommit(false);
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
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
                logger.error("Connection rollback.");
            }
            logger.error("Can't update contact. SqlException.");
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
                logger.error("Connection rollback.");
            }
            logger.error("Can't update contact. Exception.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public Set<Contact> getSetOfContacts(long startContactNumber, long quantityOfContacts) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            return contactDao.getSetOfContacts(startContactNumber, quantityOfContacts, connection);
        } catch (SQLException e) {
            logger.error("Can't get set of contacts. SqlException.");
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Can't get set of contacts. Exception.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public Set<Contact> searchContacts(Contact contact, Date lowerLimit, Date upperLimit, long startContactNumber, long quantityOfContacts) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            return contactDao.searchContacts(contact, lowerLimit, upperLimit, startContactNumber, quantityOfContacts, connection);
        } catch (SQLException e) {
            logger.error("Can't search contacts. SqlException.");
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Can't search contacts. Exception.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public long getNumberOfSearchContacts(Contact contact, Date lowerLimit, Date upperLimit) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            return contactDao.getNumberOfSearchContacts(contact, lowerLimit, upperLimit, connection);
        } catch (SQLException e) {
            logger.error("Can't get number of searched contacts. SqlException.");
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Can't get number of searched contacts. Exception.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public void deleteContacts(Set<Long> contactIds) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            connection.setAutoCommit(false);
            for (Long id : contactIds) {
                contactDao.delete(id, connection);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
                logger.error("Connection rollback.");
            }
            logger.error("Can't delete group of contacts. SqlException.");
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
                logger.error("Connection rollback.");
            }
            logger.error("Can't delete group of contacts. Exception.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public long getNumberOfContacts() throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            return contactDao.getNumberOfContacts(connection);
        } catch (SQLException e) {
            logger.error("Can't get number of all contacts. SqlException.");
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Can't get number of all contacts. Exception.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public Contact findByEmail(String email) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            return contactDao.findByEmail(email, connection);
        } catch (SQLException e) {
            logger.error("Can't find contact by email. SqlException.");
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Can't find contact by email. Exception.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public Set<Contact> findContactsByBirthday(Date date) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            return contactDao.findContactsByBirthday(date, connection);
        } catch (SQLException e) {
            logger.error("Can't find contacts by birth date. SqlException.");
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Can't find contacts by birth date. Exception.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
