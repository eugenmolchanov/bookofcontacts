package commands;

import com.itechart.javalab.firstproject.services.MessageService;
import com.itechart.javalab.firstproject.services.impl.MessageServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;
import util.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 18.10.2017.
 */
public class RestoreMessageCommand implements ActionCommand {

    private MessageService service = MessageServiceImpl.getInstance();
    private static Logger logger = Logger.getLogger(SendMessagesToBucketCommand.class);
    private final String ERROR_PAGE = ConfigurationManager.getProperty("error");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.setLevel(Level.DEBUG);
        if (Validation.restoreMessagesDataIsValid(req, logger)) {
            String[] parameters = req.getParameterMap().get("id");
            Set<Long> ids = new HashSet<>();
            for (String parameter : parameters) {
                ids.add(Long.parseLong(parameter));
            }
            try {
                service.restore(ids);
                req.setAttribute("successMessage", MessageManager.getProperty("successful.message.restore"));
                return new ShowDeletedMessagesCommand().execute(req, resp);
            } catch (SQLException e) {
                req.setAttribute("warningMessage", MessageManager.getProperty("invalid.message.restore"));
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
