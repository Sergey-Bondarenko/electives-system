package com.epamtraining.commands.admin;

import com.epamtraining.commands.AdminCommand;
import com.epamtraining.exception.*;
import com.epamtraining.services.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for managing courses
 * @author Sergey Bondarenko
 */
public class ManagerCommand extends AdminCommand {
    /**
     * Displays a list of courses with the ability to
     * delete, update or create a new one
     *
     * @param request request to read the command from
     * @param response
     * @return
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try{
            CourseService.setAllCourses(request);
        } catch (ServiceLogicalException e) {
            throw new CommandException(e);
        } catch (ServiceTechnicalException e) {
            throw new CommandException(e);
        }
        return pathManager.getString("path.page.admin.manager");
    }
}
