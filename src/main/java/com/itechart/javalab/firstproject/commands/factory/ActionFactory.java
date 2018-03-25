package com.itechart.javalab.firstproject.commands.factory;

import com.itechart.javalab.firstproject.commands.ActionCommand;
import com.itechart.javalab.firstproject.commands.EmptyCommand;
import com.itechart.javalab.firstproject.commands.client.CommandEnum;
import com.itechart.javalab.firstproject.resources.MessageManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public class ActionFactory {
    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand current = new EmptyCommand();
        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("warningMessage", MessageManager.getProperty("invalid_command"));
            throw e;
        }
        return current;
    }
}
