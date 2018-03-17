package com.itechart.javalab.firstproject.dao.util;

/**
 * Created by Yauhen Malchanau on 13.03.2018.
 */
public class FieldNameConstant {

    private FieldNameConstant() {

    }

    //Contact
    public static final String CONTACT_ID = "c.id";
    public static final String FIRST_NAME = "c.first_name";
    public static final String LAST_NAME = "c.last_name";
    public static final String EMAIL = "c.email";
    public static final String MIDDLE_NAME = "c.middle_name";
    public static final String BIRTH_DATE = "c.birth_date";
    public static final String GENDER = "c.gender";
    public static final String NATIONALITY = "c.nationality";
    public static final String MARITAL_STATUS = "c.marital_status";
    public static final String WEBSITE = "c.website";
    public static final String EMPLOYMENT_PLACE = "c.employment_place";
    public static final String CONTACT_GROUP = "c.contact_group";
    public static final String COUNTRY = "c.country";
    public static final String CITY = "c.city";
    public static final String STREET = "c.street";
    public static final String HOUSE_NUMBER = "c.house_number";
    public static final String FLAT_NUMBER = "c.flat_number";
    public static final String POSTCODE = "c.postcode";

    //Phone
    public static final String PHONE_ID = "pe.id";
    public static final String COUNTRY_CODE = "pe.country_code";
    public static final String OPERATOR_CODE = "pe.operator_code";
    public static final String PHONE_NUMBER = "pe.phone_number";
    public static final String PHONE_TYPE = "pe.phone_type";
    public static final String PHONE_COMMENTARY = "pe.commentary";
    public static final String PHONE_CONTACT_ID = "pe.contact_id";

    //Attachment
    public static final String ATTACHMENT_ID = "att.id";
    public static final String FILE_NAME = "att.file_name";
    public static final String ATTACHMENT_COMMENTARY = "att.commentary";
    public static final String RECORD_DATE = "att.record_date";
    public static final String ATTACHMENT_PATH = "att.path";
    public static final String ATTACHMENT_UUID = "att.uuid";
    public static final String ATTACHMENT_CONTACT_ID = "att.contact_id";

    //Photo
    public static final String PHOTO_ID = "po.id";
    public static final String PHOTO_PATH = "po.path";
    public static final String PHOTO_UUID = "po.uuid";

    //Message
    public static final String MESSAGE_ID = "m.id";
    public static final String TOPIC = "m.topic";
    public static final String MESSAGE = "m.message";
    public static final String SENDING_DATE = "m.sending_date";
}
