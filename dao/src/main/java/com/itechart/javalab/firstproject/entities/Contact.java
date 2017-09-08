package com.itechart.javalab.firstproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Евгений Молчанов on 06.09.2017.
 */
@Data
@AllArgsConstructor
public class Contact {
    private long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Date birthday;
    private String gender;
    private String nationality;
    private String maritalStatus;
    private String webSite;
    private String email;
    private String employmentPlace;
    private Address address;
    private Set<Phone> phones;
    private Set<Attachment> attachments;
    private Photo photo;

    public Contact() {
        this.address = new Address();
        this.attachments = new HashSet<>();
        this.phones = new HashSet<>();
        this.photo = new Photo();
    }
}
