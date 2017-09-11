package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.ContactDao;
import com.itechart.javalab.firstproject.dao.PhotoDao;
import com.itechart.javalab.firstproject.dao.util.Database;
import com.itechart.javalab.firstproject.entities.Photo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Евгений Молчанов on 08.09.2017.
 */
public class PhotoDaoImpl implements PhotoDao<Photo> {

    private static PhotoDaoImpl INSTANCE;

    private PhotoDaoImpl() {
    }

    public static PhotoDao<Photo> getInstance() {
        if (INSTANCE == null) {
            synchronized (PhotoDaoImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PhotoDaoImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void delete(long id) throws SQLException {
        String deletePhoto = "delete from photo where id = ?;";
        Connection connection = Database.getConnection();
        PreparedStatement statement = connection.prepareStatement(deletePhoto);
        statement.setLong(1, id);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }
}
