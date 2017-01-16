package com.epamtraining.commands.student;

import com.epamtraining.commands.StudentCommand;
import com.epamtraining.dao.DaoFactory;
import com.epamtraining.dao.interfaces.RatingDAO;
import com.epamtraining.entities.Account;
import com.epamtraining.exception.*;
import com.epamtraining.services.AuthenticationService;
import com.epamtraining.notification.Notification;
import com.epamtraining.notification.NotificationCreator;
import com.epamtraining.notification.NotificationService;
import com.epamtraining.resource.LocaleManager;
import com.epamtraining.services.CoursesService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Student join course
 * @author Sergey Bondarenko
 */
public class ApplyCourseCommand extends StudentCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Notification notification = null;
        Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
        Account account = AuthenticationService.account(request);
        String courseId = request.getParameter("id");

        try {
            RatingDAO dao = DaoFactory.getDaoFactory().getRatingDao();
            if (dao.addStudentToCourse(account, Integer.parseInt(courseId))) {
                notification = NotificationCreator.createFromProperty("info.db.apply_course", locale);
                CoursesService.setCoursesWithRatings(request);
                return pathManager.getString("path.page.student.courses");
            }
        } catch (DAOLogicalException e) {
            notification = new Notification(e.getMessage(), Notification.Type.ERROR);
        } catch (DAOTechnicalException | ServiceLogicalException | ServiceTechnicalException e) {
            throw new CommandException(e);
        } finally {
            if (notification != null) {
                NotificationService.push(request.getSession(), notification);
            }
        }

        return pathManager.getString("path.page.student.courses");
    }
}
