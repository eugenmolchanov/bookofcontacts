package com.itechart.javalab.firstproject.services.impl;

import com.itechart.javalab.firstproject.dao.PhoneDao;
import com.itechart.javalab.firstproject.dao.impl.PhoneDaoImpl;
import com.itechart.javalab.firstproject.entities.Phone;
import com.itechart.javalab.firstproject.services.PhoneService;
import com.itechart.javalab.firstproject.services.database.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public class PhoneServiceImpl implements PhoneService<Phone> {

    private static volatile PhoneServiceImpl instance;
    private PhoneDao<Phone> phoneDao = PhoneDaoImpl.getInstance();

    private PhoneServiceImpl() {
    }

    public static PhoneServiceImpl getInstance() {
        if (instance == null) {
            synchronized (PhoneServiceImpl.class) {
                if (instance == null) {
                    instance = new PhoneServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void delete(long id) throws SQLException {
        Connection connection = Database.getConnection();
        phoneDao.delete(id, connection);
        connection.close();
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
