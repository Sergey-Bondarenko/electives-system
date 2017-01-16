package com.epamtraining.commands;

import com.epamtraining.entities.Account;

/**
 * Teacher command
 * @author Sergey Bondarenko
 */
public abstract class TeacherCommand extends ActionCommand{
	@Override
	public boolean checkAccess(Account account) {
		if (account != null){
			Integer userType = account.getUserType().getId();
			return userType.equals(2);
		}

		return false;
	}
}
