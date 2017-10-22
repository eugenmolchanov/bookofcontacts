package commands;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;
import util.EquivalentsForSelects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yauhen Malchanau on 28.09.2017.
 */
public class DisplayContact implements ActionCommand {

    private static Logger logger = Logger.getLogger(DisplayContact.class);
    private ContactService service = ContactServiceImpl.getInstance();
    private final String ACTIVE_PAGE = ConfigurationManager.getProperty("contact");
    private final String ERROR_PAGE = ConfigurationManager.getProperty("error");
    //fwfwvfw

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.setLevel(Level.DEBUG);
        long id = 0;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (Exception e) {
            logger.debug("id of contact is not valid or id param is absent.");
        }
        try {
            id = (long) req.getAttribute("id");
        } catch (Exception e) {
            logger.debug("id of contact is not valid or id attribute is absent.");
        }
        try {
            Contact contact = service.findById(id);
            req.setAttribute("currentGender", contact.getGender());
            req.setAttribute("currentMaritalStatus", contact.getMaritalStatus());
            req.setAttribute("currentContactGroup", contact.getContactGroup());
            EquivalentsForSelects.fill(contact);
            req.setAttribute("contact", contact);
            return ACTIVE_PAGE;
        } catch (Exception  e) {
            req.setAttribute("warningMessage", MessageManager.getProperty("error"));
            return ERROR_PAGE;
        }
    }
}
