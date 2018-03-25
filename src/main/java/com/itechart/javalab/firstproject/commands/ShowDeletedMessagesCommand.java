package com.itechart.javalab.firstproject.commands;

import com.itechart.javalab.firstproject.entities.Message;
import com.itechart.javalab.firstproject.service.MessageService;
import com.itechart.javalab.firstproject.service.impl.MessageServiceImpl;
import org.apache.log4j.Logger;
import com.itechart.javalab.firstproject.resources.ConfigurationManager;
import com.itechart.javalab.firstproject.resources.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 18.10.2017.
 */
public class ShowDeletedMessagesCommand implements ActionCommand {

    private MessageService service = MessageServiceImpl.getInstance();
    private static Logger logger = Logger.getLogger(ShowMessagesCommand.class);
    private final String ACTIVE_PAGE = ConfigurationManager.getProperty("message");
    private final String ERROR_PAGE = ConfigurationManager.getProperty("error");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        long startMessageNumber;
        try {
            startMessageNumber = Long.parseLong(req.getParameter("startMessageNumber"));
        } catch (Exception e) {
            startMessageNumber = 0;
        }
        long quantityOfMessages;
        try {
            quantityOfMessages = Long.parseLong(req.getParameter("quantityOfMessages"));
        } catch (Exception e) {
            quantityOfMessages = 10;
        }
        try {
            Set<Message> messages;
            long numberOfDeletedMessages;
            messages = service.getDeletedMessages(startMessageNumber, quantityOfMessages);
            for (Message message : messages) {
                message.setTopic(message.getTopic().replace("<", "&lt").replace(">", "&gt"));
                message.setText(message.getText().replace("<", "&lt").replace(">", "&gt"));
            }
            numberOfDeletedMessages = service.getDeletedMessagesNumber();
            req.setAttribute("numberOfMessages", numberOfDeletedMessages);
            req.setAttribute("numberOfDeletedMessages", numberOfDeletedMessages);
            req.setAttribute("startMessageNumber", startMessageNumber);
            req.setAttribute("quantityOfMessages", quantityOfMessages);
            req.setAttribute("command", "showDeletedMessages");
            req.setAttribute("messages", messages);
            return ACTIVE_PAGE;
        } catch (Exception e) {
            req.setAttribute("warningMessage", MessageManager.getProperty("error"));
            return ERROR_PAGE;
        }
    }
}
