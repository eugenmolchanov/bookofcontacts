package com.itechart.javalab.firstproject.services;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public interface ContactService<T> extends GenericService<T> {
    /**
     * Method saves entity in util.
     * @param entity
     * @throws SQLException
     */
    void create(T entity) throws SQLException;

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
    long getCountOfSearchContacts(T entity, Date lowerLimit, Date upperLimit) throws SQLException;
    void deleteContacts(Set<Long> contactIds) throws SQLException;
    long countContacts() throws SQLException;
}
