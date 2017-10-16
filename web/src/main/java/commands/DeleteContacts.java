package commands;

import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;
import util.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public class DeleteContacts implements ActionCommand {

    private ContactService service = ContactServiceImpl.getInstance();
    private static Logger logger = Logger.getLogger(DeleteContacts.class);
    private final String ERROR_PAGE = ConfigurationManager.getProperty("error");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.setLevel(Level.DEBUG);
        if (Validation.deleteContactsDataIsValid(req, logger)) {
            String[] parameters = req.getParameterMap().get("id");
            Set<Long> ids = new HashSet<>();
            for (String parameter : parameters) {
                ids.add(Long.parseLong(parameter));
            }
            try {
                service.deleteContacts(ids);
                req.setAttribute("successMessage", MessageManager.getProperty("successful.contact.delete"));
                return new ShowListOfContacts().execute(req, resp);
            } catch (SQLException e) {
                req.setAttribute("warningMessage", MessageManager.getProperty("invalid.contact.delete"));
                return new ShowListOfContacts().execute(req, resp);
            } catch (Exception e) {
                req.setAttribute("warningMessage", MessageManager.getProperty("error"));
                return ERROR_PAGE;
            }
        } else {
            req.setAttribute("warningMessage", MessageManager.getProperty("invalid.data"));
            return new ShowListOfContacts().execute(req, resp);
        }
    }
}
