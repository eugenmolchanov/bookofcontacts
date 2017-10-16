package commands;

import com.itechart.javalab.firstproject.entities.*;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.MessageService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import com.itechart.javalab.firstproject.services.impl.MessageServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;
import util.Validation;

import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import org.antlr.stringtemplate.*;
import util.entity.BaseContact;

/**
 * Created by Yauhen Malchanau on 17.09.2017.
 */
public class SendEmail implements ActionCommand {

    private static Logger logger = Logger.getLogger(SendEmail.class);
    private ContactService contactService = ContactServiceImpl.getInstance();
    private MessageService messageService = MessageServiceImpl.getInstance();
    private String from = ResourceBundle.getBundle("mail_credentials").getString("from");
    private String password = ResourceBundle.getBundle("mail_credentials").getString("password");
    private final String ACTIVE_PAGE = ConfigurationManager.getProperty("send_email");
    private final String ERROR_PAGE = ConfigurationManager.getProperty("error");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.setLevel(Level.DEBUG);
        Map<String, String> validationMessages = Validation.sendEmailDataIsValid(req, logger);
        if (validationMessages.size() == 0) {
            try {
                Properties properties = System.getProperties();
                String host = "smtp.gmail.com";
                properties.put("mail.smtp.host", host);
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                String port = "587";
                properties.put("mail.smtp.port", port);
                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });
                String parameter = req.getParameter("addressees");
                MimeMessage message = new MimeMessage(session);
                com.itechart.javalab.firstproject.entities.Message sendingMessage = new com.itechart.javalab.firstproject.entities.Message();
                String subject = req.getParameter("topic");
                message.setFrom(new InternetAddress(from));
                message.setSubject(subject);
                if (req.getParameter("template") != null && !Objects.equals(req.getParameter("template"), "")) {
                    String template = req.getParameter("message");
                    StringTemplate stringTemplate = new StringTemplate(template);
                    String[] emails = parameter.split("\\s+");
                    Set<Contact> contacts = new HashSet<>();
                    for (String email : emails) {
                        try {
                            Contact contact = contactService.findByEmail(email);
                            if (contact != null && contact.getId() != 0) {
                                contacts.add(contact);
                            } else {
                                req.setAttribute("warningMessage", MessageManager.getProperty("invalid.email"));
                                return ACTIVE_PAGE;
                            }
                        } catch (SQLException e) {
                            req.setAttribute("warningMessage", MessageManager.getProperty("send.message.error"));
                            return ACTIVE_PAGE;
                        }
                    }
                    if (contacts.size() == emails.length) {
                        for (Contact contact : contacts) {
                            stringTemplate.setAttribute("contact", contact);
                            message.setText(stringTemplate.toString());
                            message.addRecipient(Message.RecipientType.TO, new InternetAddress(contact.getEmail()));
                            Transport.send(message);
                            sendingMessage.getAddressees().add(contact);
                            logger.debug("Email was sent to ".concat(contact.getLastName()).concat(" ").concat(contact.getFirstName()).concat(". ")
                                    .concat(stringTemplate.toString()));
                        }
                        BaseContact baseContact = new BaseContact();
                        stringTemplate.removeAttribute("contact");
                        stringTemplate.setAttribute("contact", baseContact);
                        sendingMessage.setText(stringTemplate.toString());
                        sendingMessage.setTopic(subject);
                        sendingMessage.setSendingDate(Timestamp.valueOf(LocalDateTime.now()));
                        messageService.save(sendingMessage);
                    }
                } else {
                    String messageText = req.getParameter("message");
                    message.setText(messageText);
                    String[] emails = parameter.split("\\s+");
                    Set<Contact> contacts = new HashSet<>();
                    for (String email : emails) {
                        try {
                            Contact contact = contactService.findByEmail(email);
                            if (contact != null && contact.getId() != 0) {
                                contacts.add(contact);
                            }
                        } catch (SQLException e) {
                            req.setAttribute("warningMessage", MessageManager.getProperty("send.message.error"));
                            return ACTIVE_PAGE;
                        }
                    }
                    for (String email : emails) {
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                        Transport.send(message);
                    }
                    sendingMessage.setTopic(subject);
                    sendingMessage.setText(messageText);
                    sendingMessage.setAddressees(contacts);
                    sendingMessage.setSendingDate(Timestamp.valueOf(LocalDateTime.now()));
                    messageService.save(sendingMessage);
                }
                req.setAttribute("successMessage", MessageManager.getProperty("successful.sending"));
                return ACTIVE_PAGE;
            } catch (MessagingException e) {
                logger.error(e.getMessage(), e);
                req.setAttribute("warningMessage", MessageManager.getProperty("send.message.error"));
                return ACTIVE_PAGE;
            } catch (SQLException e) {
                req.setAttribute("warningMessage", MessageManager.getProperty("save.error"));
                return ACTIVE_PAGE;
            } catch (Exception e) {
                req.setAttribute("warningMessage", MessageManager.getProperty("error"));
                return ERROR_PAGE;
            }
        } else {
            req.setAttribute("warningMessage", MessageManager.getProperty("send.message.error"));
            req.setAttribute("validation", validationMessages);
            return ACTIVE_PAGE;
        }
    }
}
