package commands;

import resources.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public class EmptyCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {
        return ConfigurationManager.getProperty("main");
    }
}
