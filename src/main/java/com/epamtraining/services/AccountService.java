package com.epamtraining.services;

import com.epamtraining.dao.DaoFactory;
import com.epamtraining.dao.interfaces.AccountDAO;
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

    /**
     * Create new user in database
     * @param account new user
     * @throws ServiceTechnicalException
     * @throws ServiceLogicalException
     */
    public static boolean createNewUser(Account account) throws ServiceTechnicalException, ServiceLogicalException {
        try {
            if (DaoFactory.getDaoFactory().getAccountDao().create(account)) {
                return true;
            }
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e);
        } catch (DAOLogicalException e) {
            throw new ServiceLogicalException(e);
        }
        return false;
    }

    /**
     * Update user in database
     * @param account new user
     * @throws ServiceTechnicalException
     * @throws ServiceLogicalException
     */
    public static boolean updateUser(Account account) throws ServiceTechnicalException, ServiceLogicalException {
        try {
            if (DaoFactory.getDaoFactory().getAccountDao().update(account)) {
                return true;
            }
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e);
        } catch (DAOLogicalException e) {
            throw new ServiceLogicalException(e);
        }
        return false;
    }

    /**
     * Update user in database without password
     * @param account new user
     * @throws ServiceTechnicalException
     * @throws ServiceLogicalException
     */
    public static boolean updateUserWithoutPassword(Account account) throws ServiceTechnicalException, ServiceLogicalException {
        try {
            if (DaoFactory.getDaoFactory().getAccountDao().updateWithoutPassword(account)) {
                return true;
            }
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e);
        } catch (DAOLogicalException e) {
            throw new ServiceLogicalException(e);
        }
        return false;
    }

    /**
     * Get account by id
     * @param id account id
     * @return account object
     * @throws ServiceTechnicalException
     * @throws ServiceLogicalException
     */
    public static Account getAccountById(Integer id) throws ServiceTechnicalException, ServiceLogicalException {
        Account account;
        try {
            account = DaoFactory.getDaoFactory().getAccountDao().findEntityById(id);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e);
        } catch (DAOLogicalException e) {
            throw new ServiceLogicalException(e);
        }
        return account;
    }

    /**
     * Delete user from database
     * @param account target user
     * @return success operation
     * @throws ServiceTechnicalException
     * @throws ServiceLogicalException
     */
    public static boolean deleteUser(Integer account) throws ServiceTechnicalException, ServiceLogicalException {
        try {
            AccountDAO dao = DaoFactory.getDaoFactory().getAccountDao();
            if (dao.delete(account)) {
                return true;
            }
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e);
        } catch (DAOLogicalException e) {
            throw new ServiceLogicalException(e);
        }
        return false;
    }
}
