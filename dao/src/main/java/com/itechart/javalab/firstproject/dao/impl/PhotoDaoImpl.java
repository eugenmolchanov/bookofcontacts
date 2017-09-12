package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.PhotoDao;
import com.itechart.javalab.firstproject.dao.util.Util;
import com.itechart.javalab.firstproject.entities.Photo;

import java.sql.*;

/**
 * Created by Yauhen Malchanau on 08.09.2017.
 */
public class PhotoDaoImpl implements PhotoDao<Photo> {

    private static volatile PhotoDaoImpl INSTANCE;

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
    public long save(Photo entity, Connection connection) throws SQLException {
        final String SAVE_PHOTO = "insert into photo (path, uuid) values (?, ?);";
        PreparedStatement statement = connection.prepareStatement(SAVE_PHOTO, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, entity.getPathToFile());
        statement.setString(2, entity.getUuid());
        statement.executeUpdate();
        long id = Util.getGeneratedIdAfterCreate(statement);
        statement.close();
        return id;
    }

    @Override
    public Photo findById(long id, Connection connection) throws SQLException {
        return null;
    }

    @Override
    public void update(Photo entity, Connection connection) throws SQLException {
        final String UPDATE_PHOTO = "update photo set path = ?, uuid = ? where id = ?;";
        PreparedStatement statement = connection.prepareStatement(UPDATE_PHOTO);
        statement.setString(1, entity.getPathToFile());
        statement.setString(2, entity.getUuid());
        statement.executeUpdate();
    }

    @Override
    public void delete(long id, Connection connection) throws SQLException {
        final String UPDATE_CONTACTS = "update contact set photo_id = ? where photo_id = ?;";
        final String DELETE_PHOTO = "delete from photo where id = ?;";
        PreparedStatement statement = connection.prepareStatement(UPDATE_CONTACTS);
        statement.setLong(1, 0);
        statement.setLong(2, id);
        statement.executeUpdate();

        statement = connection.prepareStatement(DELETE_PHOTO);
        statement.setLong(1, id);
        statement.executeUpdate();
        statement.close();
    }
}
