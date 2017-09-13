package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.entities.Phone;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public interface PhoneDao<T> extends GenericDao<Phone> {
    Set<T> getAllPhonesOfContact(long entityId, Connection connection) throws SQLException;
}
