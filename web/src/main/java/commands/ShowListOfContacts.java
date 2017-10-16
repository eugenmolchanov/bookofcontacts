package commands;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;
import util.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public class ShowListOfContacts implements ActionCommand {

    private ContactService service = ContactServiceImpl.getInstance();
    private static Logger logger = Logger.getLogger(ShowListOfContacts.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (Validation.paginationDataIsValid(req, logger)) {
            long startContactNumber;
            try {
                startContactNumber = Long.parseLong(req.getParameter("startContactNumber"));
            } catch (Exception e) {
                logger.debug("Getting attribute for variable from the server.");
                startContactNumber = (long) req.getSession().getAttribute("startContactNumber");
            }
            long quantityOfContacts;
            try {
                quantityOfContacts = Long.parseLong(req.getParameter("quantityOfContacts"));
            } catch (Exception e) {
                logger.debug("");
                quantityOfContacts = (long) req.getSession().getAttribute("quantityOfContacts");
            }
            HttpSession session = req.getSession(true);
            session.setAttribute("startContactNumber", startContactNumber);
            session.setAttribute("quantityOfContacts", quantityOfContacts);
            Set<Contact> contacts;
            long numberOfContacts;
            try {
                contacts = service.getSetOfContacts(startContactNumber, quantityOfContacts);
                numberOfContacts = service.getNumberOfContacts();
            } catch (SQLException e) {
                logger.error(e);
                req.setAttribute("message", MessageManager.getProperty(""));
                return ConfigurationManager.getProperty("error");
            }
            req.setAttribute("numberOfContacts", numberOfContacts);
            req.setAttribute("startContactNumber", startContactNumber);
            req.setAttribute("quantityOfContacts", quantityOfContacts);
            req.setAttribute("command", "listOfContacts");
            req.setAttribute("contacts", contacts);
            return ConfigurationManager.getProperty("main");
        } else {
            req.setAttribute("message", MessageManager.getProperty("invalid.data"));
            return ConfigurationManager.getProperty("error");
        }
    }
}
