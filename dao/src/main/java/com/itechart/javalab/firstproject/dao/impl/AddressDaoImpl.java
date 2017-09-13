package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.AddressDao;
import com.itechart.javalab.firstproject.dao.util.Util;
import com.itechart.javalab.firstproject.entities.Address;

import java.sql.*;

/**
 * Created by Yauhen Malchanau on 12.09.2017.
 */
public class AddressDaoImpl implements AddressDao<Address> {

    private static volatile AddressDaoImpl instance;

    private AddressDaoImpl() {
    }

    public static AddressDao<Address> getInstance() {
        if (instance == null) {
            synchronized (AddressDaoImpl.class) {
                if (instance == null) {
                    instance = new AddressDaoImpl();
                }
            }
        }
        return instance;
    }
    @Override
    public long checkAddress(Address entity, Connection connection) throws SQLException {
        final String CHECK_ADDRESS = "select id from address where city=? and street=? and houseNumber=? and flatNumber=? and country = ?;";
        long id = 0;
        PreparedStatement statement = connection.prepareStatement(CHECK_ADDRESS);
        statement.setString(1, entity.getCity());
        statement.setString(2, entity.getStreet());
        statement.setInt(3, entity.getHouseNumber());
        statement.setInt(4, entity.getFlatNumber());
        statement.setString(5, entity.getCountry());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            id = resultSet.getLong("id");
        }
        statement.close();
        return id;
    }

    @Override
    public long save(Address entity, Connection connection) throws SQLException {
        final String SAVE_ADDRESS = "insert into address (city, street, houseNumber, flatNumber, postalIndex, country) values (?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(SAVE_ADDRESS, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, entity.getCity());
        statement.setString(2, entity.getStreet());
        statement.setInt(3, entity.getHouseNumber());
        statement.setInt(4, entity.getFlatNumber());
        statement.setInt(5, entity.getPostalIndex());
        statement.setString(6, entity.getCountry());
        statement.executeUpdate();
        long id = Util.getGeneratedIdAfterCreate(statement);
        statement.close();
        return id;
    }

    @Override
    public Address findById(long id, Connection connection) throws SQLException {
        final String GET_ADDRESS_BY_ID = "select * from address where id = ?;";
        PreparedStatement statement = connection.prepareStatement(GET_ADDRESS_BY_ID);
        statement.setLong(1, id);
        Address address = null;
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            address = new Address(resultSet.getLong("id"), resultSet.getString("country"), resultSet.getString("city"), resultSet.getString("street"),
                    resultSet.getInt("houseNumber"), resultSet.getInt("flatNumber"), resultSet.getInt("postalIndex"));
        }
        statement.close();
        return address;
    }

    @Override
    public void update(Address entity, Connection connection) throws SQLException {
        final String UPDATE_ADDRESS = "update address set city = ?, street = ?, houseNumber = ?, flatNumber = ?, postalIndex = ?, country = ? where id = ?;";
        PreparedStatement statement = connection.prepareStatement(UPDATE_ADDRESS);
        statement.setString(1, entity.getCity());
        statement.setString(2, entity.getStreet());
        statement.setInt(3, entity.getHouseNumber());
        statement.setInt(4, entity.getFlatNumber());
        statement.setInt(5, entity.getPostalIndex());
        statement.setString(6, entity.getCountry());
        statement.setLong(7, entity.getId());
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void delete(long id, Connection connection) throws SQLException {

    }
}
