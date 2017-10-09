package jobs;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.MessageService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import com.itechart.javalab.firstproject.services.impl.MessageServiceImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import resources.MessageManager;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 07.10.2017.
 */
public class SendBirthdayEmail implements Job {

    private Logger logger = Logger.getLogger(SendBirthdayEmail.class);
    private MessageService messageService = MessageServiceImpl.getInstance();
    private ContactService contactService = ContactServiceImpl.getInstance();


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.setLevel(Level.DEBUG);
        logger.debug("execute sending message to white");
        ServletContext servletContext = null;
        try {
            servletContext = (ServletContext) jobExecutionContext.getScheduler().getContext().get("servletContext");
            servletContext.removeAttribute("alertMessage");
        } catch (SchedulerException e) {
            logger.debug(e);
        }
        Set<Contact> contacts = null;
        try {
            contacts = contactService.findContactsByBirthday(Date.valueOf(LocalDate.now()));
            logger.debug(contacts.size());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        if (contacts != null && contacts.size() > 0) {
            String from = "johnnymolchanov@gmail.com";
            String password = "1234567abc";
            String host = "smtp.gmail.com";
            String port = "587";
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
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setSubject("Happy Birthday!");
                String messageText = MessageManager.getProperty("birthday_congratulations");
                message.setText(messageText);
                for (Contact contact : contacts) {
                    if (contact.getEmail() != null) {
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(contact.getEmail()));
                        Transport.send(message);
                    }
                }
                assert servletContext != null;
                servletContext.setAttribute("alertMessage", contacts);
            } catch (MessagingException e) {
                logger.error(e);
            }
        }
    }
}
