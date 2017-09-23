package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.PhoneDao;
import com.itechart.javalab.firstproject.dao.util.Util;
import com.itechart.javalab.firstproject.entities.Phone;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 08.09.2017.
 */
public class PhoneDaoImpl implements PhoneDao {

    private static PhoneDaoImpl instance;

    private PhoneDaoImpl() {
    }

    public static PhoneDao getInstance() {
        if (instance == null) {
            instance = new PhoneDaoImpl();
        }
        return instance;
    }

    @Override
    public long save(Phone entity, Connection connection) throws SQLException {
        final String SAVE_PHONE = "insert into phone (country_code, operator_code, phone_number, phone_type, commentary, contact_id) values (?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(SAVE_PHONE, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, entity.getCountryCode());
        statement.setInt(2, entity.getOperatorCode());
        statement.setLong(3, entity.getNumber());
        statement.setString(4, entity.getType());
        statement.setString(5, entity.getComment());
        statement.setLong(6, entity.getContactId());
        statement.executeUpdate();
        long id = Util.getGeneratedIdAfterCreate(statement);
        statement.close();
        return id;
    }

    @Override
    public Phone findById(long id, Connection connection) throws SQLException {
        final String GET_PHONE_BY_ID = "select * from phone where id = ?;";
        PreparedStatement statement = connection.prepareStatement(GET_PHONE_BY_ID);
        statement.setLong(1, id);
        Phone phone = null;
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            phone = new Phone(resultSet.getLong("id"), resultSet.getInt("country_code"), resultSet.getInt("operator_code"), resultSet.getLong("phone_number"),
                    resultSet.getString("phone_type"), resultSet.getString("commentary"), resultSet.getLong("contact_id"));
        }
        statement.close();
        return phone;
    }

    @Override
    public void update(Phone entity, Connection connection) throws SQLException {
        final String UPDATE_PHONE = "update phone set country_code = ?, operator_code = ?, phone_number = ?, phone_type = ?, commentary = ? where id = ?;";
        PreparedStatement statement = connection.prepareStatement(UPDATE_PHONE);
        statement.setInt(1, entity.getCountryCode());
        statement.setInt(2, entity.getOperatorCode());
        statement.setLong(3, entity.getNumber());
        statement.setString(4, entity.getType());
        statement.setString(5, entity.getComment());
        statement.setLong(6, entity.getId());
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void delete(long id, Connection connection) throws SQLException {
        final String DELETE_PHONE = "delete from phone where id = ?;";
        PreparedStatement statement = connection.prepareStatement(DELETE_PHONE);
        statement.setLong(1, id);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public Set<Phone> getAllPhonesOfContact(long contactId, Connection connection) throws SQLException {
        final String GET_PHONES = "select id, country_code, operator_code, phone_number, phone_type, commentary from phone where contact_id = ?;";
        PreparedStatement statement = connection.prepareStatement(GET_PHONES);
        statement.setLong(1, contactId);
        ResultSet resultSet = statement.executeQuery();
        Phone phone;
        Set<Phone> phones = new HashSet<>();
        while (resultSet.next()) {
            phone = new Phone(resultSet.getLong("id"), resultSet.getInt("country_code"), resultSet.getInt("operator_code"), resultSet.getLong("phone_number"),
                    resultSet.getString("phone_type"), resultSet.getString("commentary"), contactId);
            phones.add(phone);
        }
        statement.close();
        return phones;
    }
}
