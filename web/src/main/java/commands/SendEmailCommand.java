package commands;

import org.apache.log4j.Logger;
import resources.ConfigurationManager;
import resources.MessageManager;
import util.Validation;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Properties;

import org.antlr.stringtemplate.*;

/**
 * Created by Yauhen Malchanau on 17.09.2017.
 */
public class SendEmailCommand implements ActionCommand {

    private static Logger logger = Logger.getLogger(ListOfContactsCommand.class);
    private String from = "johnnymolchanov@gmail.com";
    private String password = "1234567abc";
    private String host = "smtp.gmail.com";
    private String port = "587";
    private String page;

    @Override
    public String execute(HttpServletRequest req) {
        if (Validation.sendEmailDataIsValid(req, logger)) {
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
            String parameter = req.getParameter("addressees");
            try {
                MimeMessage message = new MimeMessage(session);
                String subject = req.getParameter("topic");
                message.setFrom(new InternetAddress(from));
                message.setSubject(subject);
                if (req.getParameter("template") != null && !Objects.equals(req.getParameter("template"), "")) {
                    String template = req.getParameter("message");
                    StringTemplate stringTemplate = new StringTemplate(template);
                    //устанавливаем атрибуты в template
                    message.setText(stringTemplate.toString());
                    String[] emails = parameter.split("\\s+");
                    for (String email : emails) {
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                        Transport.send(message);
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
            } catch (MessagingException e) {
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
