package com.epamtraining.commands;

import com.epamtraining.entities.Account;
import com.epamtraining.exception.*;
import com.epamtraining.notification.Notification;
import com.epamtraining.notification.NotificationCreator;
import com.epamtraining.notification.NotificationService;
import com.epamtraining.resource.LocaleManager;
import com.epamtraining.services.AccountService;
import com.epamtraining.services.UserTypeService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Registration command
 * @author Sergey Bondarenko
 */
public class RegistrationCommand extends ActionCommand {

    private static final String NAME_PARAMETER = "name";
    private static final String SURNAME_PARAMETER = "surname";
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String CONFIRM_PASSWORD_PARAMETER = "check_password";

    private Logger logger = Logger.getRootLogger();

    /**
     * Everyone can register as student
     * @param account can be null
     * @return
     */
    @Override
    public boolean checkAccess(Account account) {
        return true;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Notification notification = null;
        Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
        Account newAccount = new Account();
        final String name = request.getParameter(NAME_PARAMETER);
        final String surname = request.getParameter(SURNAME_PARAMETER);
        final String login = request.getParameter(LOGIN_PARAMETER);
        final String password = request.getParameter(PASSWORD_PARAMETER);
        final String confirmPassword = request.getParameter(CONFIRM_PASSWORD_PARAMETER);

        newAccount.setName(name);
        newAccount.setSurname(surname);

        if (request.getParameter("submit") != null) {

            try {
                if (!AccountService.checkLogin(login)) {
                    newAccount.setLogin(login);

                    if (DigestUtils.md5Hex(password).equals(DigestUtils.md5Hex(confirmPassword))) {
                        newAccount.setPassword(DigestUtils.md5Hex(password));
                        newAccount.setUserType(UserTypeService.getStudentUserType());
                        if (AccountService.createNewUser(newAccount)) {
                            logger.info("Successful registration by login: " + login);
                            notification = NotificationCreator.createFromProperty("info.reg.success", locale);
                            return pathManager.getString("path.page.login");
                        }
                    } else {
                        notification = NotificationCreator.createFromProperty("error.reg.invalid_pass", Notification.Type.ERROR, locale);
                    }
                } else {
                    notification = NotificationCreator.createFromProperty("error.reg.invalid_login", Notification.Type.ERROR, locale);
                }
            } catch (ServiceLogicalException e) {
                logger.info("Registration fail by: " + name + " " + surname);
            } catch (ServiceTechnicalException e) {
                throw new CommandException(e);
            } finally {
                if (notification != null) {
                    NotificationService.push(request.getSession(), notification);
                }
            }
        }

        request.setAttribute("account", newAccount);

        return pathManager.getString("path.page.registration");
    }
}
