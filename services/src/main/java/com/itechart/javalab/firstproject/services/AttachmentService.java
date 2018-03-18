package com.itechart.javalab.firstproject.services;

import com.itechart.javalab.firstproject.entities.Attachment;

import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public interface AttachmentService extends GenericService<Attachment> {
    Attachment findById(long id) throws SQLException;
}
