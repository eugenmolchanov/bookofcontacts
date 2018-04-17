package com.itechart.javalab.firstproject.commands;

import com.itechart.javalab.firstproject.service.MessageService;
import com.itechart.javalab.firstproject.service.impl.MessageServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.itechart.javalab.firstproject.resources.ConfigurationManager;
import com.itechart.javalab.firstproject.resources.MessageManager;
import com.itechart.javalab.firstproject.util.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 18.10.2017.
 */
public class DeleteMessagesCommand implements ActionCommand {

    private MessageService service = MessageServiceImpl.getInstance();
    private static Logger logger = Logger.getLogger(SendMessagesToBucketCommand.class);
    private final String ERROR_PAGE = ConfigurationManager.getProperty("error");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.setLevel(Level.DEBUG);
        if (Validation.deleteMessagesDataIsValid(req, logger)) {
            String[] parameters = req.getParameterMap().get("id");
            Set<Long> ids = new HashSet<>();
            for (String parameter : parameters) {
                ids.add(Long.parseLong(parameter));
            }
            try {
                service.remove(ids);
                req.setAttribute("successMessage", MessageManager.getProperty("successful.message.delete"));
                return new ShowDeletedMessagesCommand().execute(req, resp);
            } catch (SQLException e) {
                req.setAttribute("warningMessage", MessageManager.getProperty("invalid.message.delete"));
                return new ShowDeletedMessagesCommand().execute(req, resp);
            } catch (Exception e) {
                req.setAttribute("warningMessage", MessageManager.getProperty("error"));
                return ERROR_PAGE;
            }
        } else {
            req.setAttribute("warningMessage", MessageManager.getProperty("invalid.data"));
            return new ShowDeletedMessagesCommand().execute(req, resp);
        }
    }
}
