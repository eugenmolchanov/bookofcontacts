package com.itechart.javalab.firstproject.service.database;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public class Database {

    private Database() {
    }

    private static final ResourceBundle credentials = ResourceBundle.getBundle("credentials");
    private static final String URL_PREFIX = credentials.getString("url_prefix");
    private static final String USERNAME = credentials.getString("user_name");
    private static final String PASSWORD = credentials.getString("password");
    private static final String DRIVER = credentials.getString("driver");

    private static PoolProperties poolProperties = new PoolProperties();
    private static DataSource dataSource = new DataSource();

    static {
        poolProperties.setUrl(URL_PREFIX);
        poolProperties.setUsername(USERNAME);
        poolProperties.setPassword(PASSWORD);
        poolProperties.setDriverClassName(DRIVER);
        dataSource.setPoolProperties(poolProperties);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static Connection getDisabledAutoCommitConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        return connection;
    }

    public static void commitConnection(Connection connection) throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public static void rollbackConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.rollback();
            connection.setAutoCommit(true);
        }
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
