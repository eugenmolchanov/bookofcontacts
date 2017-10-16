package com.itechart.javalab.firstproject.services;

import com.itechart.javalab.firstproject.entities.Contact;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public interface ContactService extends GenericService<Contact> {
    /**
     * Method saves contact in data base. Method also saves all phones, attachments and photo of this contact.
     *
     * @param contact contact which should be created
     * @throws SQLException
     */
    void create(Contact contact) throws SQLException;

    /**
     * Method finds contact object by id with all of it's phones, attachments and photo.
     *
     * @param id id of contact
     * @return contact object
     * @throws SQLException
     */
    Contact findById(long id) throws SQLException;

    /**
     * Method updates contact and all phones, attachments and photo which belong to it.
     *
     * @param contact              contact which should be updated
     * @param phonesForDelete      ids of contact phones which should be deleted
     * @param attachmentsForDelete ids of contact attachments which should be deleted
     * @throws SQLException
     */
    void update(Contact contact, Set<Long> phonesForDelete, Set<Long> attachmentsForDelete) throws SQLException;

    /**
     * Method is used for pagination and returns certain set of contacts.
     *
     * @param startContactNumber the first id number of collection for pagination will start after this parameter
     * @param quantityOfContacts the quantity of contacts which is necessary for pagination
     * @return collection of contacts
     * @throws SQLException
     */
    Set<Contact> getSetOfContacts(long startContactNumber, long quantityOfContacts) throws SQLException;

    /**
     * Method searches contacts which are required for user and are corresponded to search filter.
     *
     * @param contact            contact object with fields for specific search
     * @param lowerLimit         date of birth from which contact object will satisfy search inquiry
     * @param upperLimit         date of birth up to which contact object will satisfy search inquiry
     * @param startContactNumber the first id number of collection for pagination will start after this parameter
     * @param quantityOfContacts the quantity of contacts which is necessary for pagination
     * @return collection of contact objects
     * @throws SQLException
     */
    Set<Contact> searchContacts(Contact contact, Date lowerLimit, Date upperLimit, long startContactNumber, long quantityOfContacts) throws SQLException;

    /**
     * Method returns number of contacts which are required for user and are corresponded to search filter. Used for pagination.
     *
     * @param contact    contact object which has fields for specific search
     * @param lowerLimit date of birth from which contact object will satisfy search inquiry
     * @param upperLimit date of birth up to which contact object will satisfy search inquiry
     * @return number of searched contact objects
     * @throws SQLException
     */
    long getNumberOfSearchContacts(Contact contact, Date lowerLimit, Date upperLimit) throws SQLException;

    /**
     * Method deletes contacts by their ids.
     *
     * @param contactIds ids of contact objects
     * @throws SQLException
     */
    void deleteContacts(Set<Long> contactIds) throws SQLException;

    /**
     * Method returns number of all contacts in database. Used for pagination.
     *
     * @return number of contact objects
     * @throws SQLException
     */
    long getNumberOfContacts() throws SQLException;

    /**
     * Method finds entity by email.
     *
     * @param email email of entity
     * @return entity object
     * @throws SQLException
     */
    Contact findByEmail(String email) throws SQLException;

    /**
     * Method get collection of contacts which have the required birth date.
     *
     * @param date birthday
     * @return collection of contact objects
     * @throws SQLException
     */
    Set<Contact> findContactsByBirthday(Date date) throws SQLException;
}
