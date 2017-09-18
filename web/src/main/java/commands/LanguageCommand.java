package commands;

import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import util.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Created by Yauhen Malchanau on 18.09.2017.
 */
public class LanguageCommand implements ActionCommand {

    private static Logger logger = Logger.getLogger(ListOfContactsCommand.class);
    private String page;

    @Override
    public String execute(HttpServletRequest req) {
        if (Validation.languageDataIsValid(req, logger)) {
            Locale russian = new Locale("ru", "RU");
            Locale belorussian = new Locale("be", "BY");
            String language = req.getParameter("language");
            switch (language) {
                case "en_US":
                    Locale.setDefault(Locale.US);
                    break;
                case "ru_RU":
                    Locale.setDefault(russian);
                    break;
                case "be_BY":
                    Locale.setDefault(belorussian);
                    break;
            }
            HttpSession session = req.getSession(true);
            session.setAttribute("language", language);
            return new EmptyCommand().execute(req);
        }
        return new EmptyCommand().execute(req);
    }
}
