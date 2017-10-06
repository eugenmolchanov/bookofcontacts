package commands;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 28.09.2017.
 */
public class DisplayContact implements ActionCommand {

    private static Logger logger = Logger.getLogger(DisplayContact.class);
    private ContactService service = ContactServiceImpl.getInstance();
    private String page;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.setLevel(Level.DEBUG);
        long id = 0;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (Exception e) {
            logger.debug(e);
        }
        try {
            id = (long) req.getAttribute("id");
        } catch (Exception e) {
            logger.debug(e);
        }
        Contact contact = null;
        try {
            contact = service.findById(id);
        } catch (SQLException  e) {
            logger.error(e);
        }
        req.setAttribute("contact", contact);
        return page = ConfigurationManager.getProperty("contact");
    }
}
