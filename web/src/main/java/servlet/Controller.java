package servlet;

import commands.ActionCommand;
import commands.factory.ActionFactory;
import jobs.SendBirthdayEmail;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.JobExecutionContextImpl;
import org.quartz.impl.StdSchedulerFactory;
import resources.ConfigurationManager;
import resources.MessageManager;
import util.Convertion;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
@WebServlet("/controller")
public class Controller extends HttpServlet {

    private Logger logger = Logger.getLogger(Controller.class);
    private String errorPage = ConfigurationManager.getProperty("error");

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        logger.setLevel(Level.DEBUG);
        String key = "org.quartz.impl.StdSchedulerFactory.KEY";
        ServletContext servletContext = config.getServletContext();
        StdSchedulerFactory factory = (StdSchedulerFactory) servletContext.getAttribute(key);
        Scheduler scheduler;
        try {
            scheduler = factory.getScheduler("MyQuartzScheduler");
            config.getServletContext().setAttribute("scheduler", scheduler);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (!((Scheduler) getServletContext().getAttribute("scheduler")).isStarted()) {
                Scheduler scheduler = ((Scheduler) getServletContext().getAttribute("scheduler"));
                scheduler.start();
                scheduler.getContext().put("servletContext", getServletContext());
                JobDetail job = newJob(SendBirthdayEmail.class).withIdentity("sendEmailAutomatically", "group").build();
                Trigger trigger = newTrigger().withIdentity("emailTrigger", "group").startNow().withSchedule(simpleSchedule().withIntervalInSeconds(86400).repeatForever()).build();
                scheduler.scheduleJob(job, trigger);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        String page = null;
        ActionFactory client = new ActionFactory();
        ActionCommand command = null;
        try {
            command = client.defineCommand(req);
        } catch (IllegalArgumentException e) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(errorPage);
            try {
                dispatcher.forward(req, resp);
            } catch (ServletException | IOException e1) {
                logger.error(e.getMessage(), e);
            }
        }
        try {
            page = command != null ? command.execute(req, resp) : null;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(errorPage);
            try {
                dispatcher.forward(req, resp);
            } catch (ServletException | IOException e1) {
                logger.error(e1.getMessage(), e1);
            }
        }
        if (!resp.isCommitted()) {
            if (page != null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                try {
                    Map<String, String> map = Convertion.convertResourceBundleToMap(ResourceBundle.getBundle("messages"));
                    req.setAttribute("validationMessages", map);
                    dispatcher.forward(req, resp);
                } catch (ServletException | IOException e) {
                    logger.error(e.getMessage(), e);
                }
            } else {
                req.setAttribute("message", MessageManager.getProperty("error"));
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(errorPage);
                try {
                    dispatcher.forward(req, resp);
                } catch (ServletException | IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}
