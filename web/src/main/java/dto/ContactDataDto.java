package dto;

import lombok.Data;

import java.sql.Date;

/**
 * Created by Yauhen Malchanau on 25.10.2017.
 */
@Data
public class ContactDataDto {
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
    private String flatNumber;
    private String postcode;
}
