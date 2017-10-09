package commands;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.entities.Message;
import com.itechart.javalab.firstproject.services.MessageService;
import com.itechart.javalab.firstproject.services.impl.MessageServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 08.10.2017.
 */
public class GetMessage implements ActionCommand {

    private MessageService service = MessageServiceImpl.getInstance();
    private static Logger logger = Logger.getLogger(ShowMessages.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.setLevel(Level.DEBUG);
        long id = 0;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (Exception e) {
            logger.debug(e);
        }
        Message message = null;
        try {
            message = service.findById(id);
        } catch (SQLException e) {
            logger.error(e);
        }
        Set<String> emails = new HashSet<>();
        for (Contact contact : message.getAddressees()) {
            if (contact.getEmail() != null) {
                emails.add(contact.getEmail());
            }
        }
        req.setAttribute("message", message);
        req.setAttribute("emails", emails);
        return ConfigurationManager.getProperty("send_email");
    }
}
