package com.epamtraining.commands.student;

import com.epamtraining.commands.StudentCommand;
import com.epamtraining.exception.*;
import com.epamtraining.services.CoursesService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Available courses for student
 * @author Sergey Bondarenko
 */
public class ActiveCoursesCommand extends StudentCommand {

    /**
     * Show courses available for student
     * @param request request to read the command from
     * @param response
     * @return
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        try {
            CoursesService.setCoursesWithRatings(request);
        } catch (ServiceLogicalException e) {
            throw new CommandException(e);
        } catch (ServiceTechnicalException e) {
            throw new CommandException(e);
        }

        return pathManager.getString("path.page.student.courses");
    }
}
