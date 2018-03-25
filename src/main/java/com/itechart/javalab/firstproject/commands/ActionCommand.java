package com.itechart.javalab.firstproject.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public interface ActionCommand {
    String execute(HttpServletRequest req, HttpServletResponse resp);
}
