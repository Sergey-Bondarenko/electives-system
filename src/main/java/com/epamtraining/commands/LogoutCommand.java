package com.epamtraining.commands;

import com.epamtraining.entities.Account;
import com.epamtraining.exception.CommandException;
import com.epamtraining.services.AuthenticationService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Logout command
 * @author Sergey Bondarenko
 */
public class LogoutCommand extends ActionCommand {

    private final Logger logger = Logger.getRootLogger();

    /**
     * Check logout. Everyone can logout
     * @param account can be null
     * @return
     */
    @Override
    public boolean checkAccess(Account account) {
        return true;
    }

    /**
     * Execute logout
     * @param request request to read the command from
     * @param response
     * @return
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Account account = (Account) request.getSession().getAttribute(AuthenticationService.SESSION_VAR);
        if (account != null) {
            AuthenticationService.logout(request.getSession());
            logger.info("Logged out: " + account.getName());
        }
        return pathManager.getString("path.page.main");
    }
}
