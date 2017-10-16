package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.entities.Phone;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public interface PhoneDao extends GenericDao<Phone> {
    /**
     * Method returns all phones which belong to defined contact
     *
     * @param contactId  id of contact
     * @param connection connection to database
     * @return collection of phones
     * @throws SQLException
     */
    Set<Phone> getAllPhonesOfContact(long contactId, Connection connection) throws SQLException;
}
