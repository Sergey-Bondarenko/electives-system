package com.epamtraining.commands.teacher;

import com.epamtraining.commands.TeacherCommand;
import com.epamtraining.entities.Account;
import com.epamtraining.exception.*;
import com.epamtraining.services.AuthenticationService;
import com.epamtraining.services.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Teacher page command
 * @author Sergey Bondarenko
 */
public class AccountCommand extends TeacherCommand {
    /**
     * Displays list of courses for teacher
     * @param request request to read the command from
     * @param response
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Account account = AuthenticationService.account(request);

        try {
            CourseService.setCoursesForTeacher(request, account);
        } catch (ServiceLogicalException | ServiceTechnicalException e) {
            throw new CommandException(e);
        }

        return pathManager.getString("path.page.teacher.account");
    }
}
