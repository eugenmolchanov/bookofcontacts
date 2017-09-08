package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.PhotoDao;
import com.itechart.javalab.firstproject.dao.database.Database;
import com.itechart.javalab.firstproject.entities.Photo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Евгений Молчанов on 08.09.2017.
 */
public class PhotoDaoImpl implements PhotoDao<Photo> {
    @Override
    public void save(Photo entity) throws SQLException {

    }

    @Override
    public Photo findById(long id) throws SQLException {
        return null;
    }

    @Override
    public void update(Photo entity) throws SQLException {

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
