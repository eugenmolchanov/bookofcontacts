package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.entities.Contact;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public interface ContactDao extends GenericDao<Contact> {
    /**
     * Method deletes all contacts and reset the counter.
     * This method is used only for testing.
     *
     * @param connection connection to database
     * @throws SQLException
     */
    void deleteAll(Connection connection) throws SQLException;

    Set<Contact> getContactsList(long startContactNumber, long quantityOfContacts,
                                 Connection connection) throws SQLException;

    Set<Contact> searchContacts(Contact contact, Date lowerLimit, Date upperLimit, long startContactNumber,
                                long quantityOfContacts, Connection connection) throws SQLException;

    long getNumberOfSearchContacts(Contact contact, Date lowerLimit, Date upperLimit,
                                   Connection connection) throws SQLException;

    long getNumberOfContacts(Connection connection) throws SQLException;

    Contact findByEmail(String email, Connection connection) throws SQLException;

    Set<Contact> findContactsByBirthday(Date date, Connection connection) throws SQLException;
}
