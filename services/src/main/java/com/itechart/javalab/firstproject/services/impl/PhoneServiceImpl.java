package com.itechart.javalab.firstproject.services.impl;

import com.itechart.javalab.firstproject.dao.PhoneDao;
import com.itechart.javalab.firstproject.dao.impl.PhoneDaoImpl;
import com.itechart.javalab.firstproject.entities.Phone;
import com.itechart.javalab.firstproject.services.PhoneService;
import com.itechart.javalab.firstproject.services.database.Database;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public class PhoneServiceImpl implements PhoneService {

    private static Logger logger = Logger.getLogger(PhoneServiceImpl.class);
    private static PhoneServiceImpl instance;
    private PhoneDao phoneDao = PhoneDaoImpl.getInstance();

    private PhoneServiceImpl() {
    }

    public static PhoneServiceImpl getInstance() {
        if (instance == null) {
            instance = new PhoneServiceImpl();
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
            logger.error("Can't delete phone. SqlException.");
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Can't delete phone. Exception.");
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    protected long create(Phone entity, Connection connection) throws SQLException {
        return phoneDao.save(entity, connection);
    }

    protected Set<Phone> getAllPhonesOfContact(long contactId, Connection connection) throws SQLException {
        return phoneDao.getAllPhonesOfContact(contactId, connection);
    }

    protected void update(Phone entity, Connection connection) throws SQLException {
        phoneDao.update(entity, connection);
    }
}
