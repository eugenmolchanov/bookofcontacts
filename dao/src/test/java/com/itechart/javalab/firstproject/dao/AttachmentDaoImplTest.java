package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.dao.database.Database;
import com.itechart.javalab.firstproject.dao.impl.*;
import com.itechart.javalab.firstproject.entities.*;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        Contact contact = new Contact();
        contact.setFirstName("name");
        contact.setLastName("surname");
        contact.setPhoto(photo);
        contactId = contactDao.save(contact, connection);
        attachment = createAttachment();
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
        attachment = createAttachment();
        Set<Attachment> attachments = attachmentDao.getAllAttachmentsOfContact(contactId, connection);
        Assert.assertEquals(1, attachments.size());
    }

    private Attachment createAttachment() {
        attachment = new Attachment();
        attachment.setId(0);
        attachment.setFileName("first");
        attachment.setCommentary("comment");
        attachment.setDate(Timestamp.valueOf(LocalDateTime.now()));
        attachment.setPathToFile("path");
        attachment.setUuid(UUID.randomUUID().toString());
        attachment.setContactId(contactId);
        return attachment;
    }
}
