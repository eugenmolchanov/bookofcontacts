package com.itechart.javalab.firstproject.dao.connection;

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
}
