package com.epamtraining.commands.teacher;

import com.epamtraining.commands.TeacherCommand;
import com.epamtraining.dao.DaoFactory;
import com.epamtraining.dao.interfaces.CourseDAO;
import com.epamtraining.dao.interfaces.RatingDAO;
import com.epamtraining.entities.Account;
import com.epamtraining.exception.*;
import com.epamtraining.notification.Notification;
import com.epamtraining.notification.NotificationCreator;
import com.epamtraining.notification.NotificationService;
import com.epamtraining.resource.LocaleManager;
import com.epamtraining.services.AuthenticationService;
import com.epamtraining.services.CoursesService;

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
            RatingDAO ratingDao = DaoFactory.getDaoFactory().getRatingDao();
            CourseDAO courseDAO = DaoFactory.getDaoFactory().getCourseDao();
            if (ratingDao.checkAllRatingsForCourse(courseId)) {
                courseDAO.updateCourseStatus(courseId);
                notification = NotificationCreator.createFromProperty("info.db.course_ended", locale);
                CoursesService.setCoursesForTeacher(request, account);
                return pathManager.getString("path.page.teacher.account");
            } else {
                notification = NotificationCreator.createFromProperty("info.db.course_ended_error", locale);
                CoursesService.setCoursesForTeacher(request, account);
                return pathManager.getString("path.page.teacher.account");
            }
        } catch (DAOTechnicalException e) {
            notification = new Notification(e.getMessage(), Notification.Type.ERROR);
        } catch (DAOLogicalException | ServiceLogicalException | ServiceTechnicalException e) {
            throw new CommandException(e);
        } finally {
            if (notification != null) {
                NotificationService.push(request.getSession(), notification);
            }
        }

        return pathManager.getString("path.page.teacher.account");
    }
}
