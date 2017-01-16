package com.epamtraining.commands;

import com.epamtraining.entities.Account;

/**
 * Admin command that is allowed for execution for admins only
 */
public abstract class AdminCommand extends ActionCommand{

    /**
     * Only admin has access to this command
     * @param account can be null
     * @return
     */
    @Override
    public boolean checkAccess(Account account) {
        if (account != null){
            Integer userType = account.getUserType().getId();
            if (userType != null && userType.equals(1)){
                return true;
            }
        }

        return false;
    }

}
