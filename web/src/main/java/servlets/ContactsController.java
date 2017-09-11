package servlets;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Евгений Молчанов on 11.09.2017.
 */
@WebServlet(urlPatterns = "/contacts")
public class ContactsController extends HttpServlet {

    private ContactService<Contact> service = ContactServiceImpl.getInstance();
    private Logger logger = Logger.getLogger(ContactsController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<Contact> contacts = new HashSet<>();
        long startContactNumber = 0;
        long quantityOfContacts = 10;
        if (req.getParameter("startContactNumber") != null) {
            startContactNumber = Long.parseLong(req.getParameter("startContactNumber"));
        }
        if (req.getParameter("quantityOfContacts") != null) {
            quantityOfContacts = Long.parseLong(req.getParameter("quantityOfContacts"));
        }
        try {
            contacts.addAll(service.getSetOfContacts(startContactNumber, quantityOfContacts));
        } catch (SQLException e) {
            logger.error(e);
        }
        req.setAttribute("contacts", contacts);
        req.getServletContext().getRequestDispatcher(ConfigurationManager.getProperty("main")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
