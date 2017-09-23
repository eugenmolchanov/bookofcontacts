package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.AttachmentDao;
import com.itechart.javalab.firstproject.dao.util.Util;
import com.itechart.javalab.firstproject.entities.Attachment;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 08.09.2017.
 */
public class AttachmentDaoImpl implements AttachmentDao {

    private static AttachmentDaoImpl instance;

    private AttachmentDaoImpl() {
    }

    public static AttachmentDaoImpl getInstance() {
        if (instance == null) {
            instance = new AttachmentDaoImpl();
        }
        return instance;
    }

    @Override
    public long save(Attachment entity, Connection connection) throws SQLException {
        final String SAVE_ATTACHMENT = "insert into attachment (file_name, commentary, record_date, path, uuid, contact_id) values (?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(SAVE_ATTACHMENT, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, entity.getFileName());
        statement.setString(2, entity.getCommentary());
        statement.setTimestamp(3, entity.getDate());
        statement.setString(4, entity.getPathToFile());
        statement.setString(5, entity.getUuid());
        statement.setLong(6, entity.getContactId());
        statement.executeUpdate();
        long id = Util.getGeneratedIdAfterCreate(statement);
        statement.close();
        return id;
    }

    @Override
    public Attachment findById(long id, Connection connection) throws SQLException {
        final String GET_ATTACHMENT_BY_ID = "select * from attachment where id = ?;";
        PreparedStatement statement = connection.prepareStatement(GET_ATTACHMENT_BY_ID);
        statement.setLong(1, id);
        Attachment attachment = null;
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            attachment = new Attachment(resultSet.getLong("id"), resultSet.getString("file_name"), resultSet.getString("commentary"), resultSet.getTimestamp("record_date"),
                    resultSet.getString("path"), resultSet.getString("uuid"), resultSet.getLong("contact_id"));
        }
        statement.close();
        return attachment;
    }

    @Override
    public void update(Attachment entity, Connection connection) throws SQLException {
        final String UPDATE_ATTACHMENT = "update attachment set file_name = ?, commentary = ?, record_date = ?, path = ?, uuid = ? where id = ?;";
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
        final String DELETE_ATTACHMENT = "delete from attachment where id = ?;";
        PreparedStatement statement = connection.prepareStatement(DELETE_ATTACHMENT);
        statement.setLong(1, id);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public Set<Attachment> getAllAttachmentsOfContact(long contactId, Connection connection) throws SQLException {
        final String GET_ATTACHMENTS = "select id, file_name, commentary, record_date, path, uuid from attachment where contact_id = ?";
        PreparedStatement statement = connection.prepareStatement(GET_ATTACHMENTS);
        statement.setLong(1, contactId);
        ResultSet resultSet = statement.executeQuery();
        Attachment attachment;
        Set<Attachment> attachments = new HashSet<>();
        while (resultSet.next()) {
            attachment = new Attachment(resultSet.getLong("id"), resultSet.getString("file_name"), resultSet.getString("commentary"),
                    resultSet.getTimestamp("record_date"), resultSet.getString("path"), resultSet.getString("uuid"), contactId);
            attachments.add(attachment);
        }
        statement.close();
        return attachments;
    }
}
