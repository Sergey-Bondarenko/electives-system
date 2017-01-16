package com.epamtraining.builder;

import com.epamtraining.dao.DaoFactory;
import com.epamtraining.entities.Account;
import com.epamtraining.entities.UserType;
import com.epamtraining.exception.BuildException;
import com.epamtraining.exception.DAOLogicalException;
import com.epamtraining.exception.DAOTechnicalException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Build an account entity from request
 * @author Sergey Bondarenko
 */
public class AccountBuilder extends EntityBuilder<Account> {

    private Logger logger = Logger.getRootLogger();
    @Override
    public Account build(Map<String, String[]> map) throws BuildException {
        Account account = new  Account();
        build(map, account);
        return account;
    }

    /**
     * Fill entity
     * @param map
     * @param account target account
     * @throws BuildException
     */
    public void build(Map<String, String[]> map, Account account) throws BuildException{
        boolean name = !buildName(map.get("name"), account);
        boolean surname = !buildSurname(map.get("surname"), account);
        boolean login = !buildLogin(map.get("login"), account);
        boolean userType = !buildUserType(map.get("userType"), account);

        if (name || surname || login || userType){
            throw new BuildException();
        }
    }

    /**
     * Build account name
     * @param args parameter values
     * @param account target account
     */
    private boolean buildName(String[] args, Account account) {
        if (args != null && args.length > 0){
            String name = args[0];
            if (name.length() > 0){
                try {
                    account.setName(new String(args[0].getBytes("ISO-8859-1"),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.error(e);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Build account surname
     * @param args parameter values
     * @param account target account
     */
    private boolean buildSurname(String[] args, Account account) {
        if (args != null && args.length > 0) {
            String surname = args[0];
            if (surname.length() > 0) {
                try {
                    account.setSurname(new String(args[0].getBytes("ISO-8859-1"),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.error(e);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Build account login
     * @param args parameter values
     * @param account target account
     */
    private boolean buildLogin(String[] args, Account account) {
        if (args != null && args.length > 0) {
            String login = args[0];
            if (login.length() > 0) {
                try {
                    account.setLogin(new String(args[0].getBytes("ISO-8859-1"),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.error(e);
                }
                return true;
            }
        }
        return false;
    }

    //**
    //* Build account password
    //* @param args parameter values
    //* @param builder account builder
    //*/
    //rivate boolean buildPassword(String[] args, Account.Builder builder) {
    //   if (args != null && args.length > 0) {
    //       String password = args[0];
    //       if (password.length() > 0) {
    //           try {
    //               builder.setPassword(DigestUtils.md5Hex(
    //                       new String(args[0].getBytes("ISO-8859-1"), "UTF-8")));
    //           } catch (UnsupportedEncodingException e) {
    //               logger.error(e);
    //           }
    //           return true;
    //       }
    //   }
    //   return false;
    //

    /**
     * Build account user type
     * @param args parameter values
     * @param account target account
     * @throws BuildException
     */
    private boolean buildUserType(String[] args, Account account) throws BuildException {
        if (args != null && args.length > 0 && args[0].length() > 0) {
            int userTypeId = Integer.parseInt(args[0]);
            UserType userType = null;
            try {
                userType = DaoFactory.getDaoFactory().getUserTypeDao().findEntityById(userTypeId);
            } catch (DAOLogicalException e) {
                throw new BuildException(e);
            } catch (DAOTechnicalException e) {
                throw new BuildException(e);
            }
            if (userType != null) {
                account.setUserType(userType);
                return true;
            }
        }
        return false;
    }
}
