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

    private static Logger logger = Logger.getLogger(ShowListOfContacts.class);
    private ContactService contactService = ContactServiceImpl.getInstance();
    private MessageService messageService = MessageServiceImpl.getInstance();
    private String from = "johnnymolchanov@gmail.com";
    private String password = "1234567abc";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String page = ConfigurationManager.getProperty("send_email");
        Map<String, String> validationMessages = Validation.sendEmailDataIsValid(req, logger);
        if (validationMessages.size() == 0) {
            logger.setLevel(Level.DEBUG);
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
            String parameter = null;
            try {
                parameter = req.getParameter("addressees");
            } catch (Exception e) {
                logger.debug(e.getMessage(), e);
            }
            try {
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
                        Contact contact = contactService.findByEmail(email);
                        if (contact != null && contact.getId() != 0) {
                            contacts.add(contact);
                        } else {
                            req.setAttribute("messageText", MessageManager.getProperty("invalid_emails"));
                            return page;
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
                        Contact contact = contactService.findByEmail(email);
                        if (contact != null && contact.getId() != 0) {
                            contacts.add(contact);
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
            } catch (MessagingException e) {
                logger.error(e.getMessage(), e);
                req.setAttribute("messageText", MessageManager.getProperty("send_message_error"));
                return page;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                req.setAttribute("messageText", MessageManager.getProperty("save_error"));
                return page;
            }
            req.setAttribute("messageText", MessageManager.getProperty("successful_sending"));
            return page;
        } else {
            req.setAttribute("messageText", MessageManager.getProperty("params_are_not_valid"));
            req.setAttribute("validation", validationMessages);
            return page;
        }
    }
}
