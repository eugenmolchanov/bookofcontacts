package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.ContactDao;
import com.itechart.javalab.firstproject.dao.util.Builder;
import com.itechart.javalab.firstproject.dao.util.Util;
import com.itechart.javalab.firstproject.entities.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import static com.itechart.javalab.firstproject.dao.util.EntityBuilder.*;
import static com.itechart.javalab.firstproject.dao.util.FieldNameConstant.*;
import static com.itechart.javalab.firstproject.dao.util.Util.getRecordsNumber;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public class ContactDaoImpl implements ContactDao {

    private ContactDaoImpl() {
    }

    private static ContactDaoImpl instance;

    private static final String SAVE_CONTACT = "insert into contact (first_name, last_name, middle_name, birth_date, " +
            "gender, nationality, marital_status, website, email, employment_place, contact_group, country, city, street, " +
            "house_number, flat_number, postcode, photo_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String FIND_CONTACT_BY_ID = "select c.id, c.first_name, c.last_name, c.middle_name, c.birth_date, " +
            "c.gender, c.nationality, c.marital_status, c.website, c.email, c.employment_place, c.contact_group, c.country, " +
            "c.city, c.street, c.house_number, c.flat_number, c.postcode, att.id, att.file_name, att.commentary, " +
            "att.record_date, att.path, att.uuid, att.contact_id, pe.id, pe.country_code, pe.operator_code, pe.phone_number, pe.phone_type, " +
            "pe.commentary, pe.contact_id, po.id, po.path, po.uuid from contact as c left join attachment as att on c.id=att.contact_id left " +
            "join phone as pe on c.id=pe.contact_id inner join photo as po on c.photo_id=po.id where c.id=?;";
    private static final String UPDATE_CONTACT = "update contact set first_name = ?, last_name = ?, middle_name = ?, " +
            "birth_date = ?, gender = ?, nationality = ?, marital_status = ?, website = ?, email = ?, employment_place = ?, " +
            "contact_group = ?, country = ?, city = ?, street = ?, house_number = ?, flat_number = ?, postcode = ? " +
            "where id = ?;";

    private static final String DELETE_CONTACT = "delete from contact where id = ?;";
    private static final String DELETE_CONTACT_ATTACHMENTS = "delete from attachment where contact_id=?;";
    private static final String DELETE_CONTACT_PHONES = "delete from phone where contact_id=?;";
    private static final String DELETE_MESSAGES = "delete from message where contact_id = ?;";

    private static final String DELETE_ALL_ATTACHMENTS = "delete from attachment;";
    private static final String DELETE_ALL_PHONES = "delete from phone;";
    private static final String DELETE_ALL_CONTACTS = "delete from contact;";
    private static final String DELETE_ALL_PHOTOS = "delete from photo;";
    private static final String DELETE_ALL_MESSAGES = "delete from message;";

    private static final String RESET_ATTACHMENT_COUNTER = "alter table attachment auto_increment=1;";
    private static final String RESET_PHONE_COUNTER = "alter table phone auto_increment=1;";
    private static final String RESET_PHOTO_COUNTER = "alter table photo auto_increment=1;";
    private static final String RESET_CONTACT_COUNTER = "alter table contact auto_increment=1;";
    private static final String RESET_MESSAGE_COUNTER = "alter table message auto_increment=1;";

    private static final String GET_CONTACTS = "select id, first_name, last_name, birth_date, email, employment_place, " +
            "contact_group, country, city, street, house_number, flat_number, postcode from contact order by first_name " +
            "limit ?, ?;";
    private static StringBuilder getContacts = new StringBuilder("select id, first_name, last_name, birth_date, email, " +
            "employment_place, contact_group, country, city, street, house_number, flat_number, postcode from contact ");
    private static StringBuilder getSearchedContacts = new StringBuilder("select count(id) from contact ");
    private static final String COUNT_CONTACTS = "select count(id) from contact;";
    private static final String FIND_CONTACT_BY_EMAIL = "select c.id, c.first_name, c.last_name, c.middle_name, c.birth_date, c.gender, " +
            "c.nationality, c.marital_status, c.website, c.email, c.employment_place, c.contact_group, c.country, c.city, c.street, " +
            "c.house_number, c.flat_number, c.postcode from contact as c where c.email=?;";
    private static final String GET_CONTACTS_BY_BIRTHDAY = "select id, first_name, last_name, email from contact where " +
            "dayofmonth(birth_date) = dayofmonth(?) and month(birth_date) = month(?);";

    public static ContactDao getInstance() {
        if (instance == null) {
            instance = new ContactDaoImpl();
        }
        return instance;
    }

    @Override
    public long save(Contact entity, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_CONTACT, RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getMiddleName());
            statement.setDate(4, entity.getBirthday());
            statement.setString(5, entity.getGender());
            statement.setString(6, entity.getNationality());
            statement.setString(7, entity.getMaritalStatus());
            statement.setString(8, entity.getWebSite());
            statement.setString(9, entity.getEmail());
            statement.setString(10, entity.getEmploymentPlace());
            statement.setString(11, entity.getContactGroup());
            statement.setString(12, entity.getCountry());
            statement.setString(13, entity.getCity());
            statement.setString(14, entity.getStreet());
            statement.setString(15, entity.getHouseNumber());
            statement.setInt(16, entity.getFlatNumber());
            statement.setInt(17, entity.getPostcode());
            statement.setLong(18, entity.getPhoto().getId());
            statement.executeUpdate();
            return Util.getGeneratedIdAfterCreate(statement);
        }
    }


    @Override
    public Contact findById(long id, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_CONTACT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                Contact contact = new Contact();
                while (resultSet.next()) {
                    Phone phone = createAndInitializePhone(resultSet);

                    Attachment attachment = createAndInitializeAttachment(resultSet);

                    Photo photo = createAndInitializePhoto(resultSet);

                    contact = initializeContact(contact, resultSet);
                    if (phone.getId() != 0) {
                        contact.getPhones().add(phone);
                    }
                    if (attachment.getId() != 0) {
                        contact.getAttachments().add(attachment);
                    }
                    contact.setPhoto(photo);
                }
                return contact;
            }
        }
    }

    @Override
    public void update(Contact entity, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CONTACT)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getMiddleName());
            statement.setDate(4, entity.getBirthday());
            statement.setString(5, entity.getGender());
            statement.setString(6, entity.getNationality());
            statement.setString(7, entity.getMaritalStatus());
            statement.setString(8, entity.getWebSite());
            statement.setString(9, entity.getEmail());
            statement.setString(10, entity.getEmploymentPlace());
            statement.setString(11, entity.getContactGroup());
            statement.setString(12, entity.getCountry());
            statement.setString(13, entity.getCity());
            statement.setString(14, entity.getStreet());
            statement.setString(15, entity.getHouseNumber());
            statement.setInt(16, entity.getFlatNumber());
            statement.setInt(17, entity.getPostcode());
            statement.setLong(18, entity.getId());
            statement.executeUpdate();
        }
    }


    @Override
    public void delete(long id, Connection connection) throws SQLException {
        deleteByContactId(id, DELETE_MESSAGES, connection);

        deleteByContactId(id, DELETE_CONTACT_ATTACHMENTS, connection);

        deleteByContactId(id, DELETE_CONTACT_PHONES, connection);

        deleteByContactId(id, DELETE_CONTACT, connection);
    }


    @Override
    public void deleteAll(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE_ALL_MESSAGES);
            statement.executeUpdate(DELETE_ALL_ATTACHMENTS);
            statement.executeUpdate(DELETE_ALL_PHONES);
            statement.executeUpdate(DELETE_ALL_CONTACTS);
            statement.executeUpdate(DELETE_ALL_PHOTOS);

            statement.executeUpdate(RESET_ATTACHMENT_COUNTER);
            statement.executeUpdate(RESET_PHONE_COUNTER);
            statement.executeUpdate(RESET_PHOTO_COUNTER);
            statement.executeUpdate(RESET_CONTACT_COUNTER);
            statement.executeUpdate(RESET_MESSAGE_COUNTER);
        }
    }

    @Override
    public Set<Contact> getContactsList(long startContactNumber, long quantityOfContacts,
                                        Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_CONTACTS)) {
            statement.setLong(1, startContactNumber);
            statement.setLong(2, quantityOfContacts);
            try (ResultSet resultSet = statement.executeQuery()) {
                TreeSet<Contact> contacts = new TreeSet<>(Comparator.comparing(Contact::getLastName)
                        .thenComparing(Contact::getFirstName)
                        .thenComparing(Contact::getId));
                while (resultSet.next()) {
                    Contact contact = createAndInitializeContactForMainPage(resultSet);
                    contacts.add(contact);
                }
                return contacts;
            }
        }
    }

    @Override
    public Set<Contact> searchContacts(Contact entity, Date lowerLimit, Date upperLimit, long startContactNumber,
                                       long quantityOfContacts, Connection connection) throws SQLException {
        Builder query = new Builder();
        String whereClause = "" + query.getWhereClause(entity, lowerLimit, upperLimit);
        if (!whereClause.isEmpty()) {
            query.where(getContacts);
            whereClause = whereClause.substring(0, whereClause.length() - 4);
            getContacts.append(whereClause);
        }
        query.orderBy(getContacts).limit(getContacts).build(getContacts);
        try (PreparedStatement statement = connection.prepareStatement(getContacts.toString())) {
            int counter = 0;
            if (entity.getFirstName() != null) {
                statement.setString(++counter, entity.getFirstName());
            }
            if (entity.getLastName() != null) {
                statement.setString(++counter, entity.getLastName());
            }
            if (entity.getMiddleName() != null) {
                statement.setString(++counter, entity.getMiddleName());
            }
            if (entity.getGender() != null) {
                statement.setString(++counter, entity.getGender());
            }
            if (entity.getMaritalStatus() != null) {
                statement.setString(++counter, entity.getMaritalStatus());
            }
            if (entity.getNationality() != null) {
                statement.setString(++counter, entity.getNationality());
            }
            if (entity.getContactGroup() != null) {
                statement.setString(++counter, entity.getContactGroup());
            }
            if (entity.getCountry() != null) {
                statement.setString(++counter, entity.getCountry());
            }
            if (entity.getCity() != null) {
                statement.setString(++counter, entity.getCity());
            }
            if (entity.getStreet() != null) {
                statement.setString(++counter, entity.getStreet());
            }
            if (entity.getHouseNumber() != null) {
                statement.setString(++counter, entity.getHouseNumber());
            }
            if (entity.getFlatNumber() != 0) {
                statement.setInt(++counter, entity.getFlatNumber());
            }
            if (entity.getPostcode() != 0) {
                statement.setInt(++counter, entity.getPostcode());
            }
            if (lowerLimit != null) {
                statement.setDate(++counter, lowerLimit);
            }
            if (upperLimit != null) {
                statement.setDate(++counter, upperLimit);
            }
            statement.setString(++counter, FIRST_NAME);
            statement.setLong(++counter, startContactNumber);
            statement.setLong(++counter, quantityOfContacts);
            try (ResultSet resultSet = statement.executeQuery()) {
                TreeSet<Contact> contacts = new TreeSet<>(Comparator.comparing(Contact::getLastName)
                        .thenComparing(Contact::getFirstName));
                while (resultSet.next()) {
                    Contact contact = createAndInitializeContactForMainPage(resultSet);
                    contacts.add(contact);
                }
                return contacts;
            }
        }
    }

    @Override
    public long getNumberOfSearchContacts(Contact entity, Date lowerLimit, Date upperLimit,
                                          Connection connection) throws SQLException {
        Builder query = new Builder();
        String whereClause = "" + query.getWhereClause(entity, lowerLimit, upperLimit);
        if (!whereClause.equals("")) {
            query.where(getSearchedContacts);
            whereClause = whereClause.substring(0, whereClause.length() - 4);
            getSearchedContacts.append(whereClause);
        }
        query.build(getSearchedContacts);
        try (PreparedStatement statement = connection.prepareStatement(getSearchedContacts.toString())) {
            int counter = 0;
            if (entity.getFirstName() != null) {
                statement.setString(++counter, entity.getFirstName());
            }
            if (entity.getLastName() != null) {
                statement.setString(++counter, entity.getLastName());
            }
            if (entity.getMiddleName() != null) {
                statement.setString(++counter, entity.getMiddleName());
            }
            if (entity.getGender() != null) {
                statement.setString(++counter, entity.getGender());
            }
            if (entity.getMaritalStatus() != null) {
                statement.setString(++counter, entity.getMaritalStatus());
            }
            if (entity.getNationality() != null) {
                statement.setString(++counter, entity.getNationality());
            }
            if (entity.getContactGroup() != null) {
                statement.setString(++counter, entity.getContactGroup());
            }
            if (entity.getCountry() != null) {
                statement.setString(++counter, entity.getCountry());
            }
            if (entity.getCity() != null) {
                statement.setString(++counter, entity.getCity());
            }
            if (entity.getStreet() != null) {
                statement.setString(++counter, entity.getStreet());
            }
            if (entity.getHouseNumber() != null) {
                statement.setString(++counter, entity.getHouseNumber());
            }
            if (entity.getFlatNumber() != 0) {
                statement.setInt(++counter, entity.getFlatNumber());
            }
            if (entity.getPostcode() != 0) {
                statement.setInt(++counter, entity.getPostcode());
            }
            if (lowerLimit != null) {
                statement.setDate(++counter, lowerLimit);
            }
            if (upperLimit != null) {
                statement.setDate(++counter, upperLimit);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                long totalQuantity = 0;
                while (resultSet.next()) {
                    totalQuantity = resultSet.getLong("count(id)");
                }

                return totalQuantity;
            }
        }
    }

    @Override
    public long getNumberOfContacts(Connection connection) throws SQLException {
        return getRecordsNumber(COUNT_CONTACTS, connection);
    }

    @Override
    public Contact findByEmail(String email, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_CONTACT_BY_EMAIL)) {
            statement.setString(1, email);
            Contact contact = new Contact();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    contact = initializeContact(contact, resultSet);
                }
                return contact;
            }
        }
    }

    @Override
    public Set<Contact> findContactsByBirthday(Date date, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_CONTACTS_BY_BIRTHDAY)) {
            statement.setDate(1, date);
            statement.setDate(2, date);
            try (ResultSet resultSet = statement.executeQuery()) {
                Set<Contact> contacts = new HashSet<>();
                while (resultSet.next()) {
                    Contact contact = new Contact();
                    contact.setId(resultSet.getLong(ID));
                    contact.setFirstName(resultSet.getString(FIRST_NAME));
                    contact.setLastName(resultSet.getString(LAST_NAME));
                    contact.setBirthday(date);
                    contact.setEmail(resultSet.getString(EMAIL));
                    contacts.add(contact);
                }
                return contacts;
            }
        }
    }

    private Contact initializeContact(Contact contact, ResultSet resultSet) throws SQLException {
        contact.setId(resultSet.getLong(ID));
        contact.setFirstName(resultSet.getString(FIRST_NAME));
        contact.setLastName(resultSet.getString(LAST_NAME));
        contact.setMiddleName(resultSet.getString(MIDDLE_NAME));
        contact.setBirthday(resultSet.getDate(BIRTH_DATE));
        contact.setGender(resultSet.getString(GENDER));
        contact.setNationality(resultSet.getString(NATIONALITY));
        contact.setMaritalStatus(resultSet.getString(MARITAL_STATUS));
        contact.setWebSite(resultSet.getString(WEBSITE));
        contact.setEmail(resultSet.getString(EMAIL));
        contact.setEmploymentPlace(resultSet.getString(EMPLOYMENT_PLACE));
        contact.setContactGroup(resultSet.getString(CONTACT_GROUP));
        contact.setCountry(resultSet.getString(COUNTRY));
        contact.setCity(resultSet.getString(CITY));
        contact.setStreet(resultSet.getString(STREET));
        contact.setHouseNumber(resultSet.getString(HOUSE_NUMBER));
        contact.setFlatNumber(resultSet.getInt(FLAT_NUMBER));
        contact.setPostcode(resultSet.getInt(POSTCODE));
        return contact;
    }

    private void deleteByContactId(long id, String deleteSqlQuery, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(deleteSqlQuery)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
