package com.epamtraining.commands;

import com.epamtraining.entities.Account;
import com.epamtraining.exception.CommandException;
import com.epamtraining.exception.ServiceLogicalException;
import com.epamtraining.exception.ServiceTechnicalException;
import com.epamtraining.services.UserTypeService;

/**
 * Teacher command
 * @author Sergey Bondarenko
 */
public abstract class TeacherCommand extends ActionCommand{
	@Override
	public boolean checkAccess(Account account) throws CommandException {
		if (account != null){
			try {
				if (UserTypeService.getTeacherUserType().equals(account.getUserType())) {
					return true;
				}
			} catch (ServiceLogicalException | ServiceTechnicalException e) {
				throw new CommandException(e);
			}
		}
		return false;
	}
}
