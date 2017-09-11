package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.entities.Contact;

import java.sql.SQLException;
import java.sql.Date;
import java.util.Set;

/**
 * Created by Евгений Молчанов on 06.09.2017.
 */
public interface ContactDao<T> extends GenericDao<Contact> {
    /**
     * Method saves entity in util.
     * @param entity
     * @throws SQLException
     */
    void save(T entity) throws SQLException;

    /**
     * Method finds entity by id.
     * @param id
     * @return
     * @throws SQLException
     */
    T findById(long id) throws SQLException;

    /**
     * Method updates entity.
     * @param entity
     * @throws SQLException
     */
    void update(T entity) throws SQLException;

    /**
     * Method deletes all contacts and reset the counter.
     * @throws SQLException
     */
    void deleteAll() throws SQLException;

    /**
     * Method is used for pagination and returns certain set of contacts.
     * @param startContactNumber
     * @param quantityOfContacts
     * @return Set of contacts
     * @throws SQLException
     */
    Set<T> getSetOfContacts(long startContactNumber, long quantityOfContacts) throws SQLException;

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
    Set<T> searchContacts(T entity, Date lowerLimit, Date upperLimit, long startContactNumber, long quantityOfContacts) throws SQLException;
}
