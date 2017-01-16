package com.epamtraining.commands.student;

import com.epamtraining.commands.StudentCommand;
import com.epamtraining.dao.DaoFactory;
import com.epamtraining.dao.interfaces.RatingDAO;
import com.epamtraining.entities.Account;
import com.epamtraining.entities.Course;
import com.epamtraining.exception.CommandException;
import com.epamtraining.exception.DAOLogicalException;
import com.epamtraining.exception.DAOTechnicalException;
import com.epamtraining.services.AuthenticationService;
import com.epamtraining.notification.Notification;
import com.epamtraining.notification.NotificationCreator;
import com.epamtraining.notification.NotificationService;
import com.epamtraining.resource.LocaleManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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

        int cid = Integer.parseInt(request.getParameter("cid"));

        try {
                RatingDAO dao = DaoFactory.getDaoFactory().getRatingDao();
                if (dao.deleteForStudent(cid, student.getId())) {
                    List<Course> courses = DaoFactory.getDaoFactory().getCourseDao().findCoursesForStudent(student);
                    request.setAttribute("courses", courses);
                    notification = NotificationCreator.createFromProperty("info.db.leave_course", locale);
                }
            } catch (NumberFormatException e) {
                notification = NotificationCreator.createFromProperty("error.invalid_parameter", Notification.Type.ERROR,locale);
            } catch (DAOTechnicalException e) {
                throw new CommandException(e);
            } catch (DAOLogicalException e) {
                notification = NotificationCreator.createFromProperty("error.db.no_such_record", Notification.Type.ERROR, locale);
            } finally {
                if (notification != null) {
                    NotificationService.push(request.getSession(), notification);
                }
            }
        return pathManager.getString("path.page.student.account");
    }
}
