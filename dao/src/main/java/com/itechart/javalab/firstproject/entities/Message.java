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
@EqualsAndHashCode(exclude = {"id", "sendingDate"})
@AllArgsConstructor
public class Message {
    private long id;
    private Set<Contact> addressees;
    private String topic;
    private String text;
    private Timestamp sendingDate;

    public Message() {
        this.addressees = new HashSet<>();
    }
}
