package com.itechart.javalab.firstproject.commands;

import com.itechart.javalab.firstproject.entities.*;
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

/**
 * Created by Yauhen Malchanau on 15.09.2017.
 */
public class CreateNewContactCommand implements ActionCommand {

    private static Logger logger = Logger.getLogger(CreateNewContactCommand.class);
    private ContactService service = ContactServiceImpl.getInstance();
    private final String ACTIVE_PAGE = ConfigurationManager.getProperty("create_contact");
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
            Map<String, Object> result = Validation.createContactData(parameters, logger);
            Map<String, String> validationMessages = (Map<String, String>) result.get("validation");
            if (validationMessages.size() == 0) {
                Contact contact = (Contact) result.get("contact");
                service.create(contact);
                req.setAttribute("successMessage", MessageManager.getProperty("successful_create"));
                return new ShowListOfContactsCommand().execute(req, resp);
            } else {
                logger.debug("Data isn't valid.");
                req.setAttribute("contact", result.get("thisContact"));
                req.setAttribute("validation", validationMessages);
                return ACTIVE_PAGE;
            }
        } catch (SQLException e) {
            req.setAttribute("warningMessage", MessageManager.getProperty("unsuccessful_create"));
            return ACTIVE_PAGE;
        } catch (Exception e) {
            req.setAttribute("warningMessage", MessageManager.getProperty("error"));
            return ERROR_PAGE;
        }
    }
}
