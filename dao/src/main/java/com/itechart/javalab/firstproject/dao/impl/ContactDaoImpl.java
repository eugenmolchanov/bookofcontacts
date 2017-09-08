package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.ContactDao;
import com.itechart.javalab.firstproject.dao.database.Database;
import com.itechart.javalab.firstproject.entities.*;

import java.sql.*;
import java.util.*;

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
                Database.saveAddress(connection, saveAddress, saveContactAddress, entity);
            }

            Database.saveAttachment(connection, saveAttachment, entity);

            Database.savePhone(connection, savePhone, entity);

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
    public void update(Contact entity) throws SQLException {
        String updateContact = "update contact set firstName = ?, lastName = ?, middleName = ?, birthday = ?, gender = ?, nationality = ?, maritalStatus = ?, webSite = ?, " +
                "email = ?, employmentPlace = ? where id = ?;";
        String saveAddress = "insert into address (city, street, houseNumber, flatNumber, postalIndex) values (?, ?, ?, ?, ?);";
        String saveContactAddress = "insert into contact_address values (?, ?);";
        String updateAddress = "update address set city = ?, street = ?, houseNumber = ?, flatNumber = ?, postalIndex = ? where id = ?;";
        String getAttachments = "select * from attachment where contact_id = ?";
        String getPhones = "select * from phone where contact_id = ?;";
        String saveAttachment = "insert into attachment (fileName, commentary, recordDate, path, contact_id) values (?, ?, ?, ?, ?);";
        String savePhone = "insert into phone (countryCode, operatorCode, phoneNumber, phoneType, commentary, contact_id) values (?, ?, ?, ?, ?, ?);";
        String savePhoto = "insert into photo (path, contact_id) values (?, ?);";
        String updatePhoto = "update photo set path = ? where id = ?;";
        String updateAttachment = "update attachment set fileName = ?, commentary = ?, recordDate = ?, path = ? where id = ?;";
        String updatePhone = "update phone set countryCode = ?, operatorCode = ?, phoneNumber = ?, phoneType = ?, commentary = ? where id = ?;";
        Connection connection = Database.getConnection();
        PreparedStatement statement = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(updateContact);
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

            if (entity.getAddress().getId() != 0) {
                statement = connection.prepareStatement(updateAddress);
                statement.setString(1, entity.getAddress().getCity());
                statement.setString(2, entity.getAddress().getStreet());
                statement.setInt(3, entity.getAddress().getHouseNumber());
                statement.setInt(4, entity.getAddress().getFlatNumber());
                statement.setInt(5, entity.getAddress().getPostalIndex());
                statement.setLong(6, entity.getAddress().getId());
                statement.executeUpdate();
            } else {
                Database.saveAddress(connection, saveAddress, saveContactAddress, entity);
            }

            statement = connection.prepareStatement(getAttachments);
            statement.setLong(1, entity.getId());
            ResultSet resultSet = statement.executeQuery();
            Attachment attachment;
            Contact databaseContact = new Contact();
            while (resultSet.next()) {
                attachment = new Attachment(resultSet.getLong("id"), resultSet.getString("fileName"), resultSet.getString("commentary"), resultSet.getDate("recordDate"),
                        resultSet.getString("path"));
                databaseContact.getAttachments().add(attachment);
            }
            if (databaseContact.getAttachments().size() != 0) {
                Iterator<Attachment> entityIterator = entity.getAttachments().iterator();
                while (entityIterator.hasNext()) {
                    Attachment potentialNewcomer = entityIterator.next();
                    Iterator<Attachment> databaseContactIterator = databaseContact.getAttachments().iterator();
                    while (databaseContactIterator.hasNext()) {
                        Attachment databaseAttachment = databaseContactIterator.next();
                        if (databaseAttachment.getId() == potentialNewcomer.getId()) {
                            statement = connection.prepareStatement(updateAttachment);
                            statement.setString(1, potentialNewcomer.getFileName());
                            statement.setString(2, potentialNewcomer.getCommentary());
                            statement.setDate(3, potentialNewcomer.getDate());
                            statement.setString(4, potentialNewcomer.getPathToFile());
                            statement.setLong(5, potentialNewcomer.getId());
                            statement.executeUpdate();
                            entityIterator.remove();
                        }
                    }
                }
                Database.saveAttachment(connection, saveAttachment, entity);
            } else if (entity.getAttachments().size() != 0) {
                Database.saveAttachment(connection, saveAttachment, entity);
            }

            statement = connection.prepareStatement(getPhones);
            statement.setLong(1, entity.getId());
            resultSet = statement.executeQuery();
            Phone phone;
            while (resultSet.next()) {
                phone = new Phone(resultSet.getLong("id"), resultSet.getInt("countryCode"), resultSet.getInt("operatorCode"), resultSet.getLong("phoneNumber"),
                        resultSet.getString("phoneType"), resultSet.getString("commentary"));
                databaseContact.getPhones().add(phone);
            }
            if (databaseContact.getPhones().size() != 0) {
                Iterator<Phone> entityIterator = entity.getPhones().iterator();
                while (entityIterator.hasNext()) {
                    Phone potentialNewcomer = entityIterator.next();
                    Iterator<Phone> databaseContactIterator = databaseContact.getPhones().iterator();
                    while (databaseContactIterator.hasNext()) {
                        Phone databasePhone = databaseContactIterator.next();
                        if (databasePhone.getId() == potentialNewcomer.getId()) {
                            statement = connection.prepareStatement(updatePhone);
                            statement.setInt(1, potentialNewcomer.getCountryCode());
                            statement.setInt(2, potentialNewcomer.getOperatorCode());
                            statement.setLong(3, potentialNewcomer.getNumber());
                            statement.setString(4, potentialNewcomer.getType());
                            statement.setString(5, potentialNewcomer.getComment());
                            statement.setLong(6, potentialNewcomer.getId());
                            statement.executeUpdate();
                            entityIterator.remove();
                        }
                    }
                }
                Database.savePhone(connection, savePhone, entity);
            } else if (entity.getPhones().size() != 0) {
                Database.savePhone(connection, savePhone, entity);
            }

            if (entity.getPhoto().getId() != 0) {
                statement = connection.prepareStatement(updatePhoto);
                statement.setString(1, entity.getPhoto().getPathToFile());
                statement.setLong(2, entity.getPhoto().getId());
                statement.executeUpdate();
            } else {
                statement = connection.prepareStatement(savePhoto);
                statement.setString(1, entity.getPhoto().getPathToFile());
                statement.setLong(2, entity.getId());
                statement.executeUpdate();
            }
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
    public void delete(long id) throws SQLException {
        String deleteContactAddress = "delete from contact_address where contact_id = ?;";
        String deleteAttachment = "delete from attachment where contact_id = ?;";
        String deletePhone = "delete from phone where contact_id = ?;";
        String deletePhoto = "delete from photo where contact_id = ?;";
        String deleteContactMessage = "delete from contact_message where contact_id = ?;";
        String deleteContact = "delete from contact where id = ?;";

        Connection connection = Database.getConnection();
        PreparedStatement statement = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(deleteContactAddress);
            statement.setLong(1, id);
            statement.executeUpdate();

            statement = connection.prepareStatement(deleteContactMessage);
            statement.setLong(1, id);
            statement.executeUpdate();

            statement = connection.prepareStatement(deleteAttachment);
            statement.setLong(1, id);
            statement.executeUpdate();

            statement = connection.prepareStatement(deletePhone);
            statement.setLong(1, id);
            statement.executeUpdate();

            statement = connection.prepareStatement(deletePhoto);
            statement.setLong(1, id);
            statement.executeUpdate();

            statement = connection.prepareStatement(deleteContact);
            statement.setLong(1, id);
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

    @Override
    public Set<Contact> getSetOfContacts(long startContactNumber, long quantityOfContacts) throws SQLException {
        String getContacts = "select c.id, c.firstName, c.lastName, c.birthday, c.employmentPlace, a.city, a.street, a.houseNumber, a.flatNumber from contact as c left join " +
                "contact_address as ca on c.id = ca.contact_id left join address as a on ca.address_id = a.id limit ?, ?;";
        Connection connection = Database.getConnection();
        PreparedStatement statement = connection.prepareStatement(getContacts);
        statement.setLong(1, startContactNumber);
        statement.setLong(2, quantityOfContacts);
        ResultSet resultSet = statement.executeQuery();
        TreeSet<Contact> contacts = new TreeSet<>((contact, nextContact) -> {
            if (contact.getId() > nextContact.getId()) {
                return 1;
            } else if (contact.getId() == nextContact.getId()) {
                return 0;
            } else {
                return -1;
            }
        });
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
        connection.close();
        return contacts;
    }

    @Override
    public Set<Contact> searchContacts(Contact entity) {
        String getContacts = "select c.id, c.firstName, c.lastName, c.birthday, c.employmentPlace, a.city, a.street, a.houseNumber, a.flatNumber from contact as c left join " +
                "contact_address as ca on c.id = ca.contact_id left join address as a on ca.address_id = a.id where c.firstName = if(? is null, is not null, ?) and " +
                "c.lastName = if(? is null, is not null, ?) and c.middleName = if(? is null, is not null, ?) and c.gender = if(? is null, is not null, ?) and " +
                "c.maritalStatus = if(? is null, is not null, ?) and c.nationality = if(? is null, is not null, ?) and a.city = if(? is null, is not null, ?) and " +
                "a.street = if(? is null, is not null, ?) and a.houseNumber = if(? is null, is not null, ?) and a.flatNumber = if(? is null, is not null, ?) and " +
                "a.postalIndex = if(? is null, is not null, ?);";

    }
}
