package com.itechart.javalab.firstproject.services.impl;

import com.itechart.javalab.firstproject.dao.PhotoDao;
import com.itechart.javalab.firstproject.dao.impl.PhotoDaoImpl;
import com.itechart.javalab.firstproject.entities.Photo;
import com.itechart.javalab.firstproject.services.PhotoService;

import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public class PhotoServiceImpl implements PhotoService<Photo> {

    private static PhotoServiceImpl INSTANCE;
    private PhotoDao<Photo> photoDao;

    private PhotoServiceImpl() {
    }

    public static PhotoService<Photo> getInstance() {
        if (INSTANCE == null) {
            synchronized (PhotoServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PhotoServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void delete(long id) throws SQLException {
        photoDao = PhotoDaoImpl.getInstance();
        photoDao.delete(id);
    }
}
