package com.itechart.javalab.firstproject.dao.util;

import com.itechart.javalab.firstproject.entities.Attachment;
import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.entities.Phone;
import com.itechart.javalab.firstproject.entities.Photo;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 08.03.2018.
 */
public class EntityBuilder {

    private EntityBuilder() {}

    public static Phone createAndInitializePhone(ResultSet resultSet) throws SQLException {
        Phone phone = new Phone();
        phone.setId(resultSet.getLong("pe.id"));
        phone.setCountryCode(resultSet.getString("pe.country_code"));
        phone.setOperatorCode(resultSet.getInt("pe.operator_code"));
        phone.setNumber(resultSet.getLong("pe.phone_number"));
        phone.setType(resultSet.getString("pe.phone_type"));
        phone.setComment(resultSet.getString("pe.commentary"));
        phone.setContactId(resultSet.getLong("pe.contact_id"));
        return phone;
    }

    public static Attachment createAndInitializeAttachment(ResultSet resultSet) throws SQLException {
        Attachment attachment = new Attachment();
        attachment.setId(resultSet.getLong("att.id"));
        attachment.setFileName(resultSet.getString("att.file_name"));
        attachment.setCommentary(resultSet.getString("att.commentary"));
        attachment.setDate(resultSet.getTimestamp("att.record_date"));
        attachment.setPathToFile(resultSet.getString("att.path"));
        attachment.setUuid(resultSet.getString("att.uuid"));
        attachment.setContactId(resultSet.getLong("c.id"));
        return attachment;
    }

    public static Photo createAndInitializePhoto(ResultSet resultSet) throws SQLException {
        Photo photo = new Photo();
        photo.setId(resultSet.getLong("po.id"));
        photo.setPathToFile(resultSet.getString("po.path"));
        photo.setUuid(resultSet.getString("po.uuid"));
        return photo;
    }

    public static Contact createAndInitializeContactForMainPage(ResultSet resultSet) throws SQLException {
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
        return contact;
    }
}
