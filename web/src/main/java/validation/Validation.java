package validation;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public class Validation {
    public static boolean paginationIsValid(HttpServletRequest req, Logger logger) {
        int counterOfValidParameters = 0;
        if (req.getParameter("startContactNumber") != null) {
            try {
                Long.parseLong(req.getParameter("startContactNumber"));
                counterOfValidParameters++;
            } catch (Exception e) {
                logger.debug("startContactNumber param is not valid.", e);
            }
        }
        if (req.getParameter("quantityOfContacts") != null) {
            try {
                Long.parseLong(req.getParameter("quantityOfContacts"));
                counterOfValidParameters++;
            } catch (Exception e) {
                logger.debug("quantityOfContacts param is not valid.", e);
            }
        }
        return counterOfValidParameters == 2;
    }

    public static boolean deleteContactsisValid(HttpServletRequest req, Logger logger) {
        int counterOfValidParameters = 0;
        int conterOfParams = 0;
        Map<String, String[]> parameters = req.getParameterMap();
        if (parameters.containsKey("id")) {
            for (String parameter : parameters.get("id")) {
                try {
                    conterOfParams++;
                    Long.parseLong(parameter);
                    counterOfValidParameters++;
                } catch (Exception e) {
                    logger.debug("id param is not valid.", e);
                }
            }
        } else {
            return false;
        }
        return counterOfValidParameters == conterOfParams;
    }
}
