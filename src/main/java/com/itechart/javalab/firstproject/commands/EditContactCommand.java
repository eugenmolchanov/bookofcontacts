package com.itechart.javalab.firstproject.commands;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.service.ContactService;
import com.itechart.javalab.firstproject.service.impl.ContactServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.itechart.javalab.firstproject.resources.ConfigurationManager;
import com.itechart.javalab.firstproject.resources.MessageManager;
import com.itechart.javalab.firstproject.util.Data;
import com.itechart.javalab.firstproject.util.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 04.10.2017.
 */
public class EditContactCommand implements ActionCommand {

    private static Logger logger = Logger.getLogger(EditContactCommand.class);
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
                return new DisplayContactCommand().execute(req, resp);
            } else {
                logger.debug("Data for contact update are not valid.");
                req.setAttribute("id", contact.getId());
                req.setAttribute("validation", validationMessages);
                req.setAttribute("edit", true);
                return new DisplayContactCommand().execute(req, resp);
            }
        } catch (SQLException e) {
            req.setAttribute("warningMessage", MessageManager.getProperty("unsuccessful.update"));
            return new DisplayContactCommand().execute(req, resp);
        } catch (Exception e) {
            req.setAttribute("warningMessage", MessageManager.getProperty("error"));
            return ERROR_PAGE;
        }
    }
}
