package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.ContactDao;
import com.itechart.javalab.firstproject.dao.util.Builder;
import com.itechart.javalab.firstproject.dao.util.Util;
import com.itechart.javalab.firstproject.entities.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public class ContactDaoImpl implements ContactDao<Contact> {

    private static volatile ContactDaoImpl instance;

    private ContactDaoImpl() {
    }

    public static ContactDao<Contact> getInstance() {
        if (instance == null) {
            synchronized (ContactDaoImpl.class) {
                if (instance == null) {
                    instance = new ContactDaoImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public long save(Contact entity, Connection connection) throws SQLException {
        final String SAVE_CONTACT = "insert into contact (firstName, lastName, middleName, birthday, gender, nationality, maritalStatus, webSite, email, employmentPlace, " +
                "photo_id, address_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
        statement.setLong(11, entity.getPhoto().getId());
        statement.setLong(12, entity.getAddress().getId());
        long id = Util.getGeneratedIdAfterCreate(statement);
        statement.close();
        return id;
    }


    @Override
    public Contact findById(long id, Connection connection) throws SQLException {
        final String FIND_CONTACT_BY_ID = "select c.id, c.firstName, c.lastName, c.middleName, c.birthday, c.gender, c.nationality, c.maritalStatus, c.webSite, c.email, " +
                "c.employmentPlace, ad.id, ad.country, ad.city, ad.street, ad.houseNumber, ad.flatNumber, ad.postalIndex, att.id, att.fileName, att.commentary, att.recordDate, " +
                "att.path, att.uuid, pe.id, pe.countryCode, pe.operatorCode, pe.phoneNumber, pe.phoneType, pe.commentary, po.id, po.path, po.uuid from contact as c left join " +
                "address as ad on c.address_id=ad.id left join contact_attachment as ca on c.id=ca.contact_id left join attachment as att on ca.attachment_id=att.id left join " +
                "contact_phone as cp on c.id=cp.contact_id left join phone as pe on cp.phone_id=pe.id left join photo as po on c.photo_id=po.id where c.id=?;";
        PreparedStatement statement = connection.prepareStatement(FIND_CONTACT_BY_ID);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        Contact contact = new Contact();
        while (resultSet.next()) {
            Address address = new Address(resultSet.getLong("ad.id"), resultSet.getString("ad.country"), resultSet.getString("ad.city"), resultSet.getString("ad.street"),
                    resultSet.getInt("ad.houseNumber"), resultSet.getInt("ad.flatNumber"), resultSet.getInt("ad.postalIndex"));

            Phone phone = new Phone(resultSet.getLong("pe.id"), resultSet.getInt("pe.countryCode"), resultSet.getInt("pe.operatorCode"), resultSet.getLong("pe.phoneNumber"),
                    resultSet.getString("pe.phoneType"), resultSet.getString("pe.commentary"));

            Attachment attachment = new Attachment(resultSet.getLong("att.id"), resultSet.getString("att.fileName"), resultSet.getString("att.commentary"),
                    resultSet.getTimestamp("att.recordDate"), resultSet.getString("att.path"), resultSet.getString("att.uuid"));

            Photo photo = new Photo(resultSet.getLong("po.id"), resultSet.getString("po.path"), resultSet.getString("po.uuid"));

            contact.setId(resultSet.getLong("c.id"));
            contact.setFirstName(resultSet.getString("c.firstName"));
            contact.setLastName(resultSet.getString("c.lastName"));
            contact.setMiddleName(resultSet.getString("c.middleName"));
            contact.setBirthday(resultSet.getDate("c.birthday"));
            contact.setGender(resultSet.getString("c.gender"));
            contact.setNationality(resultSet.getString("c.nationality"));
            contact.setMaritalStatus(resultSet.getString("c.maritalStatus"));
            contact.setWebSite(resultSet.getString("c.webSite"));
            contact.setEmail(resultSet.getString("c.email"));
            contact.setEmploymentPlace(resultSet.getString("c.employmentPlace"));
            contact.setAddress(address);
            contact.getPhones().add(phone);
            contact.getAttachments().add(attachment);
            contact.setPhoto(photo);
        }
        statement.close();
        return contact;
    }

    @Override
    public void update(Contact entity, Connection connection) throws SQLException {
        final String UPDATE_CONTACT = "update contact set firstName = ?, lastName = ?, middleName = ?, birthday = ?, gender = ?, nationality = ?, maritalStatus = ?, webSite = ?, " +
                "email = ?, employmentPlace = ? where id = ?;";
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
        statement.setLong(11, entity.getId());
        statement.executeUpdate();
        statement.close();
    }


    @Override
    public void delete(long id, Connection connection) throws SQLException {
        final String DELETE_CONTACT = "delete from contact where id = ?;";
        PreparedStatement statement = connection.prepareStatement(DELETE_CONTACT);
        statement.setLong(1, id);
        statement.executeUpdate();
        statement.close();
    }


    @Override
    public void deleteAll(Connection connection) throws SQLException {
        String deleteAllContacts = "delete from contact;";
        String deleteAllAddresses = "delete from address;";
        String deleteAllAttachments = "delete from attachment;";
        String deleteAllPhones = "delete from phone;";
        String deleteAllPhotos = "delete from photo;";
        String deleteAllMessages = "delete from message;";
        String deleteAllContactMessage = "delete from contact_message;";
        String deleteAllContactAddress = "delete from contact_address;";

        String resetAttachmentCounter = "alter table attachment auto_increment=1;";
        String resetPhoneCounter = "alter table phone auto_increment=1;";
        String resetPhotoCounter = "alter table photo auto_increment=1;";
        String resetContactMessageCounter = "alter table contact_message auto_increment=1;";
        String resetContactAddressCounter = "alter table contact_address auto_increment=1;";
        String resetContactCounter = "alter table contact auto_increment=1;";
        String resetAddressCounter = "alter table address auto_increment=1;";
        String resetMessageCounter = "alter table message auto_increment=1;";
        connection.setAutoCommit(false);
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(deleteAllMessages);
            statement.executeUpdate(deleteAllContactMessage);
            statement.executeUpdate(deleteAllContactAddress);
            statement.executeUpdate(deleteAllAddresses);
            statement.executeUpdate(deleteAllAttachments);
            statement.executeUpdate(deleteAllPhones);
            statement.executeUpdate(deleteAllPhotos);
            statement.executeUpdate(deleteAllContacts);

            statement.executeUpdate(resetAttachmentCounter);
            statement.executeUpdate(resetPhoneCounter);
            statement.executeUpdate(resetPhotoCounter);
            statement.executeUpdate(resetContactMessageCounter);
            statement.executeUpdate(resetContactAddressCounter);
            statement.executeUpdate(resetContactCounter);
            statement.executeUpdate(resetAddressCounter);
            statement.executeUpdate(resetMessageCounter);

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            statement.close();
            connection.close();
        }
    }

    @Override
    public Set<Contact> getSetOfContacts(long startContactNumber, long quantityOfContacts, Connection connection) throws SQLException {
        final String GET_CONTACTS = "select c.id, c.firstName, c.lastName, c.birthday, c.employmentPlace, a.city, a.street, a.houseNumber, a.flatNumber from contact as c left join " +
                "contact_address as ca on c.id = ca.contact_id left join address as a on ca.address_id = a.id limit ?, ?;";
        PreparedStatement statement = connection.prepareStatement(GET_CONTACTS);
        statement.setLong(1, startContactNumber);
        statement.setLong(2, quantityOfContacts);
        ResultSet resultSet = statement.executeQuery();
        TreeSet<Contact> contacts = new TreeSet<>(Comparator.comparing(Contact::getLastName));
        while (resultSet.next()) {
            Contact contact = new Contact();
            contact.setId(resultSet.getLong("c.id"));
            contact.setFirstName(resultSet.getString("c.firstName"));
            contact.setLastName(resultSet.getString("c.lastName"));
            contact.setBirthday(resultSet.getDate("c.birthday"));
            contact.setEmploymentPlace(resultSet.getString("c.employmentPlace"));
            contact.getAddress().setCity(resultSet.getString("a.city"));
            contact.getAddress().setStreet("a.street");
            contact.getAddress().setHouseNumber(resultSet.getInt("a.houseNumber"));
            contact.getAddress().setFlatNumber(resultSet.getInt("a.flatNumber"));
            contacts.add(contact);
        }
        statement.close();
        return contacts;
    }

    @Override
    public Set<Contact> searchContacts(Contact entity, Date lowerLimit, Date upperLimit, long startContactNumber, long quantityOfContacts, Connection connection) throws SQLException {
        StringBuilder getContacts = new StringBuilder("select c.id, c.firstName, c.lastName, c.birthday, c.employmentPlace, ad.city, ad.street, ad.houseNumber, ad.flatNumber " +
                "from contact as c left join contact_address as ca on c.id=ca.contact_id left join address as ad on ca.address_id=ad.id ");

        Builder query = new Builder();
        query.where(getContacts).addConditionIfExist("firstName", entity.getFirstName(), getContacts).addConditionIfExist("lastName", entity.getLastName(),
                getContacts).addConditionIfExist("middleName", entity.getMiddleName(), getContacts).addConditionIfExist("gender", entity.getMiddleName(),
                getContacts).addConditionIfExist("maritalStatus", entity.getMiddleName(), getContacts).addConditionIfExist("nationality", entity.getNationality(),
                getContacts).addConditionIfExist("city", entity.getAddress().getCity(), getContacts).addConditionIfExist("street", entity.getAddress().getStreet(),
                getContacts).addConditionIfExist("houseNumber", entity.getAddress().getHouseNumber(), getContacts).addConditionIfExist("flatNumber",
                entity.getAddress().getFlatNumber(), getContacts).addConditionIfExist("postalIndex", entity.getAddress().getPostalIndex(),
                getContacts).addBirthdayCondition(getContacts).limit(getContacts).build(getContacts);

        PreparedStatement statement = connection.prepareStatement(getContacts.toString());
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
        if (entity.getAddress().getCity() != null) {
            statement.setString(++counter, entity.getAddress().getCity());
        }
        if (entity.getAddress().getStreet() != null) {
            statement.setString(++counter, entity.getAddress().getStreet());
        }
        if (entity.getAddress().getHouseNumber() != 0) {
            statement.setInt(++counter, entity.getAddress().getHouseNumber());
        }
        if (entity.getAddress().getFlatNumber() != 0) {
            statement.setInt(++counter, entity.getAddress().getFlatNumber());
        }
        if (entity.getAddress().getPostalIndex() != 0) {
            statement.setInt(++counter, entity.getAddress().getPostalIndex());
        }
        statement.setDate(++counter, lowerLimit);
        statement.setDate(++counter, upperLimit);
        statement.setLong(++counter, startContactNumber);
        statement.setLong(++counter, quantityOfContacts);

        ResultSet resultSet = statement.executeQuery();
        TreeSet<Contact> contacts = new TreeSet<>(Comparator.comparing(Contact::getLastName));
        Contact contact = new Contact();
        while (resultSet.next()) {
            contact.setId(resultSet.getLong("c.id"));
            contact.setFirstName(resultSet.getString("c.firstName"));
            contact.setLastName(resultSet.getString("c.lastName"));
            contact.setBirthday(resultSet.getDate("c.birthday"));
            contact.setEmploymentPlace(resultSet.getString("c.employmentPlace"));
            contact.getAddress().setCity(resultSet.getString("ad.city"));
            contact.getAddress().setStreet(resultSet.getString("ad.street"));
            contact.getAddress().setHouseNumber(resultSet.getInt("ad.houseNumber"));
            contact.getAddress().setFlatNumber(resultSet.getInt("ad.flatNumber"));
            contacts.add(contact);
        }
        statement.close();
        return contacts;
    }

    @Override
    public void addDependenceFromAttachment(long contactId, long attachmentId, Connection connection) throws SQLException {
        final String SAVE_CONTACT_ATTACHMENT = "insert into contact_attachment values (?, ?);";
        PreparedStatement statement = connection.prepareStatement(SAVE_CONTACT_ATTACHMENT);
        statement.setLong(1, contactId);
        statement.setLong(2, attachmentId);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void addDependenceFromPhone(long contactId, long phoneId, Connection connection) throws SQLException {
        final String SAVE_CONTACT_PHONE = "insert into contact_phone values (?, ?);";
        PreparedStatement statement = connection.prepareStatement(SAVE_CONTACT_PHONE);
        statement.setLong(1, contactId);
        statement.setLong(2, phoneId);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void deleteDependenceFromAttachment(long contactId, Connection connection) throws SQLException {
        final String DELETE_CONTACT_ATTACHMENT = "delete from contact_attachment where contact_id = ?;";
        PreparedStatement statement = connection.prepareStatement(DELETE_CONTACT_ATTACHMENT);
        statement.setLong(1, contactId);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void deleteDependenceFromPhone(long contactId, Connection connection) throws SQLException {
        final String DELETE_CONTACT_PHONE = "delete from contact_phone where contact_id = ?;";
        PreparedStatement statement = connection.prepareStatement(DELETE_CONTACT_PHONE);
        statement.setLong(1, contactId);
        statement.executeUpdate();
        statement.close();
    }
}
