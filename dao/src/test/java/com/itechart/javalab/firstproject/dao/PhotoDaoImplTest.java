package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.dao.database.Database;
import com.itechart.javalab.firstproject.dao.impl.ContactDaoImpl;
import com.itechart.javalab.firstproject.dao.impl.PhotoDaoImpl;
import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.entities.Photo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by Yauhen Malchanau on 10.09.2017.
 */
public class PhotoDaoImplTest {
    private GenericDao<Photo> photoDao = PhotoDaoImpl.getInstance();
    private ContactDao<Contact> contactDao = ContactDaoImpl.getInstance();
    private Photo photo;
    private Connection connection;

    @Before
    public void beforeTesting() throws SQLException {
        connection = Database.getConnection();
        photo = new Photo(0, "path", UUID.randomUUID().toString());
    }

    @After
    public void afterTesting() throws SQLException {
        contactDao.deleteAll(connection);
    }


    @Test
    public void shouldSaveAndFindPhone() throws SQLException {
        photoDao.save(photo, connection);
        Photo actualPhoto = photoDao.findById(1, connection);
        Assert.assertEquals(photo.getUuid(), actualPhoto.getUuid());
    }


    @Test
    public void shouldDeletePhone() throws SQLException {
        photoDao.save(photo, connection);
        Photo beforeDeletePhoto = photoDao.findById(1, connection);
        Photo afterDeletePhoto = null;
        photoDao.delete(1, connection);
        try {
            afterDeletePhoto = photoDao.findById(1, connection);
        } catch (Exception e) {

        }
        Assert.assertNotEquals(beforeDeletePhoto, afterDeletePhoto);
    }

    @Test
    public void shouldUpdatePhone() throws SQLException {
        photoDao.save(photo, connection);
        Photo beforeUpdatePhoto = photoDao.findById(1, connection);
        beforeUpdatePhoto.setPathToFile("some path");
        photoDao.update(beforeUpdatePhoto, connection);
        Photo afterUpdatePhoto = photoDao.findById(1, connection);
        Assert.assertEquals(photo.getUuid(), afterUpdatePhoto.getUuid());
        Assert.assertNotEquals(photo.getPathToFile(), afterUpdatePhoto.getPathToFile());
    }
}
