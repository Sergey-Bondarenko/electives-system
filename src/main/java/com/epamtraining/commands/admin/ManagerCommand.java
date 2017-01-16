package com.epamtraining.commands.admin;

import com.epamtraining.commands.AdminCommand;
import com.epamtraining.dao.DaoFactory;
import com.epamtraining.entities.Course;
import com.epamtraining.exception.CommandException;
import com.epamtraining.exception.DAOLogicalException;
import com.epamtraining.exception.DAOTechnicalException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
            List<Course> courses = DaoFactory.getDaoFactory().getCourseDao().findAll();
            request.setAttribute("courses", courses);
        } catch (DAOLogicalException e) {
            throw new CommandException(e);
        } catch (DAOTechnicalException e) {
            throw new CommandException(e);
        }
        return pathManager.getString("path.page.admin.manager");
    }
}
