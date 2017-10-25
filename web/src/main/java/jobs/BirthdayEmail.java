package jobs;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.services.ContactService;
import com.itechart.javalab.firstproject.services.MessageService;
import com.itechart.javalab.firstproject.services.impl.ContactServiceImpl;
import com.itechart.javalab.firstproject.services.impl.MessageServiceImpl;
import org.antlr.stringtemplate.StringTemplate;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import resources.MessageManager;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 07.10.2017.
 */
public class BirthdayEmail implements Job {

    private Logger logger = Logger.getLogger(BirthdayEmail.class);
    private MessageService messageService = MessageServiceImpl.getInstance();
    private ContactService contactService = ContactServiceImpl.getInstance();


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.setLevel(Level.DEBUG);
        logger.debug("Execute sending birthday message.");
        new Thread(() -> {
            ServletContext servletContext;
            try {
                servletContext = (ServletContext) jobExecutionContext.getScheduler().getContext().get("servletContext");
                servletContext.removeAttribute("alertMessage");
                Set<Contact> contacts = contactService.findContactsByBirthday(Date.valueOf(LocalDate.now()));
                if (contacts != null && contacts.size() > 0) {
                    String from = ResourceBundle.getBundle("mail_credentials").getString("from");
                    String password = ResourceBundle.getBundle("mail_credentials").getString("password");
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
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    String subject = MessageManager.getProperty("birthday_congratulations");
                    message.setSubject(subject);
                    String messageText = ResourceBundle.getBundle("js_messages").getString("template.birthday");
                    StringTemplate stringTemplate = new StringTemplate(messageText);
                    for (Contact contact : contacts) {
                        if (contact.getEmail() != null) {
                            stringTemplate.setAttribute("contact", contact);
                            message.setText(stringTemplate.toString());
                            message.setRecipient(Message.RecipientType.TO, new InternetAddress(contact.getEmail()));
                            Transport.send(message);
                            logger.debug("Birthday message was sent to " + contact.getEmail());
                            com.itechart.javalab.firstproject.entities.Message sendingMessage = new com.itechart.javalab.firstproject.entities.Message();
                            sendingMessage.setSendingDate(Timestamp.valueOf(LocalDateTime.now()));
                            sendingMessage.setText(stringTemplate.toString());
                            sendingMessage.setTopic(subject);
                            sendingMessage.setAddressee(contact);
                            messageService.save(sendingMessage);
                            stringTemplate.removeAttribute("contact");
                        }
                    }
                    servletContext.setAttribute("alertMessage", contacts);
                }
            } catch(Exception e){
                logger.error("Quartz failed to send birthday message automatically.");
                logger.error(e.getMessage(), e);
            }
        });
    }
}
