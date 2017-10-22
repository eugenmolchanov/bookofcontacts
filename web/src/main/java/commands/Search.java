package commands;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import util.EquivalentsForSelects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 16.09.2017.
 */
public class Search implements ActionCommand {

    private static Logger logger = Logger.getLogger(Search.class);
    private ContactService service = ContactServiceImpl.getInstance();
    private final String ACTIVE_PAGE = ConfigurationManager.getProperty("main");
    private final String SEARCH_PAGE = ConfigurationManager.getProperty("search");
    private final String ERROR_PAGE = ConfigurationManager.getProperty("error");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.setLevel(Level.DEBUG);
        try {
            Contact contact = new Contact();
            Set<Contact> contacts;
            long numberOfContacts;
            long startContact = Long.valueOf(String.valueOf(req.getSession().getAttribute("startContactNumber")));
            if (req.getParameter("startContactNumber") != null && !req.getParameter("startContactNumber").equals("")) {
                startContact = Long.parseLong(req.getParameter("startContactNumber"));
            }
            long step = Long.valueOf(String.valueOf(req.getSession().getAttribute("quantityOfContacts")));
            if (req.getParameter("quantityOfContacts") != null && Objects.equals(req.getParameter("quantityOfContacts"), "")) {
                step = Long.parseLong(req.getParameter("quantityOfContacts"));
            }
            String firstName = req.getParameter("firstName");
            if (!Objects.equals(firstName, "")) {
                contact.setFirstName(firstName);
            }
            String lastName = req.getParameter("lastName");
            if (!Objects.equals(lastName, "")) {
                contact.setLastName(lastName);
            }
            String middleName = req.getParameter("middleName");
            if (!Objects.equals(middleName, "")) {
                contact.setMiddleName(middleName);
            }
            Date birthdayFrom = null;
            try {
                birthdayFrom = Date.valueOf(req.getParameter("birthdayFrom"));
            } catch (Exception e) {
                logger.debug("Birth date from is invalid or absent.");
            }
            Date birthdayTo = null;
            try {
                birthdayTo = Date.valueOf(req.getParameter("birthdayTo"));
            } catch (Exception e) {
                logger.debug("Birth date up to is invalid or absent.");
            }
            String gender = req.getParameter("gender");
            if (!Objects.equals(gender, "")) {
                contact.setGender(gender);
            }
            String nationality = req.getParameter("nationality");
            if (!Objects.equals(nationality, "")) {
                contact.setNationality(nationality);
            }
            String maritalStatus = req.getParameter("maritalStatus");
            if (!Objects.equals(maritalStatus, "")) {
                contact.setMaritalStatus(maritalStatus);
            }
            String contactGroup = req.getParameter("contactGroup");
            if (!Objects.equals(contactGroup, "")) {
                contact.setContactGroup(contactGroup);
            }
            String country = req.getParameter("country");
            if (!Objects.equals(country, "")) {
                contact.setCountry(country);
            }
            String city = req.getParameter("city");
            if (!Objects.equals(city, "")) {
                contact.setCity(city);
            }
            String street = req.getParameter("street");
            if (!Objects.equals(street, "")) {
                contact.setStreet(street);
            }
            String houseNumber = req.getParameter("houseNumber");
            if (!Objects.equals(houseNumber, "")) {
                contact.setHouseNumber(houseNumber);
            }
            int flatNumber = 0;
            try {
                flatNumber = Integer.parseInt(req.getParameter("flatNumber"));
            } catch (Exception e) {
                logger.debug("Flat number is invalid or absent.");
            }
            contact.setFlatNumber(flatNumber);
            int postalIndex = 0;
            try {
                postalIndex = Integer.parseInt(req.getParameter("postalIndex"));
            } catch (Exception e) {
                logger.debug("Postcode is invalid or absent.");
            }
            contact.setPostcode(postalIndex);
            contacts = service.searchContacts(contact, birthdayFrom, birthdayTo, startContact, step);
            EquivalentsForSelects.fill(contacts);
            numberOfContacts = service.getNumberOfSearchContacts(contact, birthdayFrom, birthdayTo);
            req.setAttribute("startContactNumber", startContact);
            req.setAttribute("quantityOfContacts", step);
            req.setAttribute("command", "search");
            req.setAttribute("contacts", contacts);
            req.setAttribute("numberOfContacts", numberOfContacts);
            return ACTIVE_PAGE;
        } catch (SQLException e) {
            req.setAttribute("warningMessage", "error");
            return SEARCH_PAGE;
        } catch (Exception e) {
            req.setAttribute("warningMessage", "error");
            return ERROR_PAGE;
        }
    }
}
