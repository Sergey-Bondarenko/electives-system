package com.epamtraining.commands.admin;

import com.epamtraining.commands.AdminCommand;
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
 * Delete course command
 * @author Sergey Bondarenko
 */
public class DeleteCourseCommand extends AdminCommand {
    /**
     * Execute deleting course from database
     * @param request request to read the command from
     * @param response
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String param = request.getParameter("cid");
        if(param != null){
            Notification notification = null;
            Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
            try {
                int id = Integer.parseInt(param);
                if (CourseService.deleteCourse(id)) {
                    notification = NotificationCreator.createFromProperty("info.db.delete_success", locale);
                }
            } catch (NumberFormatException e) {
                notification = NotificationCreator.createFromProperty("error.invalid_parameter", Notification.Type.ERROR,locale);
            } catch (ServiceTechnicalException e) {
                throw new CommandException(e);
            } catch (ServiceLogicalException e) {
                notification = NotificationCreator.createFromProperty("error.db.not_empty", Notification.Type.ERROR, locale);
            } finally {
                if (notification != null) {
                    NotificationService.push(request.getSession(), notification);
                }
            }
        }
        try {
            CourseService.setAllCourses(request);
        } catch (ServiceTechnicalException | ServiceLogicalException e) {
            throw new CommandException(e);
        }

        return pathManager.getString("path.page.admin.manager");
    }
}
