package commands.factory;

import commands.ActionCommand;
import commands.EmptyCommand;
import commands.client.CommandEnum;
import resources.MessageManager;

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
            request.setAttribute("attention", action + MessageManager.getProperty("wrong_action"));
        }
        return current;
    }
}
