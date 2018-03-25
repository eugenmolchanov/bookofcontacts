package com.itechart.javalab.firstproject.commands;

import com.itechart.javalab.firstproject.entities.Message;
import com.itechart.javalab.firstproject.service.MessageService;
import com.itechart.javalab.firstproject.service.impl.MessageServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.itechart.javalab.firstproject.resources.ConfigurationManager;
import com.itechart.javalab.firstproject.resources.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 08.10.2017.
 */
public class GetMessageCommand implements ActionCommand {

    private MessageService service = MessageServiceImpl.getInstance();
    private static Logger logger = Logger.getLogger(GetMessageCommand.class);
    private final String ACTIVE_PAGE = ConfigurationManager.getProperty("send_email");
    private final String ERROR_PAGE = ConfigurationManager.getProperty("error");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.setLevel(Level.DEBUG);
        try {
            long id = Long.parseLong(req.getParameter("id"));
            Message message = service.findById(id);
            req.setAttribute("message", message);
            return ACTIVE_PAGE;
        } catch (SQLException e) {
            req.setAttribute("warningMessage", MessageManager.getProperty("error"));
            return new ShowMessagesCommand().execute(req, resp);
        } catch (Exception e) {
            req.setAttribute("warningMessage", MessageManager.getProperty("error"));
            return ERROR_PAGE;
        }
    }
}
