package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.dao.impl.AttachmentDaoImpl;
import com.itechart.javalab.firstproject.dao.impl.ContactDaoImpl;
import com.itechart.javalab.firstproject.entities.Attachment;
import com.itechart.javalab.firstproject.entities.Contact;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Евгений Молчанов on 10.09.2017.
 */
public class AttachmentDaoImplTest {
    private GenericDao<Attachment> attachmentDao = AttachmentDaoImpl.getInstance();
    private ContactDao<Contact> contactDao = ContactDaoImpl.getInstance();
    private Contact contact = new Contact();

    @Before
    public void beforeTesting() throws SQLException {
        Attachment firstAttachment = new Attachment(0, "first", "comment", Date.valueOf(LocalDate.now()), "path");
        Attachment secondtAttachment = new Attachment(0, "second", "comment", Date.valueOf(LocalDate.now()), "anotherPath");
        Set<Attachment> attachments = new HashSet<>();
        attachments.add(firstAttachment);
        attachments.add(secondtAttachment);
        contact.setFirstName("FirstName");
        contact.setLastName("LastName");
        contact.setAttachments(attachments);
        contactDao.save(contact);
    }

    @After
    public void afterTesting() throws SQLException {
        contactDao.deleteAll();
    }


    @Test
    public void shouldDeletePhone() throws SQLException {
        Contact contact = contactDao.findById(1);
        int numberOfAttachmentsBeforeDelete = contact.getAttachments().size();
        for (Attachment attachment : contact.getAttachments()) {
            attachmentDao.delete(attachment.getId());
        }
        contact = contactDao.findById(1);
        Assert.assertNotEquals(numberOfAttachmentsBeforeDelete, contact.getPhones().size());
    }
}
