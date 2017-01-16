package com.epamtraining.commands.admin;

import com.epamtraining.builder.CourseBuilder;
import com.epamtraining.commands.AdminCommand;
import com.epamtraining.dao.DaoFactory;
import com.epamtraining.dao.interfaces.CourseDAO;
import com.epamtraining.entities.Course;
import com.epamtraining.exception.*;
import com.epamtraining.notification.Notification;
import com.epamtraining.notification.NotificationCreator;
import com.epamtraining.notification.NotificationService;
import com.epamtraining.resource.LocaleManager;
import com.epamtraining.services.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

/**
 * Add course command
 * @author Sergey Bondarenko
 */
public class AddCourseCommand extends AdminCommand {
    /**
     * Execute adding new course
     * @param request request to read the command from
     * @param response
     * @return jsp path
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Notification notification = null;
        Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
        Course course = new Course();

        try {
            CourseService.setInfoForCreateCourse(request);
        } catch (ServiceLogicalException | ServiceTechnicalException e) {
            throw new CommandException(e);
        }

        if (request.getParameter("submit") != null) {

            CourseBuilder courseBuilder = new CourseBuilder();
            try {
                courseBuilder.build(request.getParameterMap(), course);
                CourseDAO dao = DaoFactory.getDaoFactory().getCourseDao();
                if (dao.create(course)){
                    notification = NotificationCreator.createFromProperty("info.db.create_success", locale);
                    List<Course> courses = dao.findAll();
                    request.setAttribute("courses", courses);
                    return pathManager.getString("path.page.admin.manager");
                }

            } catch (BuildException e) {
                notification = NotificationCreator.createFromProperty("add.invalid_form_data", Notification.Type.ERROR,  locale);
            } catch (DAOTechnicalException e) {
                throw new CommandException(e);

            } catch (DAOLogicalException e) {
                notification = new Notification(e.getMessage(), Notification.Type.ERROR);
            } finally {
                if (notification != null){
                    NotificationService.push(request.getSession(), notification);
                }
            }
        }

        request.setAttribute("course", course);

        return pathManager.getString("path.page.admin.add_course");
    }
}
