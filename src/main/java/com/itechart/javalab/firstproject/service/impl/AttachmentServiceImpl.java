package com.itechart.javalab.firstproject.service.impl;

import com.itechart.javalab.firstproject.dao.AttachmentDao;
import com.itechart.javalab.firstproject.dao.impl.AttachmentDaoImpl;
import com.itechart.javalab.firstproject.entities.Attachment;
import com.itechart.javalab.firstproject.service.AttachmentService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import static com.itechart.javalab.firstproject.service.database.Database.getConnection;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public class AttachmentServiceImpl implements AttachmentService {

    private static Logger logger = Logger.getLogger(AttachmentServiceImpl.class);
    private static AttachmentServiceImpl instance;
    private AttachmentDao attachmentDao = AttachmentDaoImpl.getInstance();
    private static final Object lock = new Object();

    private AttachmentServiceImpl() {
    }

    public static AttachmentServiceImpl getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new AttachmentServiceImpl();
            }
        }
        return instance;
    }

    @Override
    public void delete(long id) throws SQLException {
        try (Connection connection = getConnection()) {
            attachmentDao.delete(id, connection);
        } catch (SQLException e) {
            logger.error("Can't delete attachment by id.");
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Attachment findById(long id) throws SQLException {
        try (Connection connection = getConnection()) {
            return attachmentDao.findById(id, connection);
        } catch (SQLException e) {
            logger.error("Can't find attachment by id.");
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    long create(Attachment entity, Connection connection) throws SQLException {
        return attachmentDao.save(entity, connection);
    }

    protected Set<Attachment> getAllAttachmentsOfContact(long contactId, Connection connection) throws SQLException {
        return attachmentDao.getAllAttachmentsOfContact(contactId, connection);
    }

    protected void update(Attachment entity, Connection connection) throws SQLException {
        attachmentDao.update(entity, connection);
    }
}
