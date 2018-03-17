package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.PhotoDao;
import com.itechart.javalab.firstproject.entities.Photo;

import java.sql.*;

import static com.itechart.javalab.firstproject.dao.util.DatabaseOperation.executeUpdateById;
import static com.itechart.javalab.firstproject.dao.util.DatabaseOperation.getGeneratedId;
import static com.itechart.javalab.firstproject.dao.util.EntityCreator.createAndInitializePhoto;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Created by Yauhen Malchanau on 08.09.2017.
 */
public class PhotoDaoImpl implements PhotoDao {

    private PhotoDaoImpl() {}

    private static PhotoDaoImpl instance;

    private static final String SAVE_PHOTO = "insert into photo (path, uuid) values (?, ?);";
    private static final String GET_PHOTO_BY_ID = "select * from photo as po where po.id = ?;";
    private static final String UPDATE_PHOTO = "update photo set path = ?, uuid = ? where id = ?;";
    private static final String UPDATE_CONTACTS = "update contact set photo_id = ? where photo_id = ?;";
    private static final String DELETE_PHOTO = "delete from photo where id = ?;";

    public static PhotoDao getInstance() {
        if (instance == null) {
            instance = new PhotoDaoImpl();
        }
        return instance;
    }

    @Override
    public long save(Photo entity, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_PHOTO, RETURN_GENERATED_KEYS)) {
            initializeStatementForSaveOrUpdatePhoto(statement, entity);
            statement.executeUpdate();
            return getGeneratedId(statement);
        }
    }

    @Override
    public Photo findById(long id, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_PHOTO_BY_ID)) {
            statement.setLong(1, id);
            Photo photo = null;
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    photo = createAndInitializePhoto(resultSet);
                }
                return photo;
            }
        }
    }

    @Override
    public void update(Photo entity, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PHOTO)) {
            initializeStatementForSaveOrUpdatePhoto(statement, entity);
            statement.setLong(3, entity.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(long id, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CONTACTS)) {
            statement.setLong(1, 0);
            statement.setLong(2, id);
            statement.executeUpdate();
        }

        executeUpdateById(id, DELETE_PHOTO, connection);
    }

    private void initializeStatementForSaveOrUpdatePhoto(PreparedStatement statement,
                                                         Photo entity) throws SQLException {
        statement.setString(1, entity.getPathToFile());
        statement.setString(2, entity.getUuid());
    }
}
