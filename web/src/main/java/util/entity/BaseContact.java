package util.entity;


import lombok.Data;

/**
 * Created by Yauhen Malchanau on 09.10.2017.
 */
@Data
public class BaseContact {
    private String firstName = "\"Contact name\"";
    private final String lastName = "\"Contact surname\"";
    private final String middleName = "\"Contact middle name\"";
    private final String birthday = "\"Contact birthday\"";
    private final String gender = "\"Contact gender\"";
    private final String nationality = "\"Contact nationality\"";
    private final String maritalStatus = "\"Contact marital status\"";
    private final String webSite = "\"Contact website\"";
    private final String email = "\"Contact email\"";
    private final String employmentPlace = "\"Contact employment place\"";
    private final String contactGroup = "\"Contact contact group\"";
    private final String country = "\"Contact country\"";
    private final String city = "\"Contact city\"";
    private final String street = "\"Contact street\"";
    private final String houseNumber = "\"Contact house number\"";
    private final String flatNumber = "\"Contact flat number\"";
    private final String postcode = "\"Contact postcode\"";
}
