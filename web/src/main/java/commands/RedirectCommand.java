package commands;

import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import util.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yauhen Malchanau on 15.09.2017.
 */
public class RedirectCommand implements ActionCommand {

    private static Logger logger = Logger.getLogger(RedirectCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        final String createContact = "createContact";
        final String contact = "contact";
        final String search = "search";
        final String sendEmail = "sendEmail";
        final String message = "message";
        if (Validation.redirectDataIsValid(req, logger)) {
            final String form = req.getParameter("form");
            switch (form) {
                case createContact:
                    return ConfigurationManager.getProperty("create_contact");
                case contact:
                    return ConfigurationManager.getProperty("contact");
                case search:
                    return ConfigurationManager.getProperty("search");
                case sendEmail:
                    String[] emails = req.getParameterMap().get("email");
                    req.setAttribute("emails", emails);
                    return ConfigurationManager.getProperty("send_email");
                case message:
                    return ConfigurationManager.getProperty("message");
                default:
                    return new ShowListOfContactsCommand().execute(req, resp);
            }
        }
        return new ShowListOfContactsCommand().execute(req, resp);
    }
}
