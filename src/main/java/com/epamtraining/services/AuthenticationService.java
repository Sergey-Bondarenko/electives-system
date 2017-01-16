package com.epamtraining.services;

import com.epamtraining.dao.DaoFactory;
import com.epamtraining.dao.interfaces.AccountDAO;
import com.epamtraining.entities.Account;
import com.epamtraining.exception.ServiceLogicalException;
import com.epamtraining.exception.ServiceTechnicalException;
import com.epamtraining.exception.DAOLogicalException;
import com.epamtraining.exception.DAOTechnicalException;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Performs authentication
 * @author Sergey Bondarenko
 */
public class AuthenticationService {
    public static final String SESSION_VAR = "_user";

    /**
     * Authenticate user
     * @param login
     * @param password
     * @throws ServiceLogicalException
     */
    public static Account authenticate(String login, String password) throws ServiceLogicalException, ServiceTechnicalException {
        if (login != null && password != null) {
            String hash = DigestUtils.md5Hex(password);
            try {
                AccountDAO dao = DaoFactory.getDaoFactory().getAccountDao();
                Account account = dao.findByLoginAndPassword(login, hash);
                return account;
            } catch (DAOLogicalException e) {
                throw new ServiceLogicalException(e);
            } catch (DAOTechnicalException e) {
                throw new ServiceTechnicalException(e);
            }
        }
        return null;
    }

    /**
     * check if user is logged in to the system
     * @param request
     * @return
     */
    public static boolean isLoggedIn(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        return  (session.getAttribute(SESSION_VAR) != null);
    }

    /**
     * perform logout
     * @param session
     */
    public static void logout(HttpSession session){
        session.invalidate();
    }

    /**
     * Get account from session
     * @param request
     * @return
     */
    public static Account account(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object ob = session.getAttribute(SESSION_VAR);
        return (ob != null && ob.getClass().equals(Account.class)) ? (Account) ob : null;
    }
}
