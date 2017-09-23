package com.itechart.javalab.firstproject.services.impl;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 13.09.2017.
 */
public class AddressServiceImpl {

    private static volatile AddressServiceImpl instance;
    private AddressDao<Address> addressDao = AddressDaoImpl.getInstance();

    private AddressServiceImpl() {
    }

    public static AddressServiceImpl getInstance() {
        if (instance == null) {
            synchronized (AddressServiceImpl.class) {
                if (instance == null) {
                    instance = new AddressServiceImpl();
                }
            }
        }
        return instance;
    }

    protected long checkAddress(Address entity, Connection connection) throws SQLException {
        return addressDao.checkAddress(entity, connection);
    }

    protected long create(Address entity, Connection connection) throws SQLException {
        return addressDao.save(entity, connection);
    }

    protected void update(Address entity, Connection connection) throws SQLException {
        addressDao.update(entity, connection);
    }


}
