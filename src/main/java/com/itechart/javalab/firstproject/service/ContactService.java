package com.itechart.javalab.firstproject.service;

import com.itechart.javalab.firstproject.entities.Contact;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public interface ContactService extends GenericService<Contact> {
    void create(Contact contact) throws SQLException;

    Contact findById(long id) throws SQLException;

    void update(Contact contact, Set<Long> phonesForDelete, Set<Long> attachmentsForDelete) throws SQLException;

    Set<Contact> getContacts(long startContactNumber, long quantityOfContacts) throws SQLException;

    Set<Contact> searchContacts(Contact contact, Date lowerLimit, Date upperLimit, long startContactNumber, long quantityOfContacts) throws SQLException;

    long getSearchedContactsNumber(Contact contact, Date lowerLimit, Date upperLimit) throws SQLException;

    void deleteContacts(Set<Long> contactIds) throws SQLException;

    long getContactsNumber() throws SQLException;

    Contact findByEmail(String email) throws SQLException;

    Set<Contact> findContactsByBirthday(Date date) throws SQLException;
}
