package commands;

import com.itechart.javalab.firstproject.entities.Address;
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
    private String page;
    private Date birthdayFrom;
    private Date birthdayTo;
    private Address address = new Address();

    @Override
    public String execute(HttpServletRequest req) {
        if (Validation.searchDataIsValid(req, logger)) {
            long startContact = Long.valueOf(String.valueOf(req.getSession().getAttribute("startContactNumber")));
            long step = Long.valueOf(String.valueOf(req.getSession().getAttribute("quantityOfContacts")));
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String middleName = req.getParameter("middleName");
            try {
                birthdayFrom = Date.valueOf(req.getParameter("birthdayFrom"));
            } catch (Exception e) {
                logger.debug(e);
                birthdayFrom = Date.valueOf(LocalDate.of(1900, 1, 1));
            }
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
            if (!country.isEmpty()) {
                address.setCountry(country);
            }
            String city = req.getParameter("city");
            if (!city.isEmpty()) {
                address.setCity(city);
            }
            String street = req.getParameter("street");
            if (!street.isEmpty()) {
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
                postalIndex = Integer.parseInt(req.getParameter("postalIndex"));
            } catch (Exception e) {
                logger.debug(e);
            }
            address.setPostalIndex(postalIndex);
            if (!firstName.isEmpty()) {
                contact.setFirstName(firstName);
            }
            if (!lastName.isEmpty()) {
                contact.setLastName(lastName);
            }
            if (!middleName.isEmpty()) {
                contact.setMiddleName(middleName);
            }
            if (!nationality.isEmpty()) {
                contact.setNationality(nationality);
            }
            contact.setGender(gender);
            if (!maritalStatus.isEmpty()) {
                contact.setMaritalStatus(maritalStatus);
            }
            contact.setAddress(address);
            try {
                contacts = service.searchContacts(contact, birthdayFrom, birthdayTo, startContact, step);
                numberOfContacts = service.countContacts();
            } catch (Exception e) {
                logger.error(e);
            }
            req.setAttribute("contacts", contacts);
            req.setAttribute("numberOfContacts", numberOfContacts);
            return page = ConfigurationManager.getProperty("main");
        }
        req.setAttribute("message", MessageManager.getProperty("params_are_not_valid"));
        return page = ConfigurationManager.getProperty("search");
    }
}
