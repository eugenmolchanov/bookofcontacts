package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.PhoneDao;
import com.itechart.javalab.firstproject.dao.util.EntityBuilder;
import com.itechart.javalab.firstproject.dao.util.Util;
import com.itechart.javalab.firstproject.entities.Phone;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import static com.itechart.javalab.firstproject.dao.util.EntityBuilder.createAndInitializePhone;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Created by Yauhen Malchanau on 08.09.2017.
 */
public class PhoneDaoImpl implements PhoneDao {

    private PhoneDaoImpl() {
    }

    private static PhoneDaoImpl instance;

    private static final String SAVE_PHONE = "insert into phone (country_code, operator_code, phone_number, phone_type, " +
            "commentary, contact_id) values (?, ?, ?, ?, ?, ?);";
    private static final String GET_PHONE_BY_ID = "select * from phone as pe where pe.id = ?;";
    private static final String UPDATE_PHONE = "update phone set country_code = ?, operator_code = ?, phone_number = ?, " +
            "phone_type = ?, commentary = ? where id = ?;";
    private static final String DELETE_PHONE = "delete from phone where id = ?;";
    private static final String GET_PHONES = "select pe.id, pe.country_code, pe.operator_code, pe.phone_number, pe.phone_type, " +
            "pe.commentary from phone as pe where pe.contact_id = ?;";

    public static PhoneDao getInstance() {
        if (instance == null) {
            instance = new PhoneDaoImpl();
        }
        return instance;
    }

    @Override
    public long save(Phone entity, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_PHONE, RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getCountryCode());
            statement.setInt(2, entity.getOperatorCode());
            statement.setLong(3, entity.getNumber());
            statement.setString(4, entity.getType());
            statement.setString(5, entity.getComment());
            statement.setLong(6, entity.getContactId());
            statement.executeUpdate();
            return Util.getGeneratedIdAfterCreate(statement);
        }
    }

    @Override
    public Phone findById(long id, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_PHONE_BY_ID)) {
            statement.setLong(1, id);
            Phone phone = null;
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    phone = createAndInitializePhone(resultSet);
                }
                return phone;
            }
        }
    }

    @Override
    public void update(Phone entity, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PHONE)) {
            statement.setString(1, entity.getCountryCode());
            statement.setInt(2, entity.getOperatorCode());
            statement.setLong(3, entity.getNumber());
            statement.setString(4, entity.getType());
            statement.setString(5, entity.getComment());
            statement.setLong(6, entity.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(long id, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PHONE)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Set<Phone> getAllPhonesOfContact(long contactId, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_PHONES)) {
            statement.setLong(1, contactId);
            try (ResultSet resultSet = statement.executeQuery()) {
                Phone phone;
                Set<Phone> phones = new HashSet<>();
                while (resultSet.next()) {
                    phone = createAndInitializePhone(resultSet);
                    phones.add(phone);
                }
                return phones;
            }
        }
    }
}
