package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.entities.Contact;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public interface ContactDao<T> extends GenericDao<Contact> {

    /**
     * Method deletes all contacts and reset the counter.
     * @throws SQLException
     */
    void deleteAll(Connection connection) throws SQLException;

    /**
     * Method is used for pagination and returns certain set of contacts.
     * @param startContactNumber
     * @param quantityOfContacts
     * @return Set of contacts
     * @throws SQLException
     */
    Set<T> getSetOfContacts(long startContactNumber, long quantityOfContacts, Connection connection) throws SQLException;

    /**
     * Method searches necessary contacts.
     * @param entity
     * @param lowerLimit
     * @param upperLimit
     * @param startContactNumber
     * @param quantityOfContacts
     * @return Set of contacts
     * @throws SQLException
     */
    Set<T> searchContacts(T entity, Date lowerLimit, Date upperLimit, long startContactNumber, long quantityOfContacts,
                          Connection connection) throws SQLException;

    void addDependenceFromAttachment(long contactId, long attachmentId, Connection connection) throws SQLException;
    void addDependenceFromPhone(long contactId, long phoneId, Connection connection) throws SQLException;
    void deleteDependenceFromAttachment(long contactId, Connection connection) throws SQLException;
    void deleteDependenceFromPhone(long contactId, Connection connection) throws SQLException;
}
