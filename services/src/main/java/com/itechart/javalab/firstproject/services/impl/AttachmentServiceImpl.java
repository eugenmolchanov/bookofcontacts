package com.itechart.javalab.firstproject.services.impl;

import com.itechart.javalab.firstproject.dao.AttachmentDao;
import com.itechart.javalab.firstproject.dao.impl.AttachmentDaoImpl;
import com.itechart.javalab.firstproject.entities.Attachment;
import com.itechart.javalab.firstproject.services.AttachmentService;

import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public class AttachmentServiceImpl implements AttachmentService<Attachment> {

    private static AttachmentServiceImpl INSTANCE;
    private AttachmentDao<Attachment> attachmentDao;

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
        attachmentDao = AttachmentDaoImpl.getInstance();
        attachmentDao.delete(id);
    }
}
