package commands;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;
import util.Validation;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public class DeleteContacts implements ActionCommand {

    private ContactService<Contact> service = ContactServiceImpl.getInstance();
    private static Logger logger = Logger.getLogger(ListOfContactsCommand.class);
    private String page;

    @Override
    public String execute(HttpServletRequest req) {
        if (Validation.deleteContactsDataIsValid(req, logger)) {
            String[] parameters = req.getParameterMap().get("id");
            Set<Long> ids = new HashSet<>();
            for (String parameter : parameters) {
                ids.add(Long.parseLong(parameter));
            }
            try {
                service.deleteContacts(ids);
            } catch (SQLException e) {
                logger.error(e);
                req.setAttribute("message", MessageManager.getProperty(""));
                return page = ConfigurationManager.getProperty("error");
            }
            req.setAttribute("deleteMessage", MessageManager.getProperty("successful_delete"));
            return new ListOfContactsCommand().execute(req);
        } else {

            return new ListOfContactsCommand().execute(req);
        }
    }
}
