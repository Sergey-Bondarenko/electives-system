package com.epamtraining.commands.admin;

import com.epamtraining.builder.AccountBuilder;
import com.epamtraining.commands.AdminCommand;
import com.epamtraining.entities.Account;
import com.epamtraining.exception.*;
import com.epamtraining.notification.Notification;
import com.epamtraining.notification.NotificationCreator;
import com.epamtraining.notification.NotificationService;
import com.epamtraining.resource.LocaleManager;
import com.epamtraining.services.AccountService;
import com.epamtraining.services.UserTypeService;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Create new account command
 * @author Sergey Bondarenko
 */
public class AddAccountCommand extends AdminCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Notification notification = null;
        Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
        Account account = new Account();

        try {
            UserTypeService.setupAllUserTypes(request);
        } catch (ServiceTechnicalException | ServiceLogicalException e) {
            throw new CommandException(e);
        }
        if (request.getParameter("submit") != null) {

            AccountBuilder accountBuilder = new AccountBuilder();
            try {

                accountBuilder.build(request.getParameterMap(), account);
                account.setPassword(DigestUtils.md5Hex(request.getParameter("password")));

                if (AccountService.createNewUser(account)) {
                    notification = NotificationCreator.createFromProperty("info.db.update_success", locale);
                    AccountService.setAllAccounts(request);
                    return pathManager.getString("path.page.admin.accounts");
                }
            } catch (BuildException e) {
                notification = NotificationCreator.createFromProperty("add.invalid_form_data", Notification.Type.ERROR,  locale);
            } catch (ServiceLogicalException e) {
                notification = new Notification(e.getMessage(), Notification.Type.ERROR);
            } catch (ServiceTechnicalException e) {
                throw new CommandException(e);
            } finally {
                if (notification != null) {
                    NotificationService.push(request.getSession(), notification);
                }
            }
        }

        request.setAttribute("account", account);

        return pathManager.getString("path.page.admin.add_account");
    }
}
