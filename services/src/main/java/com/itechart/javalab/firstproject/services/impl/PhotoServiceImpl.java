package com.itechart.javalab.firstproject.services.impl;

import com.itechart.javalab.firstproject.dao.PhotoDao;
import com.itechart.javalab.firstproject.dao.impl.PhoneDaoImpl;
import com.itechart.javalab.firstproject.dao.impl.PhotoDaoImpl;
import com.itechart.javalab.firstproject.entities.Photo;
import com.itechart.javalab.firstproject.services.PhotoService;
import com.itechart.javalab.firstproject.services.database.Database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public class PhotoServiceImpl implements PhotoService<Photo> {

    private static volatile PhotoServiceImpl instance;
    private PhotoDao<Photo> photoDao = PhotoDaoImpl.getInstance();

    private PhotoServiceImpl() {
    }

    public static PhotoServiceImpl getInstance() {
        if (instance == null) {
            synchronized (PhotoServiceImpl.class) {
                if (instance == null) {
                    instance = new PhotoServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void delete(long id) throws SQLException {
        Connection connection = Database.getConnection();
        photoDao.delete(id, connection);
        connection.close();
    }

    protected long create(Photo entity, Connection connection) throws SQLException {
        return photoDao.save(entity, connection);
    }

    protected void update(Photo entity, Connection connection) throws SQLException {
        photoDao.update(entity, connection);
    }
}
