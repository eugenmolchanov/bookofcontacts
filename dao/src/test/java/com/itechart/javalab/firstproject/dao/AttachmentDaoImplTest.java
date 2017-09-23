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
    private AttachmentDao attachmentDao = AttachmentDaoImpl.getInstance();
    private PhotoDao photoDao = PhotoDaoImpl.getInstance();
    private ContactDao contactDao = ContactDaoImpl.getInstance();
    private Connection connection;
    private Attachment attachment;
    private long contactId;

    @Before
    public void beforeTesting() throws SQLException {
        connection = Database.getConnection();
        Photo photo = new Photo();
        long photoId = photoDao.save(photo, connection);
        photo.setId(photoId);
        Contact contact = new Contact(0, "name", "surname", null, null, null, null, null, null, null, null, null, null, null, null, null, 0, 0, new HashSet<>(), new HashSet<>(),
                photo);
        contactId = contactDao.save(contact, connection);
        attachment = new Attachment(0, "first", "comment", Timestamp.valueOf(LocalDateTime.now()), "path", UUID.randomUUID().toString(), contactId);
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
        attachmentDao.save(attachment, connection);
        Set<Attachment> attachments = attachmentDao.getAllAttachmentsOfContact(contactId, connection);
        Assert.assertEquals(1, attachments.size());
    }
}
