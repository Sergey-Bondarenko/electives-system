package com.epamtraining.services;

import com.epamtraining.dao.DaoFactory;
import com.epamtraining.entities.Account;
import com.epamtraining.exception.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Accounts services
 * @author Sergey Bondarenko
 */
public class AccountService {
    /**
     * Set all accounts for editing
     * @param request
     * @throws ServiceTechnicalException
     * @throws ServiceLogicalException
     */
    public static void setAllAccounts(HttpServletRequest request) throws ServiceTechnicalException, ServiceLogicalException {
        try {
            List<Account> accounts = DaoFactory.getDaoFactory().getAccountDao().findAll();
            request.setAttribute("accounts", accounts);
        } catch (DAOLogicalException e) {
            throw new ServiceLogicalException(e);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e);
        }
    }

    /**
     * Check user login in database
     * @param login user
     * @return login status
     * @throws ServiceLogicalException
     * @throws ServiceTechnicalException
     */
    public static boolean checkLogin(String login) throws ServiceLogicalException, ServiceTechnicalException {
        try {
            if (DaoFactory.getDaoFactory().getAccountDao().checkLogin(login))
                return true;
        } catch (DAOLogicalException e) {
            throw new ServiceLogicalException(e);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e);
        }
        return false;
    }

    public static void createNewUser(Account account) throws ServiceTechnicalException, ServiceLogicalException {
        try {
            DaoFactory.getDaoFactory().getAccountDao().create(account);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e);
        } catch (DAOLogicalException e) {
            throw new ServiceLogicalException(e);
        }
    }
}
