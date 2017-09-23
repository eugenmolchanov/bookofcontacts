package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.entities.Attachment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public interface AttachmentDao extends GenericDao<Attachment> {

    /**
     * Method returns all attachments which the contact has.
     *
     * @param contactId  id of contact
     * @param connection connection to database
     * @return collection of attachments
     * @throws SQLException
     */
    Set<Attachment> getAllAttachmentsOfContact(long contactId, Connection connection) throws SQLException;
}
