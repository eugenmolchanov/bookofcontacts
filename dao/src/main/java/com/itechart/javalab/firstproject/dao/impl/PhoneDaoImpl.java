package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.PhoneDao;
import com.itechart.javalab.firstproject.dao.database.Database;
import com.itechart.javalab.firstproject.entities.Phone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Евгений Молчанов on 08.09.2017.
 */
public class PhoneDaoImpl implements PhoneDao<Phone> {
    @Override
    public void save(Phone entity) throws SQLException {

    }

    @Override
    public Phone findById(long id) throws SQLException {
        return null;
    }

    @Override
    public void update(Phone entity) throws SQLException {

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
