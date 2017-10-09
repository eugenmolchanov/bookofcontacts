package commands;

import com.itechart.javalab.firstproject.services.MessageService;
import com.itechart.javalab.firstproject.services.impl.MessageServiceImpl;
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

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (Validation.deleteMessagesDataIsValid(req, logger)) {
            String[] parameters = req.getParameterMap().get("id");
            Set<Long> ids = new HashSet<>();
            for (String parameter : parameters) {
                ids.add(Long.parseLong(parameter));
            }
            try {
                service.deleteMessages(ids);
            } catch (SQLException e) {
                logger.error(e);
                req.setAttribute("message", MessageManager.getProperty("error"));
                return ConfigurationManager.getProperty("error");
            }
            req.setAttribute("deleteMessage", MessageManager.getProperty("message_successful_delete"));
            return new ShowMessages().execute(req, resp);
        } else {
            return new ShowMessages().execute(req, resp);
        }
    }
}
