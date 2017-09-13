package commands;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public interface ActionCommand {
    String execute(HttpServletRequest req);
}
