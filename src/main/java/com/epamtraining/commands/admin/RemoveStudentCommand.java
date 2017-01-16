package com.epamtraining.commands.admin;

import com.epamtraining.commands.AdminCommand;
import com.epamtraining.dao.DaoFactory;
import com.epamtraining.dao.interfaces.RatingDAO;
import com.epamtraining.entities.Course;
import com.epamtraining.exception.*;
import com.epamtraining.notification.Notification;
import com.epamtraining.notification.NotificationCreator;
import com.epamtraining.notification.NotificationService;
import com.epamtraining.resource.LocaleManager;
import com.epamtraining.services.CoursesService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Remove student from course command
 * @author Sergey Bondarenko
 */
public class RemoveStudentCommand extends AdminCommand {
    /**
     *
     * @param request request to read the command from
     * @param response
     * @return
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String studentParam = request.getParameter("sid");
        String courseParam = request.getParameter("cid");
        Course course = null;
        if (courseParam != null || studentParam != null) {
            Notification notification = null;
            Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
            try {
                int sid = Integer.parseInt(studentParam);
                int cid = Integer.parseInt(courseParam);
                RatingDAO dao = DaoFactory.getDaoFactory().getRatingDao();
                if (dao.deleteForStudent(cid, sid)) {
                    CoursesService.setInfoForCourse(request);
                    course = DaoFactory.getDaoFactory().getCourseDao().findEntityById(cid);

                    notification = NotificationCreator.createFromProperty("info.db.delete_success", locale);
                }
            } catch (ServiceTechnicalException | DAOTechnicalException | ServiceLogicalException e) {
                throw new CommandException(e);
            } catch (DAOLogicalException e) {
                notification = NotificationCreator.createFromProperty("error.db.no_such_record", Notification.Type.ERROR, locale);
            } finally {
                if (notification != null) {
                    NotificationService.push(request.getSession(), notification);
                }
            }
        }
        request.setAttribute("course", course);
        return pathManager.getString("path.page.admin.update_course");
    }
}
