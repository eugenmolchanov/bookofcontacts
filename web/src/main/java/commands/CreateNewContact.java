package commands;

import com.itechart.javalab.firstproject.entities.*;
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

/**
 * Created by Yauhen Malchanau on 15.09.2017.
 */
public class CreateNewContact implements ActionCommand {

    private static Logger logger = Logger.getLogger(CreateNewContact.class);
    private ContactService service = ContactServiceImpl.getInstance();
    private String page = ConfigurationManager.getProperty("create_contact");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.setLevel(Level.DEBUG);
        try {
            Map<String, Object> parameters = Data.upload(req);
            Map<String, Object> result = Validation.createContactData(parameters, logger);
            Map<String, String> validationMessages = (Map<String, String>) result.get("validation");
            if (validationMessages.size() == 0) {
                try {
                    Contact contact = (Contact) result.get("contact");
                    service.create(contact);
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
