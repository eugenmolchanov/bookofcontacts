package com.itechart.javalab.firstproject.dao;

import java.util.Set;

/**
 * Created by Евгений Молчанов on 06.09.2017.
 */
public interface MessageDao<T> extends GenericDao<MessageDao> {
    /**
     * Method returns all messages stored in util.
     * @return
     */
    Set<T> getAll();
}
