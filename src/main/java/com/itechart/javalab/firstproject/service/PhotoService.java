package com.itechart.javalab.firstproject.service;

import com.itechart.javalab.firstproject.entities.Photo;

import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public interface PhotoService extends GenericService<Photo> {
    Photo findById(long id) throws SQLException;
}
