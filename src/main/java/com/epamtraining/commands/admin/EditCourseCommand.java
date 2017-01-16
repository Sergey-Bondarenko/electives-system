package com.epamtraining.commands.admin;

import com.epamtraining.builder.CourseBuilder;
import com.epamtraining.commands.AdminCommand;
import com.epamtraining.entities.Course;
import com.epamtraining.exception.*;
import com.epamtraining.notification.Notification;
import com.epamtraining.notification.NotificationCreator;
import com.epamtraining.notification.NotificationService;
import com.epamtraining.resource.LocaleManager;
import com.epamtraining.services.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Edit course
 * @author Sergey Bondarenko
 */
public class EditCourseCommand extends AdminCommand {
    /**
     *
     * @param request request to read the command from
     * @param response
     * @return
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Notification notification = null;
        Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
        Course course;
        int id;

        try {
            CourseService.setInfoForCourse(request);
        } catch (ServiceLogicalException | ServiceTechnicalException e) {
            throw new CommandException(e);
        }

        try {
            try {
                id = Integer.parseInt(request.getParameter("cid"));

            } catch (NumberFormatException e){
                notification = NotificationCreator.createFromProperty("error.invalid_parameter", Notification.Type.ERROR, locale);
                return pathManager.getString("path.page.admin.manager");
            }

            if (request.getParameter("submit") != null){
                course = new Course();
                course.setId(id);

                CourseBuilder courseBuilder = new CourseBuilder();
                try {

                    courseBuilder.build(request.getParameterMap(), course);

                    if (CourseService.updateCourse(course)){
                        notification = NotificationCreator.createFromProperty("info.db.update_success", locale);
                        CourseService.setAllCourses(request);
                        return pathManager.getString("path.page.admin.manager");
                    }

                } catch (BuildException e) {
                    notification = NotificationCreator.createFromProperty("add.invalid_form_data", Notification.Type.ERROR,  locale);

                } catch (ServiceLogicalException e) {
                    notification = new Notification(e.getMessage(), Notification.Type.ERROR);
                }
            } else {

                try {
                    course = CourseService.getCourseById(id);
                } catch (ServiceLogicalException e) {
                    notification = NotificationCreator.createFromProperty("error.db.no_such_record", Notification.Type.ERROR, locale);
                    return pathManager.getString("path.page.admin.manager");
                }
            }
        } catch (ServiceTechnicalException e) {
            throw new CommandException(e);
        } finally {
            if (notification != null){
                NotificationService.push(request.getSession(), notification);
            }

        }

        request.setAttribute("course", course);

        return pathManager.getString("path.page.admin.update_course");
    }
}
