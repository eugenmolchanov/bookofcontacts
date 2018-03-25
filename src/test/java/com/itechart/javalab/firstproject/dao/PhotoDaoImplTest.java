package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.dao.impl.ContactDaoImpl;
import com.itechart.javalab.firstproject.dao.impl.PhotoDaoImpl;
import com.itechart.javalab.firstproject.entities.*;
import com.itechart.javalab.firstproject.service.database.Database;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by Yauhen Malchanau on 10.09.2017.
 */
public class PhotoDaoImplTest {
    private GenericDao<Photo> photoDao = PhotoDaoImpl.getInstance();
    private ContactDao contactDao = ContactDaoImpl.getInstance();
    private Photo photo;
    private Connection connection;

    @Before
    public void beforeTesting() throws SQLException {
        connection = Database.getConnection();
        photo = createPhoto();
    }

    @After
    public void afterTesting() throws SQLException {
        contactDao.deleteAll(connection);
    }


    @Test
    public void shouldSaveAndFindPhoto() throws SQLException {
        photoDao.save(photo, connection);
        Photo actualPhoto = photoDao.findById(1, connection);
        Assert.assertEquals(photo.getUuid(), actualPhoto.getUuid());
    }


    @Test
    public void shouldDeletePhoto() throws SQLException {
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
    public void shouldUpdatePhoto() throws SQLException {
        photoDao.save(photo, connection);
        Photo beforeUpdatePhoto = photoDao.findById(1, connection);
        beforeUpdatePhoto.setPathToFile("some path");
        photoDao.update(beforeUpdatePhoto, connection);
        Photo afterUpdatePhoto = photoDao.findById(1, connection);
        Assert.assertEquals(photo.getUuid(), afterUpdatePhoto.getUuid());
        Assert.assertNotEquals(photo.getPathToFile(), afterUpdatePhoto.getPathToFile());
    }

    private Photo createPhoto() {
        photo = new Photo();
        photo.setId(0);
        photo.setPathToFile("path");
        photo.setUuid(UUID.randomUUID().toString());
        return photo;
    }
}
