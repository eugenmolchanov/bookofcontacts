package com.itechart.javalab.firstproject.services.database;

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
}
