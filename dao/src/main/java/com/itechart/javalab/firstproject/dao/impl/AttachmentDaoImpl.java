package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.AttachmentDao;
import com.itechart.javalab.firstproject.dao.util.Util;
import com.itechart.javalab.firstproject.entities.Attachment;
import com.itechart.javalab.firstproject.entities.Contact;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 08.09.2017.
 */
public class AttachmentDaoImpl implements AttachmentDao<Attachment> {

    private static AttachmentDaoImpl INSTANCE;

    private AttachmentDaoImpl() {
    }

    public static AttachmentDaoImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (AttachmentDaoImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AttachmentDaoImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public long save(Attachment entity, Connection connection) throws SQLException {
        final String SAVE_ATTACHMENT = "insert into attachment (fileName, commentary, recordDate, path, uuid) values (?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(SAVE_ATTACHMENT, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, entity.getFileName());
        statement.setString(2, entity.getCommentary());
        statement.setTimestamp(3, entity.getDate());
        statement.setString(4, entity.getPathToFile());
        statement.setString(5, entity.getUuid());
        statement.executeUpdate();
        long id = Util.getGeneratedIdAfterCreate(statement);
        statement.close();
        return id;
    }

    @Override
    public Attachment findById(long id, Connection connection) throws SQLException {
        return null;
    }

    @Override
    public void update(Attachment entity, Connection connection) throws SQLException {
        final String UPDATE_ATTACHMENT = "update attachment set fileName = ?, commentary = ?, recordDate = ?, path = ?, uuid = ? where id = ?;";
        PreparedStatement statement = connection.prepareStatement(UPDATE_ATTACHMENT);
        statement.setString(1, entity.getFileName());
        statement.setString(2, entity.getCommentary());
        statement.setTimestamp(3, entity.getDate());
        statement.setString(4, entity.getPathToFile());
        statement.setString(5, entity.getUuid());
        statement.setLong(6, entity.getId());
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void delete(long id, Connection connection) throws SQLException {
        final String DELETE_CONTACT_ATTACHMENT = "delete from contact_attachment where attachment_id = ?;";
        final String DELETE_ATTACHMENT = "delete from attachment where id = ?;";
        PreparedStatement statement = connection.prepareStatement(DELETE_CONTACT_ATTACHMENT);
        statement.setLong(1, id);
        statement.executeUpdate();

        statement = connection.prepareStatement(DELETE_ATTACHMENT);
        statement.setLong(1, id);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public Set<Attachment> getAllAttachmentsOfContact(Contact entity, Connection connection) throws SQLException {
        final String GET_ATTACHMENTS = "select att.id, att.fileName, att.commentary, att.recordDate, att.path, att.uuid from attachment as att left join contact_attachment " +
                "as ca on att.id=ca.attachment_id where ca.contact_id = ?";
        PreparedStatement statement = connection.prepareStatement(GET_ATTACHMENTS);
        statement.setLong(1, entity.getId());
        ResultSet resultSet = statement.executeQuery();
        Attachment attachment;
        Set<Attachment> attachments = new HashSet<>();
        while (resultSet.next()) {
            attachment = new Attachment(resultSet.getLong("att.id"), resultSet.getString("att.fileName"), resultSet.getString("att.commentary"),
                    resultSet.getTimestamp("att.recordDate"), resultSet.getString("att.path"), resultSet.getString("att.uuid"));
            attachments.add(attachment);
        }
        statement.close();
        return attachments;
    }
}
