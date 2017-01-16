package com.epamtraining.commands.admin;

import com.epamtraining.commands.AdminCommand;
import com.epamtraining.exception.*;
import com.epamtraining.services.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for editing accounts
 * @author Sergey Bondarenko
 */
public class AccountsCommand extends AdminCommand {

    /**
     * Accounts command. Displays a list of accounts in system
     * @param request request to read the command from
     * @param response
     * @return
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            AccountService.setAllAccounts(request);
        } catch (ServiceTechnicalException e) {
            throw new CommandException(e);
        } catch (ServiceLogicalException e) {
            throw new CommandException(e);
        }

        return pathManager.getString("path.page.admin.accounts");
    }
}
