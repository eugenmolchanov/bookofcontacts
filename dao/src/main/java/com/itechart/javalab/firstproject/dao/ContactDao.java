package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.entities.Contact;

import java.sql.SQLException;

/**
 * Created by Евгений Молчанов on 06.09.2017.
 */
public interface ContactDao<T> extends GenericDao<Contact> {
    /**
     * Method deletes all contacts and reset the counter.
     */
    void deleteAll() throws SQLException;
}
