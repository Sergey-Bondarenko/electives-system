package com.epamtraining.commands.teacher;

import com.epamtraining.commands.TeacherCommand;
import com.epamtraining.entities.Account;
import com.epamtraining.exception.*;
import com.epamtraining.notification.Notification;
import com.epamtraining.notification.NotificationCreator;
import com.epamtraining.notification.NotificationService;
import com.epamtraining.resource.LocaleManager;
import com.epamtraining.services.AuthenticationService;
import com.epamtraining.services.CourseService;
import com.epamtraining.services.RatingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Complete course teacher command
 * @author Sergey Bondarenko
 */
public class CompleteCourseCommand extends TeacherCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Notification notification = null;
        Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
        Account account = AuthenticationService.account(request);
        Integer courseId = Integer.parseInt(request.getParameter("cid"));

        try {
            if (RatingService.isCourseEmpty(courseId)) {
                CourseService.setCourseStatus(courseId);
                notification = NotificationCreator.createFromProperty("info.db.course_ended", locale);
                CourseService.setCoursesForTeacher(request, account);
                return pathManager.getString("path.page.teacher.account");
            } else {
                notification = NotificationCreator.createFromProperty("info.db.course_ended_error", locale);
                CourseService.setCoursesForTeacher(request, account);
                return pathManager.getString("path.page.teacher.account");
            }
        } catch (ServiceTechnicalException e) {
            notification = new Notification(e.getMessage(), Notification.Type.ERROR);
        } catch (ServiceLogicalException e) {
            throw new CommandException(e);
        } finally {
            if (notification != null) {
                NotificationService.push(request.getSession(), notification);
            }
        }

        return pathManager.getString("path.page.teacher.account");
    }
}
