package com.epamtraining.commands;

import com.epamtraining.entities.Account;
import com.epamtraining.exception.*;
import com.epamtraining.services.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * View courses command
 * @author Sergey Bondarenko
 */
public class ViewCoursesCommand extends ActionCommand {
    @Override
    public boolean checkAccess(Account account) {
        return true;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        try {
            CourseService.setCoursesWithRatings(request);
        } catch (ServiceLogicalException | ServiceTechnicalException e) {
            throw new CommandException(e);
        }

        return pathManager.getString("path.page.courses");
    }
}
