package com.itechart.javalab.firstproject.services;

import com.itechart.javalab.firstproject.entities.Photo;

import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public interface PhotoService extends GenericService<Photo> {
    /**
     * Method finds photo object by id.
     *
     * @param id id of photo
     * @return photo object
     * @throws SQLException
     */
    Photo findById(long id) throws SQLException;
}
