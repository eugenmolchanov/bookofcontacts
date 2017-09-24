package servlet;

import commands.ActionCommand;
import commands.factory.ActionFactory;
import resources.ConfigurationManager;
import resources.MessageManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
@WebServlet("/controller")
public class Controller extends HttpServlet {

    private String errorPage = ConfigurationManager.getProperty("error");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page;
        ActionFactory client = new ActionFactory();
        ActionCommand command = null;
        try {
            command = client.defineCommand(req);
        } catch (IllegalArgumentException e) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(errorPage);
            dispatcher.forward(req, resp);
        }
        page = command != null ? command.execute(req) : null;
        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        } else {
            req.setAttribute("message", MessageManager.getProperty("error"));
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(errorPage);
            dispatcher.forward(req, resp);
        }
    }
}
