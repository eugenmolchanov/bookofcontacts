package com.itechart.javalab.firstproject.dao.util;

import java.sql.*;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public class Util {

    private Util() {
    }

    public static long getGeneratedIdAfterCreate(Statement statement) throws SQLException {
        ResultSet generatedKeys = statement.getGeneratedKeys();
        long id = 0;
        if (generatedKeys.next()) {
            id = generatedKeys.getLong(1);
        }
        return id;
    }
}
