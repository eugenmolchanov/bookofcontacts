package commands;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;
import util.EquivalentForSelect;
import util.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public class ShowListOfContactsCommand implements ActionCommand {

    private ContactService service = ContactServiceImpl.getInstance();
    private static Logger logger = Logger.getLogger(ShowListOfContactsCommand.class);
    private final String ACTIVE_PAGE = ConfigurationManager.getProperty("main");
    private final String ERROR_PAGE = ConfigurationManager.getProperty("error");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (Validation.paginationDataIsValid(req, logger)) {
            long startContactNumber;
            try {
                startContactNumber = Long.parseLong(req.getParameter("startContactNumber"));
            } catch (Exception e) {
                logger.debug("Getting attribute for startContactNumber variable from session.");
                startContactNumber = (long) req.getSession().getAttribute("startContactNumber");
            }
            long quantityOfContacts;
            try {
                quantityOfContacts = Long.parseLong(req.getParameter("quantityOfContacts"));
            } catch (Exception e) {
                logger.debug("Getting attribute for quantityOfContacts variable from session");
                quantityOfContacts = (long) req.getSession().getAttribute("quantityOfContacts");
            }
            try {
                HttpSession session = req.getSession(true);
                session.setAttribute("startContactNumber", startContactNumber);
                session.setAttribute("quantityOfContacts", quantityOfContacts);
                Set<Contact> contacts;
                long numberOfContacts;
                contacts = service.getSetOfContacts(startContactNumber, quantityOfContacts);
                EquivalentForSelect.fill(contacts);
                numberOfContacts = service.getNumberOfContacts();
                req.setAttribute("numberOfContacts", numberOfContacts);
                req.setAttribute("startContactNumber", startContactNumber);
                req.setAttribute("quantityOfContacts", quantityOfContacts);
                req.setAttribute("command", "listOfContacts");
                req.setAttribute("contacts", contacts);
                return ACTIVE_PAGE;
            } catch (Exception e) {
                req.setAttribute("warningMessage", MessageManager.getProperty("error"));
                return ERROR_PAGE;
            }
        } else {
            req.setAttribute("warningMessage", MessageManager.getProperty("invalid.data"));
            return ERROR_PAGE;
        }
    }
}
