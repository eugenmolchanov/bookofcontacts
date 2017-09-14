package commands;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;
import validation.Validation;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public class ListOfContactsCommand implements ActionCommand {

    private ContactService<Contact> service = ContactServiceImpl.getInstance();
    private static Logger logger = Logger.getLogger(ListOfContactsCommand.class);
    private long startContactNumber;
    private long quantityOfContacts;
    private Set<Contact> contacts;
    private String page;

    @Override
    public String execute(HttpServletRequest req) {
        if (Validation.paginationIsValid(req, logger)) {
            startContactNumber = Long.parseLong(req.getParameter("startContactNumber"));
            quantityOfContacts = Long.parseLong(req.getParameter("quantityOfContacts"));
            try {
                contacts = service.getSetOfContacts(startContactNumber, quantityOfContacts);
            } catch (SQLException e) {
                logger.error(e);
                req.setAttribute("message", MessageManager.getProperty(""));
                return page = ConfigurationManager.getProperty("error");
            }
            req.setAttribute("contacts", contacts);
            return page = ConfigurationManager.getProperty("main");
        } else {
            req.setAttribute("message", MessageManager.getProperty("params_are_not_valid"));
            return page = ConfigurationManager.getProperty("error");
        }
    }
}
