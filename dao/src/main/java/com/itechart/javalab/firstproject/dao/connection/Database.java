package com.itechart.javalab.firstproject.dao.connection;

import com.itechart.javalab.firstproject.entities.Attachment;
import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.entities.Phone;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.*;

/**
 * Created by Евгений Молчанов on 06.09.2017.
 */
public class Database {

    private Database() {
    }

    private static final String URL_PREFIX = "jdbc:mysql://localhost:3306/bookofcontacts?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "user";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    private static PoolProperties poolProperties = new PoolProperties();
    private static DataSource dataSource = new DataSource();

    public static Connection getConnection() throws SQLException {
        poolProperties.setUrl(URL_PREFIX);
        poolProperties.setUsername(USERNAME);
        poolProperties.setPassword(PASSWORD);
        poolProperties.setDriverClassName(DRIVER);
        dataSource.setPoolProperties(poolProperties);
        return dataSource.getConnection();
    }

    public static long getGeneratedIdAfterCreate(Statement statement) throws SQLException {
        ResultSet generatedKeys = statement.getGeneratedKeys();
        long id;
        if (generatedKeys.next()) {
            id = generatedKeys.getLong(1);
        } else {
            throw new SQLException("Creating user failed, no ID obtained.");
        }
        return id;
    }

    public static void saveAddress(Connection connection, String saveAddress, String saveContactAddress, Contact entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(saveAddress, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, entity.getAddress().getCity());
        statement.setString(2, entity.getAddress().getStreet());
        statement.setInt(3, entity.getAddress().getHouseNumber());
        statement.setInt(4, entity.getAddress().getFlatNumber());
        statement.setInt(5, entity.getAddress().getPostalIndex());
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating address failed, no rows affected.");
        }
        entity.getAddress().setId(Database.getGeneratedIdAfterCreate(statement));

        statement = connection.prepareStatement(saveContactAddress);
        statement.setLong(1, entity.getId());
        statement.setLong(2, entity.getAddress().getId());
        statement.executeUpdate();
        statement.close();
    }

    public static void saveAttachment(Connection connection, String saveAttachment, Contact entity) throws SQLException {
        PreparedStatement statement = null;
        for (Attachment attachment : entity.getAttachments()) {
            statement = connection.prepareStatement(saveAttachment);
            statement.setString(1, attachment.getFileName());
            statement.setString(2, attachment.getCommentary());
            statement.setDate(3, attachment.getDate());
            statement.setString(4, attachment.getPathToFile());
            statement.setLong(5, entity.getId());
            statement.executeUpdate();
        }
        statement.close();
    }

    public static void savePhone(Connection connection, String savePhone, Contact entity) throws SQLException {
        PreparedStatement statement = null;
        for (Phone phone : entity.getPhones()) {
            statement = connection.prepareStatement(savePhone);
            statement.setInt(1, phone.getCountryCode());
            statement.setInt(2, phone.getOperatorCode());
            statement.setLong(3, phone.getNumber());
            statement.setString(4, phone.getType());
            statement.setString(5, phone.getComment());
            statement.setLong(6, entity.getId());
            statement.executeUpdate();
        }
        statement.close();
    }
}
