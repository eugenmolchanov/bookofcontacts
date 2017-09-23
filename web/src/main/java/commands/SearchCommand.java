package commands;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;
import util.Validation;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 16.09.2017.
 */
public class SearchCommand implements ActionCommand {

    private static Logger logger = Logger.getLogger(CreateNewContactCommand.class);
    private ContactService<Contact> service = ContactServiceImpl.getInstance();
    private Set<Contact> contacts;
    private long numberOfContacts;
    private Contact contact = new Contact();
    private Address address = new Address();

    @Override
    public String execute(HttpServletRequest req) {
        if (Validation.searchDataIsValid(req, logger)) {
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
            String country = req.getParameter("country");
            if (!Objects.equals(country, "")) {
                address.setCountry(country);
            }
            String city = req.getParameter("city");
            if (!Objects.equals(city, "")) {
                address.setCity(city);
            }
            String street = req.getParameter("street");
            if (!Objects.equals(street, "")) {
                address.setStreet(street);
            }
            int houseNumber = 0;
            try {
                houseNumber = Integer.parseInt(req.getParameter("houseNumber"));
            } catch (Exception e) {
                logger.debug(e);
            }
            address.setHouseNumber(houseNumber);
            int flatNumber = 0;
            try {
                flatNumber = Integer.parseInt(req.getParameter("flatNumber"));
            } catch (Exception e) {
                logger.debug(e);
            }
            address.setFlatNumber(flatNumber);
            int postalIndex = 0;
            try {
                postalIndex = Integer.parseInt(req.getParameter("postcode"));
            } catch (Exception e) {
                logger.debug(e);
            }
            address.setPostalIndex(postalIndex);
            if (!Objects.equals(firstName, "")) {
                contact.setFirstName(firstName);
            }
            if (!Objects.equals(lastName, "")) {
                contact.setLastName(lastName);
            }
            if (!Objects.equals(middleName, "")) {
                contact.setMiddleName(middleName);
            }
            if (!Objects.equals(nationality, "")) {
                contact.setNationality(nationality);
            }
            contact.setGender(gender);
            if (!Objects.equals(maritalStatus, "")) {
                contact.setMaritalStatus(maritalStatus);
            }
            contact.setAddress(address);
            try {
                contacts = service.searchContacts(contact, birthdayFrom, birthdayTo, startContact, step);
                numberOfContacts = service.getCountOfSearchContacts(contact, birthdayFrom, birthdayTo);
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
