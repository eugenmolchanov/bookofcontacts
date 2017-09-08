package com.itechart.javalab.firstproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
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

    @Override
    public boolean equals(Object object) {
        if (object instanceof Contact) {
            Contact contact = (Contact) object;
            return Objects.equals(this.firstName, contact.getFirstName()) && Objects.equals(this.lastName, contact.getLastName()) && Objects.equals(this.middleName,
                    contact.getMiddleName()) && Objects.equals(this.birthday, contact.getBirthday()) && Objects.equals(this.gender, contact.getGender()) &&
                    Objects.equals(this.nationality, contact.getNationality()) && Objects.equals(this.maritalStatus, contact.getMaritalStatus()) &&
                    Objects.equals(this.webSite, contact.getWebSite()) && Objects.equals(this.email, contact.getEmail()) && Objects.equals(this.employmentPlace,
                    contact.getEmploymentPlace());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + Objects.hashCode(this.firstName);
        result = result * 31 + Objects.hashCode(this.lastName);
        result = result * 31 + Objects.hashCode(this.middleName);
        result = result * 31 + Objects.hashCode(this.birthday);
        result = result * 31 + Objects.hashCode(this.gender);
        result = result * 31 + Objects.hashCode(this.nationality);
        result = result * 31 + Objects.hashCode(this.maritalStatus);
        result = result * 31 + Objects.hashCode(this.webSite);
        result = result * 31 + Objects.hashCode(this.email);
        result = result * 31 + Objects.hashCode(this.employmentPlace);
        return result;
    }
}
