package com.itechart.javalab.firstproject.commands;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.itechart.javalab.firstproject.resources.ConfigurationManager;
import com.itechart.javalab.firstproject.resources.MessageManager;
import com.itechart.javalab.firstproject.util.Validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Yauhen Malchanau on 18.09.2017.
 */
public class ChangeLanguageCommand implements ActionCommand {

    private static Logger logger = Logger.getLogger(ChangeLanguageCommand.class);
    private final String ERROR_PAGE = ConfigurationManager.getProperty("error");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.setLevel(Level.DEBUG);
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
            MessageManager.resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());
            HttpSession session = req.getSession(true);
            session.setAttribute("language", language);
            return new EmptyCommand().execute(req, resp);
        }
        req.setAttribute("warningMessage", MessageManager.getProperty("error"));
        return ERROR_PAGE;
    }
}
