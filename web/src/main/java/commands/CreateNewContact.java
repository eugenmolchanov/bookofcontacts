package commands;

import com.itechart.javalab.firstproject.entities.*;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;
import util.Data;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 15.09.2017.
 */
public class CreateNewContact implements ActionCommand {

    private static Logger logger = Logger.getLogger(CreateNewContact.class);
    private ContactService service = ContactServiceImpl.getInstance();
    private String page = ConfigurationManager.getProperty("create_contact");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.setLevel(Level.DEBUG);
        if (true) {
            try {
                System.out.println(req.getParameter("firstName"));
                Map<String, Object> parameters = Data.upload(req);
                String firstName = (String) parameters.get("firstName");
                String lastName = (String) parameters.get("lastName");
                String middleName = null;
                if (parameters.get("middleName") != null) {
                    middleName = (String) parameters.get("middleName");
                }
                Date birthday = null;
                if (parameters.get("birthday") != null) {
                    birthday = Date.valueOf((String) (parameters.get("birthday")));
                }
                String gender = null;
                if (parameters.get("gender") != null) {
                    gender = (String) parameters.get("gender");
                }
                String nationality = null;
                if (parameters.get("nationality") != null) {
                    nationality = (String) parameters.get("nationality");
                }
                String maritalStatus = null;
                if (parameters.get("maritalStatus") != null) {
                    maritalStatus = (String) parameters.get("maritalStatus");
                }
                String webSite = null;
                if (parameters.get("webSite") != null) {
                    webSite = (String) parameters.get("webSite");
                }
                String email = null;
                if (parameters.get("email") != null) {
                    email = (String) parameters.get("email");
                }
                String employmentPlace = null;
                if (parameters.get("employmentPlace") != null) {
                    employmentPlace = (String) parameters.get("employmentPlace");
                }
                String contactGroup = null;
                if (parameters.get("contactGroup") != null) {
                    contactGroup = (String) parameters.get("contactGroup");
                }
                String country = null;
                if (parameters.get("country") != null) {
                    country = (String) parameters.get("country");
                }
                String city = null;
                if (parameters.get("city") != null) {
                    city = (String) parameters.get("city");
                }
                String street = null;
                if (parameters.get("street") != null) {
                    street = (String) parameters.get("street");
                }
                String houseNumber = null;
                if (parameters.get("houseNumber") != null) {
                    houseNumber = (String) parameters.get("houseNumber");
                }
                int flatNumber = 0;
                if (parameters.get("flatNumber") != null) {
                    flatNumber = Integer.parseInt(String.valueOf(parameters.get("flatNumber")));
                }
                int postalIndex = 0;
                if (parameters.get("postalIndex") != null) {
                    postalIndex = Integer.parseInt(String.valueOf(parameters.get("postalIndex")));
                }
                Set<Attachment> attachments = null;
                if (parameters.get("attachments") != null) {
                    attachments = (Set<Attachment>) parameters.get("attachments");
                }
                Set<Phone> phones = null;
                if (parameters.get("phones") != null) {
                    phones = (Set<Phone>) parameters.get("phones");
                }
                Photo photo = new Photo(0, "none", "none");
                if (parameters.get("image") != null) {
                    photo = (Photo) parameters.get("image");
                }
                Contact contact = new Contact(0, firstName, lastName, middleName, birthday, gender, nationality, maritalStatus, webSite, email, employmentPlace, contactGroup,
                        country, city, street, houseNumber, flatNumber, postalIndex, phones, attachments, photo);
                try {
                    service.create(contact);
                } catch (SQLException e) {
                    logger.error(e);
                    req.setAttribute("message", MessageManager.getProperty("unsuccessful_create"));
                    return page;
                }
                req.setAttribute("message", MessageManager.getProperty("successful_create"));
                return page;
            } catch (IOException | ServletException | FileUploadException e) {
                logger.error(e);
                req.setAttribute("message", MessageManager.getProperty("files_not_upload"));
                return page;
            }
        }
        logger.debug("Data isn't valid.");
        req.setAttribute("message", MessageManager.getProperty("params_are_not_valid"));
        return page;
    }
}
