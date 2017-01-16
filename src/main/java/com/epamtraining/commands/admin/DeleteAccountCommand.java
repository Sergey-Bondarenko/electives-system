package com.epamtraining.commands.admin;

import com.epamtraining.commands.AdminCommand;
import com.epamtraining.exception.*;
import com.epamtraining.notification.Notification;
import com.epamtraining.notification.NotificationCreator;
import com.epamtraining.notification.NotificationService;
import com.epamtraining.resource.LocaleManager;
import com.epamtraining.services.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Delete account command
 * @author Sergey Bondarenko
 */
public class DeleteAccountCommand extends AdminCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String param = request.getParameter("id");
        if(param != null){
            Notification notification = null;
            Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
            try {
                int id = Integer.parseInt(param);
                if (AccountService.deleteUser(id)) {
                    AccountService.setAllAccounts(request);
                    notification = NotificationCreator.createFromProperty("info.db.delete_success", locale);
                }
            } catch (NumberFormatException e) {
                notification = NotificationCreator.createFromProperty("error.invalid_parameter", Notification.Type.ERROR,locale);
            } catch (ServiceTechnicalException e) {
                throw new CommandException(e);
            } catch (ServiceLogicalException e) {
                notification = NotificationCreator.createFromProperty("error.db.no_such_record", Notification.Type.ERROR, locale);
            } finally {
                if (notification != null) {
                    NotificationService.push(request.getSession(), notification);
                }
            }
        }

        return pathManager.getString("path.page.admin.accounts");
    }
}
