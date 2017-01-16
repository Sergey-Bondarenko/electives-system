package com.epamtraining.commands.admin;

import com.epamtraining.builder.AccountBuilder;
import com.epamtraining.commands.AdminCommand;
import com.epamtraining.dao.DaoFactory;
import com.epamtraining.dao.interfaces.AccountDAO;
import com.epamtraining.entities.Account;
import com.epamtraining.entities.Rating;
import com.epamtraining.entities.UserType;
import com.epamtraining.exception.BuildException;
import com.epamtraining.exception.CommandException;
import com.epamtraining.exception.DAOLogicalException;
import com.epamtraining.exception.DAOTechnicalException;
import com.epamtraining.notification.Notification;
import com.epamtraining.notification.NotificationCreator;
import com.epamtraining.notification.NotificationService;
import com.epamtraining.resource.LocaleManager;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

/**
 * Edit account
 * @author Sergey Bondarenko
 */
public class EditAccountCommand extends AdminCommand {
    /**
     * Perform editing account command
     * @param request request to read the command from
     * @param response
     * @return
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Notification notification = null;
        Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
        Account account = new Account();
        List<UserType> userTypes;
        int id;

        try {
            userTypes = DaoFactory.getDaoFactory().getUserTypeDao().findAll();
            request.setAttribute("usertypes", userTypes);
        } catch (DAOTechnicalException e) {
            throw new CommandException(e);
        } catch (DAOLogicalException e) {
            throw new CommandException(e);
        }

        try {
            AccountDAO dao = DaoFactory.getDaoFactory().getAccountDao();

            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch (NumberFormatException e) {
                notification = NotificationCreator.createFromProperty("error.invalid_parameter", Notification.Type.ERROR, locale);
                return pathManager.getString("path.page.admin.accounts");
            }

            if (request.getParameter("submit") != null) {
                account.setId(id);

                AccountBuilder accountBuilder = new AccountBuilder();
                try {

                    accountBuilder.build(request.getParameterMap(), account);

                    if (!request.getParameter("password").equals("")) {
                        account.setPassword(DigestUtils.md5Hex(request.getParameter("password")));
                        dao.update(account);
                    } else {
                        dao.updateWithoutPassword(account);
                    }
                    notification = NotificationCreator.createFromProperty("info.db.update_success", locale);
                    List<Account> accounts = dao.findAll();
                    request.setAttribute("accounts", accounts);
                    return pathManager.getString("path.page.admin.accounts");
                } catch (BuildException e) {
                    notification = NotificationCreator.createFromProperty("add.invalid_form_data", Notification.Type.ERROR,  locale);
                } catch (DAOLogicalException e) {
                    notification = new Notification(e.getMessage(), Notification.Type.ERROR);
                }

            } else {
                try {
                    account = dao.findEntityById(id);
                } catch (DAOLogicalException e) {
                    notification = NotificationCreator.createFromProperty("error.db.no_such_record", Notification.Type.ERROR, locale);
                    return pathManager.getString("path.page.admin.accounts");
                }
            }
        } catch (DAOTechnicalException e) {
            throw new CommandException(e);
        } finally {
            if (notification != null){
                NotificationService.push(request.getSession(), notification);
            }
        }

        request.setAttribute("account", account);

        return pathManager.getString("path.page.admin.update_account");
    }
}
