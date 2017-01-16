package com.epamtraining.commands.admin;

import com.epamtraining.builder.AccountBuilder;
import com.epamtraining.commands.AdminCommand;
import com.epamtraining.dao.DaoFactory;
import com.epamtraining.dao.interfaces.AccountDAO;
import com.epamtraining.entities.Account;
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
            List<UserType> userTypes = DaoFactory.getDaoFactory().getUserTypeDao().findAll();
            request.setAttribute("usertypes", userTypes);
        } catch (DAOTechnicalException e) {
            throw new CommandException(e);
        } catch (DAOLogicalException e) {
            throw new CommandException(e);
        }
        if (request.getParameter("submit") != null) {

            AccountBuilder accountBuilder = new AccountBuilder();
            try {
                AccountDAO dao = DaoFactory.getDaoFactory().getAccountDao();

                accountBuilder.build(request.getParameterMap(), account);
                account.setPassword(DigestUtils.md5Hex(request.getParameter("password")));

                if (dao.create(account)) {
                    notification = NotificationCreator.createFromProperty("info.db.update_success", locale);
                    List<Account> accounts = dao.findAll();
                    request.setAttribute("accounts", accounts);
                    return pathManager.getString("path.page.admin.accounts");
                }
            } catch (BuildException e) {
                notification = NotificationCreator.createFromProperty("add.invalid_form_data", Notification.Type.ERROR,  locale);
            } catch (DAOLogicalException e) {
                notification = new Notification(e.getMessage(), Notification.Type.ERROR);
            } catch (DAOTechnicalException e) {
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
