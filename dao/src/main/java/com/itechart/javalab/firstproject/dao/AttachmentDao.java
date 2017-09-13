package com.itechart.javalab.firstproject.dao;

import com.itechart.javalab.firstproject.entities.Attachment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
public interface AttachmentDao<T> extends GenericDao<Attachment> {
    Set<T> getAllAttachmentsOfContact(long contactId, Connection connection) throws SQLException;
}
