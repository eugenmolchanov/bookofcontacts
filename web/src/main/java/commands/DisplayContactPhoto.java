package commands;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import resources.ConfigurationManager;
import util.DisplayProfilePhoto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Yauhen Malchanau on 28.09.2017.
 */
public class DisplayContactPhoto implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        long id = Long.parseLong(req.getParameter("id"));
        ContactService contactService = ContactServiceImpl.getInstance();
        try {
            Contact contact = contactService.findById(id);
            byte[] imageBytes = DisplayProfilePhoto.execute(req, resp, contact);

            resp.setContentType("image/jpeg");
            resp.setContentLength(imageBytes.length);
            resp.getOutputStream().write(imageBytes);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ConfigurationManager.getProperty("contact");
    }
}
