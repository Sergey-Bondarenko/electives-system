package com.epamtraining.commands;

import com.epamtraining.entities.Account;
import com.epamtraining.exception.CommandException;
import com.epamtraining.exception.ServiceLogicalException;
import com.epamtraining.exception.ServiceTechnicalException;
import com.epamtraining.services.UserTypeService;

/**
 * Admin command that is allowed for execution for admins only
 * @author Sergey Bondarenko
 */
public abstract class AdminCommand extends ActionCommand{

    /**
     * Only admin has access to this command
     * @param account can be null
     * @return
     */
    @Override
    public boolean checkAccess(Account account) throws CommandException {
        if (account != null){
            try {
                if (UserTypeService.getAdminUserType().equals(account.getUserType())) {
                    return true;
                }
            } catch (ServiceLogicalException | ServiceTechnicalException e) {
                throw new CommandException(e);
            }
        }

        return false;
    }

}
