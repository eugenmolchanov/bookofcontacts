package commands;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public class EmptyCommand implements ActionCommand {

    private ContactService<Contact> service = ContactServiceImpl.getInstance();
    private static Logger logger = Logger.getLogger(ListOfContactsCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        Set<Contact> contacts;
        long numberOfContacts;
        try {
            contacts = service.getSetOfContacts(0, 10);
            numberOfContacts = service.countContacts();
        } catch (SQLException e) {
            logger.error(e);
            req.setAttribute("message", MessageManager.getProperty(""));
            return ConfigurationManager.getProperty("error");
        }
        HttpSession session = req.getSession(true);
        session.setAttribute("startContactNumber", 0L);
        session.setAttribute("quantityOfContacts", 10L);
        req.setAttribute("startContactNumber", 0L);
        req.setAttribute("quantityOfContacts", 10L);
        req.setAttribute("command", "listOfContacts");
        req.setAttribute("numberOfContacts", numberOfContacts);
        req.setAttribute("contacts", contacts);
        return ConfigurationManager.getProperty("main");
    }
}
