package commands;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;
import util.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 16.09.2017.
 */
public class Search implements ActionCommand {

    private static Logger logger = Logger.getLogger(Search.class);
    private ContactService service = ContactServiceImpl.getInstance();
    private Set<Contact> contacts;
    private long numberOfContacts;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (Validation.searchDataIsValid(req, logger)) {
            logger.setLevel(Level.DEBUG);
            Contact contact = new Contact();
            long startContact = Long.valueOf(String.valueOf(req.getSession().getAttribute("startContactNumber")));
            if (req.getParameter("startContactNumber") != null && !req.getParameter("startContactNumber").equals("")) {
                startContact = Long.parseLong(req.getParameter("startContactNumber"));
            }
            long step = Long.valueOf(String.valueOf(req.getSession().getAttribute("quantityOfContacts")));
            if (req.getParameter("quantityOfContacts") != null && Objects.equals(req.getParameter("quantityOfContacts"), "")) {
                step = Long.parseLong(req.getParameter("quantityOfContacts"));
            }
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String middleName = req.getParameter("middleName");
            if (!Objects.equals(firstName, "")) {
                contact.setFirstName(firstName);
            }
            if (!Objects.equals(lastName, "")) {
                contact.setLastName(lastName);
            }
            if (!Objects.equals(middleName, "")) {
                contact.setMiddleName(middleName);
            }
            Date birthdayFrom;
            try {
                birthdayFrom = Date.valueOf(req.getParameter("birthdayFrom"));

            } catch (Exception e) {
                logger.debug(e);
                birthdayFrom = Date.valueOf(LocalDate.of(1900, 1, 1));
            }
            Date birthdayTo;
            try {
                birthdayTo = Date.valueOf(req.getParameter("birthdayTo"));
            } catch (Exception e) {
                logger.debug(e);
                birthdayTo = Date.valueOf(LocalDate.now());
            }
            String gender = req.getParameter("gender");
            String nationality = req.getParameter("nationality");
            String maritalStatus = req.getParameter("maritalStatus");
            String contactGroup = req.getParameter("contactGroup");
            String country = req.getParameter("country");
            if (!Objects.equals(gender, "")) {
                contact.setGender(gender);
            }
            if (!Objects.equals(nationality, "")) {
                contact.setNationality(nationality);
            }
            if (!Objects.equals(maritalStatus, "")) {
                contact.setMaritalStatus(maritalStatus);
            }
            if (!Objects.equals(contactGroup, "")) {
                contact.setContactGroup(contactGroup);
            }
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
                logger.debug(e);
            }
            contact.setFlatNumber(flatNumber);
            int postalIndex = 0;
            try {
                postalIndex = Integer.parseInt(req.getParameter("postalIndex"));
            } catch (Exception e) {
                logger.debug(e);
            }
            contact.setPostcode(postalIndex);
            try {
                logger.debug(contact);
                contacts = service.searchContacts(contact, birthdayFrom, birthdayTo, startContact, step);
                numberOfContacts = service.getNumberOfSearchContacts(contact, birthdayFrom, birthdayTo);
            } catch (Exception e) {
                logger.error(e);
            }
            req.setAttribute("startContactNumber", startContact);
            req.setAttribute("quantityOfContacts", step);
            req.setAttribute("command", "search");
            req.setAttribute("contacts", contacts);
            req.setAttribute("numberOfContacts", numberOfContacts);
            return ConfigurationManager.getProperty("main");
        }
        req.setAttribute("message", MessageManager.getProperty("params_are_not_valid"));
        return ConfigurationManager.getProperty("search");
    }
}
