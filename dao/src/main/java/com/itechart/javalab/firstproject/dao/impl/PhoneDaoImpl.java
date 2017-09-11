package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.PhoneDao;
import com.itechart.javalab.firstproject.dao.util.Database;
import com.itechart.javalab.firstproject.entities.Phone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Евгений Молчанов on 08.09.2017.
 */
public class PhoneDaoImpl implements PhoneDao<Phone> {

    private static PhoneDaoImpl INSTANCE;

    private PhoneDaoImpl() {
    }

    public static PhoneDao<Phone> getInstance() {
        if (INSTANCE == null) {
            synchronized (PhoneDaoImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PhoneDaoImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void delete(long id) throws SQLException {
        String deletePhone = "delete from phone where id = ?;";
        Connection connection = Database.getConnection();
        PreparedStatement statement = connection.prepareStatement(deletePhone);
        statement.setLong(1, id);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }
}
