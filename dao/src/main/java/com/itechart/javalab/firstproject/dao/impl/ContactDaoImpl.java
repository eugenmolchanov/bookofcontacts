package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.ContactDao;
import com.itechart.javalab.firstproject.dao.util.Util;
import com.itechart.javalab.firstproject.entities.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public class ContactDaoImpl implements ContactDao {

    private static ContactDaoImpl instance;

    private ContactDaoImpl() {
    }

    public static ContactDao getInstance() {
        if (instance == null) {
            instance = new ContactDaoImpl();
        }
        return instance;
    }

    @Override
    public long save(Contact entity, Connection connection) throws SQLException {
        final String SAVE_CONTACT = "insert into contact (first_name, last_name, middle_name, birth_date, gender, nationality, marital_status, website, email, employment_place, " +
                "contact_group, country, city, street, house_number, flat_number, postcode, photo_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(SAVE_CONTACT, Statement.RETURN_GENERATED_KEYS);
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
        long id = Util.getGeneratedIdAfterCreate(statement);
        statement.close();
        return id;
    }


    @Override
    public Contact findById(long id, Connection connection) throws SQLException {
        final String FIND_CONTACT_BY_ID = "select c.id, c.first_name, c.last_name, c.middle_name, c.birth_date, c.gender, c.nationality, c.marital_status, c.website, c.email, " +
                "c.employment_place, c.contact_group, c.country, c.city, c.street, c.house_number, c.flat_number, c.postcode, att.id, att.file_name, att.commentary, att.record_date, " +
                "att.path, att.uuid, pe.id, pe.country_code, pe.operator_code, pe.phone_number, pe.phone_type, pe.commentary, po.id, po.path, po.uuid from contact as c left join " +
                "attachment as att on c.id=att.contact_id left join phone as pe on c.id=pe.contact_id inner join photo as po on c.photo_id=po.id where c.id=?;";
        PreparedStatement statement = connection.prepareStatement(FIND_CONTACT_BY_ID);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        Contact contact = new Contact();
        while (resultSet.next()) {
            Phone phone = new Phone(resultSet.getLong("pe.id"), resultSet.getString("pe.country_code"), resultSet.getInt("pe.operator_code"), resultSet.getLong("pe.phone_number"),
                    resultSet.getString("pe.phone_type"), resultSet.getString("pe.commentary"), resultSet.getLong("c.id"));

            Attachment attachment = new Attachment(resultSet.getLong("att.id"), resultSet.getString("att.file_name"), resultSet.getString("att.commentary"),
                    resultSet.getTimestamp("att.record_date"), resultSet.getString("att.path"), resultSet.getString("att.uuid"), resultSet.getLong("c.id"));

            Photo photo = new Photo(resultSet.getLong("po.id"), resultSet.getString("po.path"), resultSet.getString("po.uuid"));

            contact.setId(resultSet.getLong("c.id"));
            contact.setFirstName(resultSet.getString("c.first_name"));
            contact.setLastName(resultSet.getString("c.last_name"));
            contact.setMiddleName(resultSet.getString("c.middle_name"));
            contact.setBirthday(resultSet.getDate("c.birth_date"));
            contact.setGender(resultSet.getString("c.gender"));
            contact.setNationality(resultSet.getString("c.nationality"));
            contact.setMaritalStatus(resultSet.getString("c.marital_status"));
            contact.setWebSite(resultSet.getString("c.website"));
            contact.setEmail(resultSet.getString("c.email"));
            contact.setEmploymentPlace(resultSet.getString("c.employment_place"));
            contact.setContactGroup(resultSet.getString("c.contact_group"));
            contact.setCountry(resultSet.getString("c.country"));
            contact.setCity(resultSet.getString("c.city"));
            contact.setStreet(resultSet.getString("c.street"));
            contact.setHouseNumber(resultSet.getString("c.house_number"));
            contact.setFlatNumber(resultSet.getInt("c.flat_number"));
            contact.setPostcode(resultSet.getInt("c.postcode"));
            if (phone.getId() != 0) {
                contact.getPhones().add(phone);
            }
            if (attachment.getId() != 0) {
                contact.getAttachments().add(attachment);
            }
            contact.setPhoto(photo);
        }
        statement.close();
        return contact;
    }

    @Override
    public void update(Contact entity, Connection connection) throws SQLException {
        final String UPDATE_CONTACT = "update contact set first_name = ?, last_name = ?, middle_name = ?, birth_date = ?, gender = ?, nationality = ?, marital_status = ?, " +
                "website = ?, email = ?, employment_place = ?, contact_group = ?, country = ?, city = ?, street = ?, house_number = ?, flat_number = ?, postcode = ? where id = ?;";
        PreparedStatement statement = connection.prepareStatement(UPDATE_CONTACT);
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
        statement.close();
    }


    @Override
    public void delete(long id, Connection connection) throws SQLException {
        final String DELETE_CONTACT = "delete from contact where id = ?;";
        final String DELETE_CONTACT_ATTACHMENTS = "delete from attachment where contact_id=?;";
        final String DELETE_CONTACT_PHONES = "delete from phone where contact_id=?;";
        final String DELETE_CONTACT_MESSAGE = "delete from contact_message where contact_id = ?;";
        PreparedStatement statement = connection.prepareStatement(DELETE_CONTACT_MESSAGE);
        statement.setLong(1, id);
        statement.executeUpdate();

        statement = connection.prepareStatement(DELETE_CONTACT_ATTACHMENTS);
        statement.setLong(1, id);
        statement.executeUpdate();

        statement = connection.prepareStatement(DELETE_CONTACT_PHONES);
        statement.setLong(1, id);
        statement.executeUpdate();

        statement = connection.prepareStatement(DELETE_CONTACT);
        statement.setLong(1, id);
        statement.executeUpdate();

        statement.close();
    }


    @Override
    public void deleteAll(Connection connection) throws SQLException {
        String deleteAllContactMessage = "delete from contact_message;";
        String deleteAllAttachments = "delete from attachment;";
        String deleteAllPhones = "delete from phone;";
        String deleteAllContacts = "delete from contact;";
        String deleteAllPhotos = "delete from photo;";
        String deleteAllMessages = "delete from message;";

        String resetAttachmentCounter = "alter table attachment auto_increment=1;";
        String resetPhoneCounter = "alter table phone auto_increment=1;";
        String resetPhotoCounter = "alter table photo auto_increment=1;";
        String resetContactCounter = "alter table contact auto_increment=1;";
        String resetMessageCounter = "alter table message auto_increment=1;";
        Statement statement = connection.createStatement();
        statement.executeUpdate(deleteAllContactMessage);
        statement.executeUpdate(deleteAllMessages);
        statement.executeUpdate(deleteAllAttachments);
        statement.executeUpdate(deleteAllPhones);
        statement.executeUpdate(deleteAllContacts);
        statement.executeUpdate(deleteAllPhotos);

        statement.executeUpdate(resetAttachmentCounter);
        statement.executeUpdate(resetPhoneCounter);
        statement.executeUpdate(resetPhotoCounter);
        statement.executeUpdate(resetContactCounter);
        statement.executeUpdate(resetMessageCounter);
        statement.close();
    }

    @Override
    public Set<Contact> getSetOfContacts(long startContactNumber, long quantityOfContacts, Connection connection) throws SQLException {
        final String GET_CONTACTS = "select id, first_name, last_name, birth_date, email, employment_place, contact_group, country, city, street, house_number, " +
                "flat_number, postcode from contact order by first_name limit ?, ?;";
        PreparedStatement statement = connection.prepareStatement(GET_CONTACTS);
        statement.setLong(1, startContactNumber);
        statement.setLong(2, quantityOfContacts);
        ResultSet resultSet = statement.executeQuery();
        TreeSet<Contact> contacts = new TreeSet<>(Comparator.comparing(Contact::getLastName).thenComparing(Contact::getFirstName).thenComparing(Contact::getId));
        while (resultSet.next()) {
            Contact contact = new Contact();
            contact.setId(resultSet.getLong("id"));
            contact.setFirstName(resultSet.getString("first_name"));
            contact.setLastName(resultSet.getString("last_name"));
            contact.setBirthday(resultSet.getDate("birth_date"));
            contact.setEmail(resultSet.getString("email"));
            contact.setEmploymentPlace(resultSet.getString("employment_place"));
            contact.setContactGroup(resultSet.getString("contact_group"));
            contact.setCountry(resultSet.getString("country"));
            contact.setCity(resultSet.getString("city"));
            contact.setStreet(resultSet.getString("street"));
            contact.setHouseNumber(resultSet.getString("house_number"));
            contact.setFlatNumber(resultSet.getInt("flat_number"));
            contact.setPostcode(resultSet.getInt("postcode"));
            contacts.add(contact);
        }
        statement.close();
        return contacts;
    }

    @Override
    public Set<Contact> searchContacts(Contact entity, Date lowerLimit, Date upperLimit, long startContactNumber, long quantityOfContacts, Connection connection) throws SQLException {
        final String GET_CONTACTS = "select id, first_name, last_name, birth_date, email, employment_place, contact_group, country, city, street, house_number, " +
                "flat_number, postcode from contact where (first_name = ? or ? is null) and (last_name = ? or ? is null) and (middle_name = ? or ? is null) and (gender = ? or ? is null) " +
                "and (marital_status = ? or ? is null) and (nationality = ? or ? is null) and (contact_group = ? or ? is null) and (country = ? or ? is null)" +
                " and (city = ? or ? is null) and (street = ? or ? is null) and (house_number = ? or ? is null) and (flat_number = ? or ? = 0) and (postcode = ? or ? = 0)" +
                " and birth_date between ? and ? order by last_name limit ?, ?;";

        PreparedStatement statement = connection.prepareStatement(GET_CONTACTS);
        statement.setString(1, entity.getFirstName());
        statement.setString(2, entity.getFirstName());
        statement.setString(3, entity.getLastName());
        statement.setString(4, entity.getLastName());
        statement.setString(5, entity.getMiddleName());
        statement.setString(6, entity.getMiddleName());
        statement.setString(7, entity.getGender());
        statement.setString(8, entity.getGender());
        statement.setString(9, entity.getMaritalStatus());
        statement.setString(10, entity.getMaritalStatus());
        statement.setString(11, entity.getNationality());
        statement.setString(12, entity.getNationality());
        statement.setString(13, entity.getContactGroup());
        statement.setString(14, entity.getContactGroup());
        statement.setString(15, entity.getCountry());
        statement.setString(16, entity.getCountry());
        statement.setString(17, entity.getCity());
        statement.setString(18, entity.getCity());
        statement.setString(19, entity.getStreet());
        statement.setString(20, entity.getStreet());
        statement.setString(21, entity.getHouseNumber());
        statement.setString(22, entity.getHouseNumber());
        statement.setInt(23, entity.getFlatNumber());
        statement.setInt(24, entity.getFlatNumber());
        statement.setInt(25, entity.getPostcode());
        statement.setInt(26, entity.getPostcode());
        statement.setDate(27, lowerLimit);
        statement.setDate(28, upperLimit);
        statement.setLong(29, startContactNumber);
        statement.setLong(30, quantityOfContacts);

        ResultSet resultSet = statement.executeQuery();
        TreeSet<Contact> contacts = new TreeSet<>(Comparator.comparing(Contact::getLastName));
        while (resultSet.next()) {
            Contact contact = new Contact();
            contact.setId(resultSet.getLong("id"));
            contact.setFirstName(resultSet.getString("first_name"));
            contact.setLastName(resultSet.getString("last_name"));
            contact.setBirthday(resultSet.getDate("birth_date"));
            contact.setEmail(resultSet.getString("email"));
            contact.setEmploymentPlace(resultSet.getString("employment_place"));
            contact.setContactGroup(resultSet.getString("contact_group"));
            contact.setCountry(resultSet.getString("country"));
            contact.setCity(resultSet.getString("city"));
            contact.setStreet(resultSet.getString("street"));
            contact.setHouseNumber(resultSet.getString("house_number"));
            contact.setFlatNumber(resultSet.getInt("flat_number"));
            contact.setPostcode(resultSet.getInt("postcode"));
            contacts.add(contact);
        }
        statement.close();
        return contacts;
    }

    @Override
    public long getNumberOfSearchContacts(Contact contact, Date lowerLimit, Date upperLimit, Connection connection) throws SQLException {
        final String GET_CONTACTS = "select count(id) from contact where (first_name = ? or ? is null) and (last_name = ? or ? is null) and (middle_name = ? or ? is null) " +
                "and (gender = ? or ? is null) and (marital_status = ? or ? is null) and (nationality = ? or ? is null) and (contact_group = ? or ? is null) " +
                "and (country = ? or ? is null)and (city = ? or ? is null) and (street = ? or ? is null) and (house_number = ? or ? is null) and (flat_number = ? or ? = 0) " +
                "and (postcode = ? or ? = 0) and birth_date between ? and ?;";

        PreparedStatement statement = connection.prepareStatement(GET_CONTACTS);
        statement.setString(1, contact.getFirstName());
        statement.setString(2, contact.getFirstName());
        statement.setString(3, contact.getLastName());
        statement.setString(4, contact.getLastName());
        statement.setString(5, contact.getMiddleName());
        statement.setString(6, contact.getMiddleName());
        statement.setString(7, contact.getGender());
        statement.setString(8, contact.getGender());
        statement.setString(9, contact.getMaritalStatus());
        statement.setString(10, contact.getMaritalStatus());
        statement.setString(11, contact.getNationality());
        statement.setString(12, contact.getNationality());
        statement.setString(13, contact.getContactGroup());
        statement.setString(14, contact.getContactGroup());
        statement.setString(15, contact.getCountry());
        statement.setString(16, contact.getCountry());
        statement.setString(17, contact.getCity());
        statement.setString(18, contact.getCity());
        statement.setString(19, contact.getStreet());
        statement.setString(20, contact.getStreet());
        statement.setString(21, contact.getHouseNumber());
        statement.setString(22, contact.getHouseNumber());
        statement.setInt(23, contact.getFlatNumber());
        statement.setInt(24, contact.getFlatNumber());
        statement.setInt(25, contact.getPostcode());
        statement.setInt(26, contact.getPostcode());
        statement.setDate(27, lowerLimit);
        statement.setDate(28, upperLimit);

        ResultSet resultSet = statement.executeQuery();
        long totalQuantity = 0;
        while (resultSet.next()) {
            totalQuantity = resultSet.getLong("count(id)");
        }
        statement.close();
        return totalQuantity;
    }

    @Override
    public long getNumberOfContacts(Connection connection) throws SQLException {
        final String countContacts = "select count(id) from contact;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(countContacts);
        long number = 0;
        while (resultSet.next()) {
            number = resultSet.getLong("count(id)");
        }
        statement.close();
        return number;
    }

    @Override
    public Contact findByEmail(String email, Connection connection) throws SQLException {
        final String FIND_CONTACT_BY_EMAIL = "select first_name, last_name, middle_name, birth_date, gender, nationality, marital_status, website, email, " +
                "employment_place, contact_group, country, city, street, house_number, flat_number, postcode from contact where email=?;";
        PreparedStatement statement = connection.prepareStatement(FIND_CONTACT_BY_EMAIL);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        Contact contact = new Contact();
        while (resultSet.next()) {
            contact.setFirstName(resultSet.getString("first_name"));
            contact.setLastName(resultSet.getString("last_name"));
            contact.setMiddleName(resultSet.getString("middle_name"));
            contact.setBirthday(resultSet.getDate("birth_date"));
            contact.setGender(resultSet.getString("gender"));
            contact.setNationality(resultSet.getString("nationality"));
            contact.setMaritalStatus(resultSet.getString("marital_status"));
            contact.setWebSite(resultSet.getString("website"));
            contact.setEmail(resultSet.getString("email"));
            contact.setEmploymentPlace(resultSet.getString("employment_place"));
            contact.setContactGroup(resultSet.getString("contact_group"));
            contact.setCountry(resultSet.getString("country"));
            contact.setCity(resultSet.getString("city"));
            contact.setStreet(resultSet.getString("street"));
            contact.setHouseNumber(resultSet.getString("house_number"));
            contact.setFlatNumber(resultSet.getInt("flat_number"));
            contact.setPostcode(resultSet.getInt("postcode"));
        }
        statement.close();
        return contact;
    }
}
