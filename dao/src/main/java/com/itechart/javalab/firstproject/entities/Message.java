package com.itechart.javalab.firstproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
@Data
@EqualsAndHashCode(exclude = {"id"})
@AllArgsConstructor
public class Message {
    private long id;
    private String topic;
    private Set<Contact> addressees;
    private String text;
    private Timestamp sendingDate;
    private int isDeleted;

    public Message() {
        this.addressees = new HashSet<>();
    }
}
