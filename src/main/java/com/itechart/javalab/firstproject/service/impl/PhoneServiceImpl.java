package com.itechart.javalab.firstproject.service.impl;

import com.itechart.javalab.firstproject.dao.PhoneDao;
import com.itechart.javalab.firstproject.dao.impl.PhoneDaoImpl;
import com.itechart.javalab.firstproject.entities.Phone;
import com.itechart.javalab.firstproject.service.PhoneService;
import com.itechart.javalab.firstproject.service.database.Database;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import static com.itechart.javalab.firstproject.service.database.Database.closeConnection;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public class PhoneServiceImpl implements PhoneService {

    private static Logger logger = Logger.getLogger(PhoneServiceImpl.class);
    private static PhoneServiceImpl instance;
    private PhoneDao phoneDao = PhoneDaoImpl.getInstance();
    private static final Object lock = new Object();

    private PhoneServiceImpl() {
    }

    public static PhoneServiceImpl getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new PhoneServiceImpl();
            }
        }
        return instance;
    }

    @Override
    public void delete(long id) throws SQLException {
        Connection connection = null;
        try {
            connection = Database.getConnection();
            phoneDao.delete(id, connection);
        } catch (SQLException e) {
            logger.error("Can't delete phone.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            closeConnection(connection);
        }
    }

    long create(Phone entity, Connection connection) throws SQLException {
        return phoneDao.save(entity, connection);
    }

    Set<Phone> getAllPhonesOfContact(long contactId, Connection connection) throws SQLException {
        return phoneDao.getAllPhonesOfContact(contactId, connection);
    }

    void update(Phone entity, Connection connection) throws SQLException {
        phoneDao.update(entity, connection);
    }
}
