package com.itechart.javalab.firstproject.dao.impl;

import com.itechart.javalab.firstproject.dao.AttachmentDao;
import com.itechart.javalab.firstproject.dao.util.Database;
import com.itechart.javalab.firstproject.entities.Attachment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Евгений Молчанов on 08.09.2017.
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
    public void delete(long id) throws SQLException {
        String deleteAttachment = "delete from attachment where id = ?;";
        Connection connection = Database.getConnection();
        PreparedStatement statement = connection.prepareStatement(deleteAttachment);
        statement.setLong(1, id);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }
}
