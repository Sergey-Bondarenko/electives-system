package com.epamtraining.commands.student;

import com.epamtraining.commands.StudentCommand;
import com.epamtraining.entities.Account;
import com.epamtraining.exception.*;
import com.epamtraining.services.AuthenticationService;
import com.epamtraining.notification.Notification;
import com.epamtraining.notification.NotificationCreator;
import com.epamtraining.notification.NotificationService;
import com.epamtraining.resource.LocaleManager;
import com.epamtraining.services.CourseService;
import com.epamtraining.services.RatingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Allows student to leave course.
 * @author Sergey Bondarenko
 */
public class LeaveCourseCommand extends StudentCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Account student = AuthenticationService.account(request);
        Notification notification = null;
        Locale locale = LocaleManager.INSTANCE.resolveLocale(request);

        Integer cid = Integer.parseInt(request.getParameter("cid"));

        try {
                if (RatingService.removeStudentFromCourse(cid, student.getId())) {
                    CourseService.setCoursesWithRatingsForStudent(request, student);
                    notification = NotificationCreator.createFromProperty("info.db.leave_course", locale);
                }
            } catch (NumberFormatException e) {
                notification = NotificationCreator.createFromProperty("error.invalid_parameter", Notification.Type.ERROR,locale);
            } catch (ServiceLogicalException e) {
                notification = NotificationCreator.createFromProperty("error.db.no_such_record", Notification.Type.ERROR, locale);
            } catch (ServiceTechnicalException e) {
                throw new CommandException(e);
            } finally {
                if (notification != null) {
                    NotificationService.push(request.getSession(), notification);
                }
            }
        return pathManager.getString("path.page.student.account");
    }
}
