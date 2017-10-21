package commands;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;
import util.Data;
import util.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 04.10.2017.
 */
public class EditContact implements ActionCommand {

    private static Logger logger = Logger.getLogger(EditContact.class);
    private ContactService service = ContactServiceImpl.getInstance();
    private final String ACTIVE_PAGE = ConfigurationManager.getProperty("contact");
    private final String ERROR_PAGE = ConfigurationManager.getProperty("error");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.setLevel(Level.DEBUG);
        try {
            Map<String, Object> parameters = Data.upload(req, logger);
            if (parameters == null) {
                req.setAttribute("warningMessage", MessageManager.getProperty("validation.data"));
                return ACTIVE_PAGE;
            }
            Map<String, Object> result = Validation.editContactData(parameters, logger);
            Map<String, String> validationMessages = (Map<String, String>) result.get("validation");
            Contact contact = (Contact) result.get("contact");
            if (validationMessages.size() == 0) {
                Set<Long> phonesForDelete = (Set<Long>) result.get("phonesForDelete");
                Set<Long> attachmentsForDelete = (Set<Long>) result.get("attachmentsForDelete");
                service.update(contact, phonesForDelete, attachmentsForDelete);
                req.setAttribute("id", contact.getId());
                req.setAttribute("successMessage", MessageManager.getProperty("successful.update"));
                return new DisplayContact().execute(req, resp);
            } else {
                logger.debug("Data for contact update are not valid.");
                req.setAttribute("id", contact.getId());
                req.setAttribute("validation", validationMessages);
                req.setAttribute("edit", true);
                return new DisplayContact().execute(req, resp);
            }
        } catch (SQLException e) {
            req.setAttribute("warningMessage", MessageManager.getProperty("unsuccessful.update"));
            return new DisplayContact().execute(req, resp);
        } catch (Exception e) {
            req.setAttribute("warningMessage", MessageManager.getProperty("error"));
            return ERROR_PAGE;
        }
    }
}
