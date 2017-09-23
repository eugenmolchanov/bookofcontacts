package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.entities.Contact;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
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

    /**
     * Method is used for pagination and returns collection of contacts. The size of collection is defined by startContactNumber and quantityOfContacts parameters.
     *
     * @param startContactNumber the first id number of collection for pagination will start after this parameter
     * @param quantityOfContacts the quantity of contacts which is necessary for pagination
     * @param connection         connection to database
     * @return collection of contact objects
     * @throws SQLException
     */
    Set<Contact> getSetOfContacts(long startContactNumber, long quantityOfContacts, Connection connection) throws SQLException;

    /**
     * Method searches contacts which are required for user and are corresponded to search filter.
     *
     * @param contact            contact object which has fields for specific search
     * @param lowerLimit         date of birth from which contact object will satisfy search inquiry
     * @param upperLimit         date of birth up to which contact object will satisfy search inquiry
     * @param startContactNumber the first id number of collection for pagination will start after this parameter
     * @param quantityOfContacts the quantity of contacts which is necessary for pagination
     * @param connection         connection to database
     * @return collection of contact objects
     * @throws SQLException
     */
    Set<Contact> searchContacts(Contact contact, Date lowerLimit, Date upperLimit, long startContactNumber, long quantityOfContacts,
                                Connection connection) throws SQLException;

    /**
     * Method returns number of contacts which are required for user and are corresponded to search filter. Used for pagination.
     *
     * @param contact    contact object which has fields for specific search
     * @param lowerLimit date of birth from which contact object will satisfy search inquiry
     * @param upperLimit date of birth up to which contact object will satisfy search inquiry
     * @param connection connection to database
     * @return number of searched contact objects
     * @throws SQLException
     */
    long getNumberOfSearchContacts(Contact contact, Date lowerLimit, Date upperLimit, Connection connection) throws SQLException;

    /**
     * Method returns number of all contacts in database. Used for pagination.
     *
     * @param connection number of all contact objects
     * @return number of all contact objects
     * @throws SQLException
     */
    long getNumberOfContacts(Connection connection) throws SQLException;
}
