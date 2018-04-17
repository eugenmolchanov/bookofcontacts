package com.itechart.javalab.firstproject.service.impl;

import com.itechart.javalab.firstproject.dao.PhotoDao;
import com.itechart.javalab.firstproject.dao.impl.PhotoDaoImpl;
import com.itechart.javalab.firstproject.entities.Photo;
import com.itechart.javalab.firstproject.service.PhotoService;
import com.itechart.javalab.firstproject.service.database.Database;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import static com.itechart.javalab.firstproject.service.database.Database.closeConnection;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public class PhotoServiceImpl implements PhotoService {

    private static Logger logger = Logger.getLogger(PhotoServiceImpl.class);
    private static PhotoServiceImpl instance;
    private PhotoDao photoDao = PhotoDaoImpl.getInstance();
    private static final Object lock = new Object();

    private PhotoServiceImpl() {
    }

    public static PhotoServiceImpl getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new PhotoServiceImpl();
            }
        }
        return instance;
    }

    @Override
    public void delete(long id) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            photoDao.delete(id, connection);
        } catch (SQLException e) {
            logger.error("Can't delete photo.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Photo findById(long id) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            return photoDao.findById(id, connection);
        } catch (SQLException e) {
            logger.error("Can't find photo by id.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }


    void update(Photo entity, Connection connection) throws SQLException {
        photoDao.update(entity, connection);
    }

    long create(Photo entity, Connection connection) throws SQLException {
        return photoDao.save(entity, connection);
    }
}