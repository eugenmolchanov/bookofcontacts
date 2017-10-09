package commands;

import com.itechart.javalab.firstproject.entities.Message;
import com.itechart.javalab.firstproject.services.MessageService;
import com.itechart.javalab.firstproject.services.impl.MessageServiceImpl;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 08.10.2017.
 */
public class ShowMessages implements ActionCommand {

    private MessageService service = MessageServiceImpl.getInstance();
    private static Logger logger = Logger.getLogger(ShowMessages.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        long startMessageNumber;
        try {
            startMessageNumber = Long.parseLong(req.getParameter("startMessageNumber"));
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            startMessageNumber = 0;
        }
        long quantityOfMessages;
        try {
            quantityOfMessages = Long.parseLong(req.getParameter("quantityOfMessages"));
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            quantityOfMessages = 10;
        }
        Set<Message> messages;
        long numberOfMessages;
        try {
            messages = service.getMessages(startMessageNumber, quantityOfMessages);
            numberOfMessages = service.getNumberOfAllMessages();
        } catch (SQLException e) {
            logger.error(e);
            req.setAttribute("message", MessageManager.getProperty("error"));
            return ConfigurationManager.getProperty("error");
        }
        req.setAttribute("numberOfMessages", numberOfMessages);
        req.setAttribute("startMessageNumber", startMessageNumber);
        req.setAttribute("quantityOfMessages", quantityOfMessages);
        req.setAttribute("command", "showMessages");
        req.setAttribute("messages", messages);
        return ConfigurationManager.getProperty("message");
    }
}
