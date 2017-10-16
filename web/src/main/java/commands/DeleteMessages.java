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
 * Created by Yauhen Malchanau on 09.10.2017.
 */
public class DeleteMessages implements ActionCommand{

    private MessageService service = MessageServiceImpl.getInstance();
    private static Logger logger = Logger.getLogger(DeleteMessages.class);
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
                service.deleteMessages(ids);
                req.setAttribute("successMessage", MessageManager.getProperty("successful.message.delete"));
                return new ShowMessages().execute(req, resp);
            } catch (SQLException e) {
                req.setAttribute("warningMessage", MessageManager.getProperty("invalid.message.delete"));
                return new ShowMessages().execute(req, resp);
            } catch (Exception e) {
                req.setAttribute("warningMessage", MessageManager.getProperty("error"));
                return ERROR_PAGE;
            }
        } else {
            req.setAttribute("warningMessage", MessageManager.getProperty("invalid.data"));
            return new ShowMessages().execute(req, resp);
        }
    }
}
