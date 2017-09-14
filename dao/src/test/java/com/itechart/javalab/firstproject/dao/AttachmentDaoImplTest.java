package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.dao.database.Database;
import com.itechart.javalab.firstproject.dao.impl.*;
import com.itechart.javalab.firstproject.entities.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Yauhen Malchanau on 10.09.2017.
 */
public class AttachmentDaoImplTest {
    private AttachmentDao<Attachment> attachmentDao = AttachmentDaoImpl.getInstance();
    private ContactDao<Contact> contactDao = ContactDaoImpl.getInstance();
    private Connection connection;
    private Attachment attachment;

    @Before
    public void beforeTesting() throws SQLException {
        connection = Database.getConnection();
        attachment = new Attachment(0, "first", "comment", Timestamp.valueOf(LocalDateTime.now()), "path", UUID.randomUUID().toString());
    }

    @After
    public void afterTesting() throws SQLException {
        contactDao.deleteAll(connection);
    }

    @Test
    public void shouldSaveAndFindAttachment() throws SQLException {
        attachmentDao.save(attachment, connection);
        Attachment actualAttachment = attachmentDao.findById(1, connection);
        Assert.assertEquals(attachment.getUuid(), actualAttachment.getUuid());
    }


    @Test
    public void shouldDeleteAttachment() throws SQLException {
        attachmentDao.save(attachment, connection);
        Attachment beforeDeleteAttachment = attachmentDao.findById(1, connection);
        Attachment afterDeleteAttachment = null;
        attachmentDao.delete(1, connection);
        try {
            afterDeleteAttachment = attachmentDao.findById(1, connection);
        } catch (Exception e) {

        }
        Assert.assertNotEquals(beforeDeleteAttachment, afterDeleteAttachment);
    }

    @Test
    public void shouldUpdateAttachment() throws SQLException {
        attachmentDao.save(attachment, connection);
        Attachment beforeUpdateAttachment = attachmentDao.findById(1, connection);
        beforeUpdateAttachment.setFileName("ChangeToSomething");
        attachmentDao.update(beforeUpdateAttachment, connection);
        Attachment afterUpdateAttachment = attachmentDao.findById(1, connection);
        Assert.assertEquals(attachment.getPathToFile(), afterUpdateAttachment.getPathToFile());
        Assert.assertNotEquals(attachment.getFileName(), afterUpdateAttachment.getFileName());
    }

    @Test
    public void shouldGetAllAttachmentsOfContact() throws SQLException {
        Photo photo = new Photo(0, "path", UUID.randomUUID().toString());
        Attachment firstAttachment = new Attachment(0, "passport data", "comment", Timestamp.valueOf(LocalDateTime.now()), "path", UUID.randomUUID().toString());
        Attachment secondAttachment = new Attachment(0, "another data", "comment", Timestamp.valueOf(LocalDateTime.now()), "anotherPath", UUID.randomUUID().toString());
        Set<Attachment> attachments = new HashSet<>();
        attachments.add(firstAttachment);
        attachments.add(secondAttachment);
        Address address = new Address(0, "Belarus", "city", "street", 10, 10, 10);
        AddressDao<Address> addressDao = AddressDaoImpl.getInstance();
        PhotoDao<Photo> photoDao = PhotoDaoImpl.getInstance();
        photo.setId(photoDao.save(photo, connection));
        address.setId(addressDao.save(address, connection));
        Contact contact = new Contact(0, "FirstName", "LastName", "MiddleName", Date.valueOf(LocalDate.of(1980, 10, 10)), "gender", "nationality", "maritalStatus", "webSite", "email",
                "employmentPlace", address, new HashSet<>(), attachments, photo);
        ContactDao<Contact> contactDao = ContactDaoImpl.getInstance();
        long contactId = contactDao.save(contact, connection);
        long firstAttachmentId = attachmentDao.save(firstAttachment, connection);
        long secondAttachmentId = attachmentDao.save(secondAttachment, connection);
        contactDao.addDependenceFromAttachment(contactId, firstAttachmentId, connection);
        contactDao.addDependenceFromAttachment(contactId, secondAttachmentId, connection);
        Set<Attachment> contactAttachments = attachmentDao.getAllAttachmentsOfContact(1, connection);
        Assert.assertEquals(2, contactAttachments.size());
    }
}
