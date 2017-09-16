package util;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Map;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public class Validation {
    public static boolean paginationDataIsValid(HttpServletRequest req, Logger logger) {
        boolean result = true;
        if (req.getParameter("startContactNumber") != null) {
            try {
                Long.parseLong(req.getParameter("startContactNumber"));
            } catch (Exception e) {
                result = false;
                logger.debug("startContactNumber param is not valid.", e);
            }
        }
        if (req.getParameter("quantityOfContacts") != null) {
            try {
                Long.parseLong(req.getParameter("quantityOfContacts"));
            } catch (Exception e) {
                result = false;
                logger.debug("quantityOfContacts param is not valid.", e);
            }
        }
        if (req.getAttribute("startContactNumber") != null && req.getAttribute("quantityOfContacts")!= null) {
            result = true;
        }
        return result;
    }

    public static boolean deleteContactsDataIsValid(HttpServletRequest req, Logger logger) {
        boolean result = true;
        Map<String, String[]> parameters = req.getParameterMap();
        if (parameters.containsKey("id")) {
            for (String parameter : parameters.get("id")) {
                try {
                    Long.parseLong(parameter);
                } catch (Exception e) {
                    result = false;
                    logger.debug("id param is not valid.", e);
                }
            }
        } else {
            return false;
        }
        return result;
    }

    public static boolean createContactDataIsValid(HttpServletRequest req, Logger logger) {
        boolean result = true;
        if (!ServletFileUpload.isMultipartContent(req)) {
            result = false;
        }
        if (req.getParameter("firstName") == null) {
            result = false;
        }
        if (req.getParameter("lastName") == null) {
            result = false;
        }
//        if (req.getParameter("middleName") == null) {
//            result = false;
//        }
        if (req.getParameter("birthday") != null) {
            try {
                Date.valueOf(req.getParameter("birthday"));
            } catch (Exception e) {
                logger.debug(e);
                result = false;
            }
        } else {
            result = false;
        }
//        if (req.getParameter("gender") == null) {
//            result = false;
//        }
//        if (req.getParameter("nationality") == null) {
//            result = false;
//        }
//        if (req.getParameter("maritalStatus") == null) {
//            result = false;
//        }
//        if (req.getParameter("webSite") == null) {
//            result = false;
//        }
//        if (req.getParameter("email") == null) {
//            result = false;
//        }
//        if (req.getParameter("employmentPlace") == null) {
//            result = false;
//        }
//        if (req.getParameter("country") == null) {
//            result = false;
//        }
//        if (req.getParameter("city") == null) {
//            result = false;
//        }
//        if (req.getParameter("street") == null) {
//            result = false;
//        }
        if (req.getParameter("houseNumber") != null) {
            try {
                Integer.valueOf(req.getParameter("houseNumber"));
            } catch (Exception e) {
                logger.debug(e);
                result = false;
            }
        } else {
            result = false;
        }
        if (req.getParameter("flatNumber") != null) {
            try {
                Integer.valueOf(req.getParameter("flatNumber"));
            } catch (Exception e) {
                logger.debug(e);
                result = false;
            }
        } else {
            result = false;
        }
        if (req.getParameter("postalIndex") != null) {
            try {
                Integer.valueOf(req.getParameter("postalIndex"));
            } catch (Exception e) {
                logger.debug(e);
                result = false;
            }
        } else {
            result = false;
        }
        return result;
    }

    public static boolean redirectDataIsValid(HttpServletRequest req, Logger logger) {
        boolean result = false;
        String param = req.getParameter("form");
        if (param != null) {
            if (param.equals("createContact") || param.equals("contact")) {
                result = true;
            }
        }
        return result;
    }
}
