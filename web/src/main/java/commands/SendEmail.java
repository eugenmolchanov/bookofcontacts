package commands;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;
import util.Validation;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import org.antlr.stringtemplate.*;

/**
 * Created by Yauhen Malchanau on 17.09.2017.
 */
public class SendEmail implements ActionCommand {

    private static Logger logger = Logger.getLogger(ShowListOfContacts.class);
    private ContactService service = ContactServiceImpl.getInstance();
    private String from = "johnnymolchanov@gmail.com";
    private String password = "1234567abc";
    private String host = "smtp.gmail.com";
    private String port = "587";
    private String page;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (Validation.sendEmailDataIsValid(req, logger)) {
            logger.setLevel(Level.DEBUG);
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.port", port);
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });
            String parameter = null;
            try {
                parameter = req.getParameter("addressees");
            } catch (Exception e) {
                logger.debug(e.getMessage(), e);
            }
            try {
                MimeMessage message = new MimeMessage(session);
                String subject = req.getParameter("topic");
                message.setFrom(new InternetAddress(from));
                message.setSubject(subject);
                if (req.getParameter("template") != null && !Objects.equals(req.getParameter("template"), "")) {
                    String template = req.getParameter("message");
                    StringTemplate stringTemplate = new StringTemplate(template);
                    String[] emails = parameter.split("\\s+");
                    Set<Contact> contacts = new HashSet<>();
                    for (String email : emails) {
                        Contact contact = service.findByEmail(email);
                        if (contact != null) {
                            contacts.add(contact);
                        } else {
                            req.setAttribute("message", MessageManager.getProperty("invalid_emails"));
                            return page = ConfigurationManager.getProperty("send_email");
                        }
                    }
                    if (contacts.size() == emails.length) {
                        for (Contact contact : contacts) {
                            stringTemplate.setAttribute("contact", contact);
                            message.setText(stringTemplate.toString());
                            message.addRecipient(Message.RecipientType.TO, new InternetAddress(contact.getEmail()));
                            Transport.send(message);
                            logger.debug("Email was sent to ".concat(contact.getLastName()).concat(" ").concat(contact.getFirstName()).concat(". ")
                            .concat(stringTemplate.toString()));
                        }
                    }
                } else {
                    String messageText = req.getParameter("message");
                    message.setText(messageText);
                    String[] emails = parameter.split("\\s+");
                    for (String email : emails) {
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                        Transport.send(message);
                    }
                }
            } catch (MessagingException | SQLException e) {
                logger.error(e);
                req.setAttribute("message", MessageManager.getProperty("error"));
                return page = ConfigurationManager.getProperty("send_email");
            }
            req.setAttribute("message", MessageManager.getProperty("successful_sending"));
            return page = ConfigurationManager.getProperty("send_email");
        }
        req.setAttribute("message", MessageManager.getProperty("params_are_not_valid"));
        return page = ConfigurationManager.getProperty("send_email");
    }
}
