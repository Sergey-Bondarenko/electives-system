package com.epamtraining.commands.admin;

import com.epamtraining.commands.AdminCommand;
import com.epamtraining.dao.DaoFactory;
import com.epamtraining.dao.interfaces.AccountDAO;
import com.epamtraining.dao.interfaces.CourseDAO;
import com.epamtraining.entities.Account;
import com.epamtraining.entities.Course;
import com.epamtraining.exception.CommandException;
import com.epamtraining.exception.DAOLogicalException;
import com.epamtraining.exception.DAOTechnicalException;
import com.epamtraining.notification.Notification;
import com.epamtraining.notification.NotificationCreator;
import com.epamtraining.notification.NotificationService;
import com.epamtraining.resource.LocaleManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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
                AccountDAO dao = DaoFactory.getDaoFactory().getAccountDao();
                if (dao.delete(id)) {
                    List<Account> accounts = dao.findAll();
                    request.setAttribute("accounts", accounts);
                    notification = NotificationCreator.createFromProperty("info.db.delete_success", locale);
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
        }

        return pathManager.getString("path.page.admin.accounts");
    }
}
