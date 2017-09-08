package com.itechart.javalab.firstproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Евгений Молчанов on 06.09.2017.
 */
@Data
@AllArgsConstructor
public class Message {
    private long id;
    private Set<Contact> addressees;
    private String topic;
    private String text;

    public Message() {
        this.addressees = new HashSet<>();
    }
}
