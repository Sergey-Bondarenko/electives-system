package com.epamtraining.commands;

import com.epamtraining.entities.Account;
import com.epamtraining.exception.CommandException;
import com.epamtraining.resource.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Abstract class for commands
 */
public abstract class ActionCommand {

    /**
     * Path manager
     */
    protected static final PathManager pathManager = PathManager.INSTANCE;

    /**
     * Check the access of user, return true if the user has access to
     * this command, otherwise return false
     * @param account can be null
     * @return
     */
    public abstract boolean checkAccess(Account account);

    /**
     * This method reads a command from the request
     * and processes it. The result will be given as
     * a page to forward to
     *
     * @param request request to read the command from
     * @param response
     * @return forward page
     */
    public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}

