package commands;

import com.itechart.javalab.firstproject.entities.*;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;
import util.Data;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Yauhen Malchanau on 15.09.2017.
 */
public class CreateNewContactCommand implements ActionCommand {

    private static Logger logger = Logger.getLogger(CreateNewContactCommand.class);
    private ContactService<Contact> service = ContactServiceImpl.getInstance();
    private Set<Phone> phones = new HashSet<>();
    private Set<Attachment> attachments = new HashSet<>();
    private String page;

    @Override
    public String execute(HttpServletRequest req) {
        if (true) {
            Map<String, Object> parameters;
            try {
                parameters = Data.uploadImage(req);
                String firstName = (String) parameters.get("firstName");
                String lastName = (String) parameters.get("lastName");
                String middleName = (String) parameters.get("middleName");
                Date birthday = Date.valueOf(String.valueOf(parameters.get("birthday")));
                String gender = (String) parameters.get("gender");
                String nationality = (String) parameters.get("nationality");
                String maritalStatus = (String) parameters.get("maritalStatus");
                String webSite = (String) parameters.get("webSite");
                String email = (String) parameters.get("email");
                String employmentPlace = (String) parameters.get("employmentPlace");
                String country = (String) parameters.get("country");
                String city = (String) parameters.get("city");
                String street = (String) parameters.get("street");
                int houseNumber = Integer.parseInt(String.valueOf(parameters.get("houseNumber")));
                int flatNumber = Integer.parseInt(String.valueOf(parameters.get("flatNumber")));
                int postalIndex = Integer.parseInt(String.valueOf(parameters.get("postalIndex")));
                Address address = new Address(0, country, city, street, houseNumber, flatNumber, postalIndex);
                Photo photo = (Photo) parameters.get("image");
                Contact contact = new Contact(0, firstName, lastName, middleName, birthday, gender, nationality, maritalStatus, webSite, email, employmentPlace, address,
                        phones, attachments, photo);
                try {
                    service.create(contact);
                } catch (SQLException e) {
                    logger.error(e);
                }
                req.setAttribute("message", MessageManager.getProperty("successful_create"));
                return page = ConfigurationManager.getProperty("create_contact");
            } catch (IOException | ServletException | FileUploadException e) {
                e.printStackTrace();
            }
        }
        req.setAttribute("message", MessageManager.getProperty("create_fail"));
        return page = ConfigurationManager.getProperty("create_contact");
    }
}
