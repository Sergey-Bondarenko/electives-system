package com.epamtraining.commands;

import com.epamtraining.entities.Account;
import com.epamtraining.entities.UserType;
import com.epamtraining.exception.*;
import com.epamtraining.services.AuthenticationService;
import com.epamtraining.notification.Notification;
import com.epamtraining.notification.NotificationCreator;
import com.epamtraining.notification.NotificationService;
import com.epamtraining.resource.LocaleManager;
import com.epamtraining.services.CourseService;
import com.epamtraining.services.UserTypeService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Login command
 * @author Sergey Bondarenko
 */
public class LoginCommand extends ActionCommand{

    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";

    private Logger logger = Logger.getRootLogger();

    /**
     * Everyone allowed to login
     * @param account can be null
     * @return
     */
    @Override
    public boolean checkAccess(Account account) {
        return true;
    }


    /**
     * Execute login command
     * @param request request to read the command from
     * @param response
     * @return
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Notification notification = null;
        Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
        final String login = request.getParameter(LOGIN_PARAMETER);
        final String password = request.getParameter(PASSWORD_PARAMETER);

        if (login != null && password != null) {

            try {
                Account account = AuthenticationService.authenticate(login, password);
                UserType admin = UserTypeService.getAdminUserType();
                UserType teacher = UserTypeService.getTeacherUserType();
                UserType student = UserTypeService.getStudentUserType();

                HttpSession session = request.getSession();
                session.setAttribute(AuthenticationService.SESSION_VAR, account);

                logger.info("Successful authentication by login: " + login);
                notification = NotificationCreator.createFromProperty("info.auth.success", locale);

                if (account.getUserType().equals(admin)) {
                    CourseService.setAllCourses(request);
                    return pathManager.getString("path.page.admin.manager");

                } else if (account.getUserType().equals(teacher)) {
                    CourseService.setCoursesForTeacher(request, account);
                    return pathManager.getString("path.page.teacher.account");

                } else if (account.getUserType().equals(student)) {
                    CourseService.setCoursesWithRatingsForStudent(request, account);
                    return pathManager.getString("path.page.student.account");
                }


            } catch (ServiceTechnicalException e) {
                throw new CommandException(e);
            } catch (ServiceLogicalException e) {
                logger.info("Authentication fail by login: " + login);
                notification = NotificationCreator.createFromProperty("error.auth.invalid_login_pass", Notification.Type.ERROR, locale);

            } finally {
                if (notification != null){
                    NotificationService.push(request.getSession(), notification);
                }
            }
        }
        return pathManager.getString("path.page.login");
    }
}
