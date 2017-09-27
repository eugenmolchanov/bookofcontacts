package com.itechart.javalab.firstproject.services;

import com.itechart.javalab.firstproject.dao.ContactDao;
import com.itechart.javalab.firstproject.dao.PhotoDao;
import com.itechart.javalab.firstproject.dao.database.Database;
import com.itechart.javalab.firstproject.dao.impl.ContactDaoImpl;
import com.itechart.javalab.firstproject.dao.impl.PhotoDaoImpl;
import com.itechart.javalab.firstproject.entities.Attachment;
import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.entities.Photo;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
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
 * Created by Yauhen Malchanau on 25.09.2017.
 */
public class ContactServiceImplTest {

    private ContactService service = ContactServiceImpl.getInstance();
    private ContactDao dao = ContactDaoImpl.getInstance();
    private PhotoDao photoDao = PhotoDaoImpl.getInstance();
    private Connection connection;
    private Contact contact;

    @Before
    public void beforeTesting() throws SQLException {
        connection = Database.getConnection();
        Photo photo = new Photo();
        long photoId = photoDao.save(photo, connection);
        photo.setId(photoId);
        Attachment attachment = new Attachment(0, "first", "comment", Timestamp.valueOf(LocalDateTime.now()), "path", UUID.randomUUID().toString(), 0);
        Set<Attachment> attachments = new HashSet<>();
        attachments.add(attachment);
        contact = new Contact(0, "name", "surname", null, Date.valueOf(LocalDate.of(1990, 10, 10)), "Мужчина", null, null, null, null, null, null, null, null, null, null, 0, 0, new HashSet<>(), attachments,
                photo);
    }

    @After
    public void afterTesting() throws SQLException {
        connection = Database.getConnection();
        dao.deleteAll(connection);
        connection.close();
    }

    @Test
    public void shouldSaveAndFindContact() throws SQLException {
        service.create(contact);
        Contact actualContact = service.findById(1);
        Assert.assertEquals(contact.getFirstName(), actualContact.getFirstName());
        Assert.assertEquals(contact.getAttachments().size(), actualContact.getAttachments().size());
    }
}
