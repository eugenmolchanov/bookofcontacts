package com.itechart.javalab.firstproject.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
@Data
@EqualsAndHashCode(exclude = "id")
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
    private String contactGroup;
    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private int flatNumber;
    private int postcode;
    private Set<Phone> phones;
    private Set<Attachment> attachments;
    private Photo photo;

    public Contact() {
        this.attachments = new HashSet<>();
        this.phones = new HashSet<>();
        this.photo = new Photo();
    }
}
