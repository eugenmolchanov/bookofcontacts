package commands;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;
import util.Data;
import util.Validation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 04.10.2017.
 */
public class EditContact implements ActionCommand {

    private static Logger logger = Logger.getLogger(EditContact.class);
    private ContactService service = ContactServiceImpl.getInstance();
    private String page = ConfigurationManager.getProperty("contact");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.setLevel(Level.DEBUG);
        try {
            Map<String, Object> parameters = Data.upload(req);
            Map<String, Object> result = Validation.editContactData(parameters, logger);
            Map<String, String> validationMessages = (Map<String, String>) result.get("validation");
            Set<Long> phonesForDelete = (Set<Long>) result.get("phonesForDelete");
            Set<Long> attachmentsForDelete = (Set<Long>) result.get("attachmentsForDelete");
            if (validationMessages.size() == 0) {
                try {
                    Contact contact = (Contact) result.get("contact");
                    service.update(contact, phonesForDelete, attachmentsForDelete);
                } catch (SQLException e) {
                    logger.error(e);
                    req.setAttribute("message", MessageManager.getProperty("unsuccessful_create"));
                    return page;
                }
                req.setAttribute("message", MessageManager.getProperty("successful_create"));
                return page;
            } else {
                logger.debug("Data isn't valid.");
                req.setAttribute("validation", validationMessages);
                return page;
            }
        } catch (IOException | ServletException | FileUploadException e) {
            logger.error(e);
            req.setAttribute("message", MessageManager.getProperty("files_not_upload"));
            return page;
        }
    }
}
