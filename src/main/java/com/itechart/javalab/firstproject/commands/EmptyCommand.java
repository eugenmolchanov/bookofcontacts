package com.itechart.javalab.firstproject.commands;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.service.ContactService;
import com.itechart.javalab.firstproject.service.impl.ContactServiceImpl;
import org.apache.log4j.Logger;
import com.itechart.javalab.firstproject.resources.ConfigurationManager;
import com.itechart.javalab.firstproject.resources.MessageManager;
import com.itechart.javalab.firstproject.util.EquivalentForSelect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public class EmptyCommand implements ActionCommand {

    private ContactService service = ContactServiceImpl.getInstance();
    private static Logger logger = Logger.getLogger(EmptyCommand.class);
    private final String ACTIVE_PAGE = ConfigurationManager.getProperty("main");
    private final String ERROR_PAGE = ConfigurationManager.getProperty("error");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Set<Contact> contacts;
            long numberOfContacts;
            contacts = service.getContacts(0, 10);
            EquivalentForSelect.fill(contacts);
            numberOfContacts = service.getContactsNumber();
            HttpSession session = req.getSession(true);
            session.setAttribute("startContactNumber", 0L);
            session.setAttribute("startMessageNumber", 0L);
            session.setAttribute("quantityOfContacts", 10L);
            session.setAttribute("quantityOfMessages", 10L);
            req.setAttribute("startContactNumber", 0L);
            req.setAttribute("quantityOfContacts", 10L);
            req.setAttribute("command", "listOfContacts");
            req.setAttribute("numberOfContacts", numberOfContacts);
            req.setAttribute("contacts", contacts);
            return ACTIVE_PAGE;
        } catch (Exception e) {
            logger.error(e);
            req.setAttribute("warningMessage", MessageManager.getProperty("error"));
            return ERROR_PAGE;
        }
    }
}
