package com.epamtraining.commands;

import com.epamtraining.entities.Account;
import com.epamtraining.exception.CommandException;
import com.epamtraining.resource.PathManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This command is used if empty or wrong command is specified
 * @author Sergey Bondarenko
 */
public class EmptyCommand extends ActionCommand{

    @Override
    public boolean checkAccess(Account user) {
        return true;
    }

    /**
     * Default command that render main page
     * @param request request to read the command from
     * @param response response
     * @return path
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return PathManager.INSTANCE.getString("path.page.main");
    }
}

