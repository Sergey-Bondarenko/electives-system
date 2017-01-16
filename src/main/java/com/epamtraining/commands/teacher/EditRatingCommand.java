package com.epamtraining.commands.teacher;

import com.epamtraining.builder.RatingBuilder;
import com.epamtraining.commands.TeacherCommand;
import com.epamtraining.entities.Account;
import com.epamtraining.entities.Rating;
import com.epamtraining.exception.*;
import com.epamtraining.notification.Notification;
import com.epamtraining.notification.NotificationCreator;
import com.epamtraining.notification.NotificationService;
import com.epamtraining.resource.LocaleManager;
import com.epamtraining.services.AccountService;
import com.epamtraining.services.AuthenticationService;
import com.epamtraining.services.CourseService;
import com.epamtraining.services.RatingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Edit student rating command
 * @author Sergey Bondarenko
 */
public class EditRatingCommand extends TeacherCommand {
    /**
     * Perform editing rating
     * @param request request to read the command from
     * @param response
     * @return
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Notification notification = null;
        Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
        Account account = AuthenticationService.account(request);
        Rating rating;
        int cid, sid;

        try {
            CourseService.setInfoForCourse(request);
        } catch (ServiceLogicalException | ServiceTechnicalException e) {
            throw new CommandException(e);
        }

        try {
            try {
                cid = Integer.parseInt(request.getParameter("cid"));
                sid = Integer.parseInt(request.getParameter("sid"));

            } catch (NumberFormatException e){
                notification = NotificationCreator.createFromProperty("error.invalid_parameter", Notification.Type.ERROR, locale);
                return pathManager.getString("path.page.teacher.account");
            }

            if (request.getParameter("submit") != null){
                rating = new Rating();
                rating.setStudent(AccountService.getAccountById(sid));
                rating.setCourse(CourseService.getCourseById(cid));

                RatingBuilder ratingBuilder = new RatingBuilder();
                try {

                    ratingBuilder.build(request.getParameterMap(), rating);

                    if (RatingService.updateStudentRating(rating)){
                        notification = NotificationCreator.createFromProperty("info.db.update_success", locale);
                        CourseService.setCoursesForTeacher(request, account);
                        return pathManager.getString("path.page.teacher.account");
                    }

                } catch (BuildException e) {
                    notification = NotificationCreator.createFromProperty("add.invalid_form_data", Notification.Type.ERROR,  locale);

                } catch (ServiceLogicalException e) {
                    notification = new Notification(e.getMessage(), Notification.Type.ERROR);
                } catch (ServiceTechnicalException e) {
                    throw new CommandException(e);
                }
            } else {

                try {
                    rating = RatingService.getStudentRating(sid, cid);
                } catch (ServiceLogicalException e) {
                    notification = NotificationCreator.createFromProperty("error.db.no_such_record", Notification.Type.ERROR, locale);
                    return pathManager.getString("path.page.teacher.account");
                }
            }
        } catch (ServiceTechnicalException | ServiceLogicalException e) {
            throw new CommandException(e);
        } finally {
            if (notification != null){
                NotificationService.push(request.getSession(), notification);
            }

        }

        request.setAttribute("rating", rating);

        return pathManager.getString("path.page.teacher.update_rating");
    }
}
