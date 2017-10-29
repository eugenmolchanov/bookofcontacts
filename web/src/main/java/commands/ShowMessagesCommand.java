package commands;

import com.itechart.javalab.firstproject.entities.Message;
import com.itechart.javalab.firstproject.services.MessageService;
import com.itechart.javalab.firstproject.services.impl.MessageServiceImpl;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 08.10.2017.
 */
public class ShowMessagesCommand implements ActionCommand {

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
            long numberOfMessages;
            long numberOfDeletedMessages;
            messages = service.getMessages(startMessageNumber, quantityOfMessages);
            for (Message message : messages) {
                message.setTopic(message.getTopic().replace("<", "&lt").replace(">", "&gt"));
                message.setText(message.getText().replace("<", "&lt").replace(">", "&gt"));
            }
            numberOfMessages = service.getNumberOfAllMessages();
            numberOfDeletedMessages = service.getNumberOfAllDeletedMessages();
            req.setAttribute("numberOfMessages", numberOfMessages);
            req.setAttribute("numberOfDeletedMessages", numberOfDeletedMessages);
            req.setAttribute("startMessageNumber", startMessageNumber);
            req.setAttribute("quantityOfMessages", quantityOfMessages);
            req.setAttribute("command", "showMessages");
            req.setAttribute("messages", messages);
            return ACTIVE_PAGE;
        } catch (Exception e) {
            req.setAttribute("warningMessage", MessageManager.getProperty("error"));
            return ERROR_PAGE;
        }
    }
}
