package com.itechart.javalab.firstproject.services.impl;

import com.itechart.javalab.firstproject.dao.AttachmentDao;
import com.itechart.javalab.firstproject.dao.impl.AttachmentDaoImpl;
import com.itechart.javalab.firstproject.entities.Attachment;
import com.itechart.javalab.firstproject.services.AttachmentService;
import com.itechart.javalab.firstproject.services.database.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public class AttachmentServiceImpl implements AttachmentService<Attachment> {

    private static AttachmentServiceImpl INSTANCE;
    private AttachmentDao<Attachment> attachmentDao = AttachmentDaoImpl.getInstance();

    private AttachmentServiceImpl() {
    }

    public static AttachmentServiceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (AttachmentServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AttachmentServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void delete(long id) throws SQLException {
        Connection connection = Database.getConnection();
        attachmentDao.delete(id, connection);
        connection.close();
    }

    protected long create(Attachment entity, Connection connection) throws SQLException {
        return attachmentDao.save(entity, connection);
    }

    protected Set<Attachment> getAllAttachmentsOfContact(long contactId, Connection connection) throws SQLException {
        return attachmentDao.getAllAttachmentsOfContact(contactId, connection);
    }

    protected void update(Attachment entity, Connection connection) throws SQLException {
        attachmentDao.update(entity, connection);
    }
}
