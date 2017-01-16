package com.epamtraining.commands;

import com.epamtraining.entities.Account;

/**
 * Student command
 * @author Sergey Bondarenko
 */
public abstract class StudentCommand extends ActionCommand {
    @Override
    public boolean checkAccess(Account account) {
        if (account != null){
            Integer userType = account.getUserType().getId();
            return userType.equals(3);
        }

        return false;
    }
}
