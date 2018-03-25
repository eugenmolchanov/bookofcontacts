package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.AttachmentDao;
import com.itechart.javalab.firstproject.entities.Attachment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static com.itechart.javalab.firstproject.dao.util.DatabaseOperation.getGeneratedId;
import static com.itechart.javalab.firstproject.dao.util.EntityCreator.createAndInitializeAttachment;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Created by Yauhen Malchanau on 08.09.2017.
 */
public class AttachmentDaoImpl implements AttachmentDao {

    private AttachmentDaoImpl() {}

    private static AttachmentDaoImpl instance;

    private static final String SAVE_ATTACHMENT = "insert into attachment (file_name, commentary, record_date, path, " +
            "uuid, contact_id) values (?, ?, ?, ?, ?, ?);";
    private static final String GET_ATTACHMENT_BY_ID = "select * from attachment as att where att.id = ?;";
    private static final String UPDATE_ATTACHMENT = "update attachment set file_name = ?, commentary = ?, " +
            "record_date = record_date where id = ?;";
    private static final String DELETE_ATTACHMENT = "delete from attachment where id = ?;";
    private static final String GET_ATTACHMENTS = "select * from attachment as att where att.contact_id = ?";

    public static AttachmentDaoImpl getInstance() {
        if (instance == null) {
            instance = new AttachmentDaoImpl();
        }
        return instance;
    }

    @Override
    public long save(Attachment entity, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_ATTACHMENT, RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getFileName());
            statement.setString(2, entity.getCommentary());
            statement.setTimestamp(3, entity.getDate());
            statement.setString(4, entity.getPathToFile());
            statement.setString(5, entity.getUuid());
            statement.setLong(6, entity.getContactId());
            statement.executeUpdate();
            return getGeneratedId(statement);
        }
    }

    @Override
    public Attachment findById(long id, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_ATTACHMENT_BY_ID)) {
            statement.setLong(1, id);
            Attachment attachment = null;
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    attachment = createAndInitializeAttachment(resultSet);
                }
            }
            return attachment;
        }
    }

    @Override
    public void update(Attachment entity, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ATTACHMENT)) {
            statement.setString(1, entity.getFileName());
            statement.setString(2, entity.getCommentary());
            statement.setLong(3, entity.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(long id, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ATTACHMENT)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Set<Attachment> getAllAttachmentsOfContact(long contactId, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_ATTACHMENTS)) {
            statement.setLong(1, contactId);
            try (ResultSet resultSet = statement.executeQuery()) {
                Attachment attachment;
                Set<Attachment> attachments = new HashSet<>();
                while (resultSet.next()) {
                    attachment = createAndInitializeAttachment(resultSet);
                    attachments.add(attachment);
                }
                return attachments;
            }
        }
    }
}
