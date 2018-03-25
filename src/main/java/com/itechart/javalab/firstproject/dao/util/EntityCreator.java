package com.itechart.javalab.firstproject.dao.util;

import com.itechart.javalab.firstproject.entities.Attachment;
import com.itechart.javalab.firstproject.entities.Phone;
import com.itechart.javalab.firstproject.entities.Photo;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.itechart.javalab.firstproject.dao.util.FieldNameConstant.*;

/**
 * Created by Yauhen Malchanau on 08.03.2018.
 */
public class EntityCreator {

    private EntityCreator() {}

    public static Phone createAndInitializePhone(ResultSet resultSet) throws SQLException {
        Phone phone = new Phone();
        phone.setId(resultSet.getLong(PHONE_ID));
        phone.setCountryCode(resultSet.getString(COUNTRY_CODE));
        phone.setOperatorCode(resultSet.getInt(OPERATOR_CODE));
        phone.setNumber(resultSet.getLong(PHONE_NUMBER));
        phone.setType(resultSet.getString(PHONE_TYPE));
        phone.setComment(resultSet.getString(PHONE_COMMENTARY));
        phone.setContactId(resultSet.getLong(PHONE_CONTACT_ID));
        return phone;
    }

    public static Attachment createAndInitializeAttachment(ResultSet resultSet) throws SQLException {
        Attachment attachment = new Attachment();
        attachment.setId(resultSet.getLong(ATTACHMENT_ID));
        attachment.setFileName(resultSet.getString(FILE_NAME));
        attachment.setCommentary(resultSet.getString(ATTACHMENT_COMMENTARY));
        attachment.setDate(resultSet.getTimestamp(RECORD_DATE));
        attachment.setPathToFile(resultSet.getString(ATTACHMENT_PATH));
        attachment.setUuid(resultSet.getString(ATTACHMENT_UUID));
        attachment.setContactId(resultSet.getLong(ATTACHMENT_CONTACT_ID));
        return attachment;
    }

    public static Photo createAndInitializePhoto(ResultSet resultSet) throws SQLException {
        Photo photo = new Photo();
        photo.setId(resultSet.getLong(PHOTO_ID));
        photo.setPathToFile(resultSet.getString(PHOTO_PATH));
        photo.setUuid(resultSet.getString(PHOTO_UUID));
        return photo;
    }
}
