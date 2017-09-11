package com.itechart.javalab.firstproject.services.impl;

import com.itechart.javalab.firstproject.dao.PhoneDao;
import com.itechart.javalab.firstproject.dao.impl.PhoneDaoImpl;
import com.itechart.javalab.firstproject.entities.Phone;
import com.itechart.javalab.firstproject.services.PhoneService;

import java.sql.SQLException;

/**
 * Created by Евгений Молчанов on 11.09.2017.
 */
public class PhoneServiceImpl implements PhoneService<Phone> {

    private static PhoneServiceImpl INSTANCE;
    private PhoneDao<Phone> phoneDao;

    private PhoneServiceImpl() {
    }

    public static PhoneService<Phone> getInstance() {
        if (INSTANCE == null) {
            synchronized (PhoneServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PhoneServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void delete(long id) throws SQLException {
        phoneDao = PhoneDaoImpl.getInstance();
        phoneDao.delete(id);
    }
}
