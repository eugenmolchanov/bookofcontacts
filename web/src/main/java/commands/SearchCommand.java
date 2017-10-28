package commands;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;
import util.EquivalentForSelect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 16.09.2017.
 */
public class SearchCommand implements ActionCommand {

    private static Logger logger = Logger.getLogger(SearchCommand.class);
    private ContactService service = ContactServiceImpl.getInstance();
    private final String ACTIVE_PAGE = ConfigurationManager.getProperty("main");
    private final String SEARCH_PAGE = ConfigurationManager.getProperty("search");
    private final String ERROR_PAGE = ConfigurationManager.getProperty("error");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.setLevel(Level.DEBUG);
        try {
            Contact contact = new Contact();
            StringBuilder parameters = new StringBuilder("&");
            Set<Contact> contacts;
            long numberOfContacts;
            long startContact = Long.valueOf(String.valueOf(req.getSession().getAttribute("startContactNumber")));
            if (req.getParameter("startContactNumber") != null && !Objects.equals(req.getParameter("startContactNumber"), "")) {
                startContact = Long.parseLong(req.getParameter("startContactNumber"));
            }
            long step = Long.valueOf(String.valueOf(req.getSession().getAttribute("quantityOfContacts")));
            if (req.getParameter("quantityOfContacts") != null && !Objects.equals(req.getParameter("quantityOfContacts"), "")) {
                step = Long.parseLong(req.getParameter("quantityOfContacts"));
            }
            String firstName = req.getParameter("firstName");
            if (firstName != null && !Objects.equals(firstName, "")) {
                contact.setFirstName(firstName);
                parameters.append("firstName=").append(firstName).append("&");
            }
            String lastName = req.getParameter("lastName");
            if (lastName != null && !Objects.equals(lastName, "")) {
                contact.setLastName(lastName);
                parameters.append("lastName=").append(lastName).append("&");
            }
            String middleName = req.getParameter("middleName");
            if (middleName != null && !Objects.equals(middleName, "")) {
                contact.setMiddleName(middleName);
                parameters.append("middleName=").append(middleName).append("&");
            }
            Date birthdayFrom = null;
            try {
                birthdayFrom = Date.valueOf(req.getParameter("birthdayFrom"));
                parameters.append("birthdayFrom=").append(birthdayFrom).append("&");
            } catch (Exception e) {
                logger.debug("Birth date from is invalid or absent.");
            }
            Date birthdayTo = null;
            try {
                birthdayTo = Date.valueOf(req.getParameter("birthdayTo"));
                parameters.append("birthdayTo=").append(birthdayTo).append("&");
            } catch (Exception e) {
                logger.debug("Birth date up to is invalid or absent.");
            }
            String gender = req.getParameter("gender");
            if (gender != null && !Objects.equals(gender, "")) {
                contact.setGender(gender);
                parameters.append("gender=").append(gender).append("&");
            }
            String nationality = req.getParameter("nationality");
            if (nationality != null && !Objects.equals(nationality, "")) {
                contact.setNationality(nationality);
                parameters.append("nationality=").append(nationality).append("&");
            }
            String maritalStatus = req.getParameter("maritalStatus");
            if (maritalStatus != null && !Objects.equals(maritalStatus, "")) {
                contact.setMaritalStatus(maritalStatus);
                parameters.append("maritalStatus=").append(maritalStatus).append("&");
            }
            String contactGroup = req.getParameter("contactGroup");
            if (contactGroup != null && !Objects.equals(contactGroup, "")) {
                contact.setContactGroup(contactGroup);
                parameters.append("contactGroup=").append(contactGroup).append("&");
            }
            String country = req.getParameter("country");
            if (country != null && !Objects.equals(country, "")) {
                contact.setCountry(country);
                parameters.append("country=").append(country).append("&");
            }
            String city = req.getParameter("city");
            if (city != null && !Objects.equals(city, "")) {
                contact.setCity(city);
                parameters.append("city=").append(city).append("&");
            }
            String street = req.getParameter("street");
            if (street != null && !Objects.equals(street, "")) {
                contact.setStreet(street);
                parameters.append("street=").append(street).append("&");
            }
            String houseNumber = req.getParameter("houseNumber");
            if (houseNumber != null && !Objects.equals(houseNumber, "")) {
                contact.setHouseNumber(houseNumber);
                parameters.append("houseNumber=").append(houseNumber).append("&");
            }
            int flatNumber = 0;
            try {
                flatNumber = Integer.parseInt(req.getParameter("flatNumber"));
                parameters.append("flatNumber=").append(flatNumber).append("&");
            } catch (Exception e) {
                logger.debug("Flat number is invalid or absent.");
            }
            contact.setFlatNumber(flatNumber);
            int postalIndex = 0;
            try {
                postalIndex = Integer.parseInt(req.getParameter("postalIndex"));
                parameters.append("postalIndex=").append(postalIndex).append("&");
            } catch (Exception e) {
                logger.debug("Postcode is invalid or absent.");
            }
            contact.setPostcode(postalIndex);
            parameters.deleteCharAt(parameters.length() - 1);
            contacts = service.searchContacts(contact, birthdayFrom, birthdayTo, startContact, step);
            EquivalentForSelect.fill(contacts);
            numberOfContacts = service.getNumberOfSearchContacts(contact, birthdayFrom, birthdayTo);
            req.setAttribute("startContactNumber", startContact);
            req.setAttribute("quantityOfContacts", step);
            req.setAttribute("command", "search");
            req.setAttribute("contacts", contacts);
            req.setAttribute("parameters", parameters);
            req.setAttribute("numberOfContacts", numberOfContacts);
            return ACTIVE_PAGE;
        } catch (SQLException e) {
            req.setAttribute("warningMessage", MessageManager.getProperty("error"));
            return SEARCH_PAGE;
        } catch (Exception e) {
            req.setAttribute("warningMessage", MessageManager.getProperty("error"));
            return ERROR_PAGE;
        }
    }
}
