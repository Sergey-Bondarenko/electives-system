package com.epamtraining.commands;

import com.epamtraining.entities.Account;
import com.epamtraining.exception.*;
import com.epamtraining.services.CoursesService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Archived courses command
 * @author Sergey Bondarenko
 */
public class ArchiveCoursesCommand extends ActionCommand {

    @Override
    public boolean checkAccess(Account account) {
        return true;
    }

    /**
     * Displays archival courses
     * @param request request to read the command from
     * @param response
     * @return
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        try {
            CoursesService.setArchivedCourses(request);
        } catch (ServiceLogicalException | ServiceTechnicalException e) {
            throw new CommandException(e);
        }

        return pathManager.getString("path.page.archive");
    }
}
