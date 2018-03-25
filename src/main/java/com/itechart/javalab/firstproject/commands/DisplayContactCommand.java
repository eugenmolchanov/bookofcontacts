package com.itechart.javalab.firstproject.commands;

import com.itechart.javalab.firstproject.entities.Attachment;
import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.service.ContactService;
import com.itechart.javalab.firstproject.service.impl.ContactServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.itechart.javalab.firstproject.resources.ConfigurationManager;
import com.itechart.javalab.firstproject.resources.MessageManager;
import com.itechart.javalab.firstproject.util.EquivalentForSelect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yauhen Malchanau on 28.09.2017.
 */
public class DisplayContactCommand implements ActionCommand {

    private static Logger logger = Logger.getLogger(DisplayContactCommand.class);
    private ContactService service = ContactServiceImpl.getInstance();
    private final String ACTIVE_PAGE = ConfigurationManager.getProperty("contact");
    private final String ERROR_PAGE = ConfigurationManager.getProperty("error");

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
            if (contact.getId() == 0) {
                req.setAttribute("warningMessage", MessageManager.getProperty("invalid.data"));
                return ERROR_PAGE;
            }
            for (Attachment attachment : contact.getAttachments()) {
                attachment.setFileName(attachment.getFileName().replace("<", "&lt").replace(">", "&gt"));
            }
            req.setAttribute("currentGender", contact.getGender());
            req.setAttribute("currentMaritalStatus", contact.getMaritalStatus());
            req.setAttribute("currentContactGroup", contact.getContactGroup());
            EquivalentForSelect.fill(contact);
            req.setAttribute("contact", contact);
            return ACTIVE_PAGE;
        } catch (Exception  e) {
            req.setAttribute("warningMessage", MessageManager.getProperty("error"));
            return ERROR_PAGE;
        }
    }
}
