package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.ContactDao;
import com.itechart.javalab.firstproject.dao.connection.Database;
import com.itechart.javalab.firstproject.entities.*;

import java.sql.*;

/**
 * Created by Евгений Молчанов on 06.09.2017.
 */
public class ContactDaoImpl implements ContactDao<Contact> {
    @Override
    public void save(Contact entity) throws SQLException {
        String saveContact = "insert into contact (firstName, lastName, middleName, birthday, gender, nationality, maritalStatus, webSite, email, employmentPlace)" +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String saveAddress = "insert into address (city, street, houseNumber, flatNumber, postalIndex) values (?, ?, ?, ?, ?);";
        String savePhone = "insert into phone (countryCode, operatorCode, phoneNumber, phoneType, commentary, contact_id) values (?, ?, ?, ?, ?, ?);";
        String saveAttachment = "insert into attachment (fileName, commentary, recordDate, path, contact_id) values (?, ?, ?, ?, ?);";
        String savePhoto = "insert into photo (path, contact_id) values (?, ?);";
        String saveContactAddress = "insert into contact_address values (?, ?);";
        String checkAddress = "select id from address where city=? and street=? and houseNumber=? and flatNumber=?;";

        Connection connection = Database.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(saveContact, Statement.RETURN_GENERATED_KEYS);
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
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating contact failed, no rows affected.");
            }
            entity.setId(Database.getGeneratedIdAfterCreate(statement));

            statement = connection.prepareStatement(checkAddress);
            statement.setString(1, entity.getAddress().getCity());
            statement.setString(2, entity.getAddress().getStreet());
            statement.setInt(3, entity.getAddress().getHouseNumber());
            statement.setInt(4, entity.getAddress().getFlatNumber());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                statement = connection.prepareStatement(saveContactAddress);
                statement.setLong(1, entity.getId());
                statement.setLong(2, resultSet.getLong("id"));
                statement.executeUpdate();
            } else {
                statement = connection.prepareStatement(saveAddress, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, entity.getAddress().getCity());
                statement.setString(2, entity.getAddress().getStreet());
                statement.setInt(3, entity.getAddress().getHouseNumber());
                statement.setInt(4, entity.getAddress().getFlatNumber());
                statement.setInt(5, entity.getAddress().getPostalIndex());
                statement.executeUpdate();
                int affectedAddressRows = statement.executeUpdate();
                if (affectedAddressRows == 0) {
                    throw new SQLException("Creating address failed, no rows affected.");
                }
                entity.getAddress().setId(Database.getGeneratedIdAfterCreate(statement));
                statement = connection.prepareStatement(saveContactAddress);
                statement.setLong(1, entity.getId());
                statement.setLong(2, entity.getAddress().getId());
                statement.executeUpdate();
            }

            for (Attachment attachment : entity.getAttachments()) {
                statement = connection.prepareStatement(saveAttachment);
                statement.setString(1, attachment.getFileName());
                statement.setString(2, attachment.getCommentary());
                statement.setDate(3, attachment.getDate());
                statement.setString(4, attachment.getPathToFile());
                statement.setLong(5, entity.getId());
                statement.executeUpdate();
            }

            for (Phone phone : entity.getPhones()) {
                statement = connection.prepareStatement(savePhone);
                statement.setInt(1, phone.getCountryCode());
                statement.setInt(2, phone.getOperatorCode());
                statement.setLong(3, phone.getNumber());
                statement.setString(4, phone.getType());
                statement.setString(5, phone.getComment());
                statement.setLong(6, entity.getId());
                statement.executeUpdate();
            }

            statement = connection.prepareStatement(savePhoto);
            statement.setString(1, entity.getPhoto().getPathToFile());
            statement.setLong(2, entity.getId());
            statement.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            statement.close();
            connection.close();
        }
    }

    @Override
    public Contact findById(long id) throws SQLException {
        String findContactById = "select c.id, c.firstName, c.lastName, c.middleName, c.birthday, c.gender, c.nationality, c.maritalStatus, c.webSite, c.email, " +
                "c.employmentPlace, ad.id, ad.city, ad.street, ad.houseNumber, ad.flatNumber, ad.postalIndex, att.id, att.fileName, att.commentary, att.recordDate, " +
                "att.path, pe.id, pe.countryCode, pe.operatorCode, pe.phoneNumber, pe.phoneType, pe.commentary, po.id, po.path from contact as c left join contact_address " +
                "as ca on c.id=ca.contact_id left join address as ad on ca.address_id=ad.id left join attachment as att on c.id=att.contact_id left join phone as pe on " +
                "c.id=pe.contact_id left join photo as po on c.id=po.contact_id where c.id=?;";
        Connection connection = Database.getConnection();
        PreparedStatement statement = connection.prepareStatement(findContactById);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        Contact contact = new Contact();
        while (resultSet.next()) {
            Address address = new Address(resultSet.getLong("ad.id"), resultSet.getString("ad.city"), resultSet.getString("ad.street"), resultSet.getInt("ad.houseNumber"),
                    resultSet.getInt("ad.flatNumber"), resultSet.getInt("ad.postalIndex"));

            Phone phone = new Phone(resultSet.getLong("pe.id"), resultSet.getInt("pe.countryCode"), resultSet.getInt("pe.operatorCode"), resultSet.getLong("pe.phoneNumber"),
                    resultSet.getString("pe.phoneType"), resultSet.getString("pe.commentary"));

            Attachment attachment = new Attachment(resultSet.getLong("att.id"), resultSet.getString("att.fileName"), resultSet.getString("att.commentary"),
                    resultSet.getDate("att.recordDate"), resultSet.getString("att.path"));

            Photo photo = new Photo(resultSet.getLong("po.id"), resultSet.getString("po.path"));

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
        connection.close();
        return contact;
    }

    @Override
    public void update(Contact entity) {
        
    }

    @Override
    public void delete(long id) {

    }


    @Override
    public void deleteAll() throws SQLException {
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
        Connection connection = Database.getConnection();
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
}
