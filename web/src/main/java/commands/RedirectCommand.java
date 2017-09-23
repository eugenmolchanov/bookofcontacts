package commands;

import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import util.Validation;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yauhen Malchanau on 15.09.2017.
 */
public class RedirectCommand implements ActionCommand {

    private static Logger logger = Logger.getLogger(CreateNewContactCommand.class);
    private String page;
    private final String createContact = "createContact";
    private final String contact = "contact";
    private final String search = "search";
    private final String sendEmail = "sendEmail";

    @Override
    public String execute(HttpServletRequest req) {
        if (Validation.redirectDataIsValid(req, logger)) {
            final String form = req.getParameter("form");
            switch (form) {
                case createContact :
                    return page = ConfigurationManager.getProperty("create_contact");
                case contact :
                    return page = ConfigurationManager.getProperty("contact");
                case search :
                    return page = ConfigurationManager.getProperty("search");
                case sendEmail :
                    String[] emails = req.getParameterMap().get("email");
                    req.setAttribute("emails", emails);
                    return page = ConfigurationManager.getProperty("send_email");
                default:
                    return new ListOfContactsCommand().execute(req);

            }
        }

        return new ListOfContactsCommand().execute(req);
    }
}
