package com.epamtraining.commands;

import com.epamtraining.dao.DaoFactory;
import com.epamtraining.entities.Account;
import com.epamtraining.entities.Course;
import com.epamtraining.exception.CommandException;
import com.epamtraining.exception.DAOLogicalException;
import com.epamtraining.exception.DAOTechnicalException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
            List<Course> courses = DaoFactory.getDaoFactory().getCourseDao().findActiveCourses();
            request.setAttribute("activecourses", courses);
        } catch (DAOLogicalException | DAOTechnicalException e) {
            throw new CommandException(e);
        }

        return pathManager.getString("path.page.courses");
    }
}
