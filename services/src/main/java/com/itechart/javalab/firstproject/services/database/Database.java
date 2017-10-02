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

    private static final String URL_PREFIX = ResourceBundle.getBundle("credentials").getString("url_prefix");
    private static final String USERNAME = ResourceBundle.getBundle("credentials").getString("user_name");
    private static final String PASSWORD = ResourceBundle.getBundle("credentials").getString("password");
    private static final String DRIVER = ResourceBundle.getBundle("credentials").getString("driver");

    private static PoolProperties poolProperties = new PoolProperties();
    private static DataSource dataSource = new DataSource();

    public static Connection getConnection() throws SQLException {
        poolProperties.setUrl(URL_PREFIX);
        poolProperties.setUsername(USERNAME);
        poolProperties.setPassword(PASSWORD);
        poolProperties.setDriverClassName(DRIVER);
        poolProperties.setMaxActive(20);
        poolProperties.setMaxIdle(20);
        dataSource.setPoolProperties(poolProperties);
        return dataSource.getConnection();
    }
}
