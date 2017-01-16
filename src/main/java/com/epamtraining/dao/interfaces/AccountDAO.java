package com.epamtraining.dao.interfaces;

import com.epamtraining.entities.Account;
import com.epamtraining.entities.Course;
import com.epamtraining.entities.UserType;
import com.epamtraining.exception.DAOLogicalException;

import java.util.List;


/**
 * Interface for working with Account entity
 *
 * @author Sergey Bondarenko
 * @see Account
 * @see Course
 * @see UserType
 */
public interface AccountDAO extends DaoBase<Account, Integer> {
   /**
    * Find account by login and password
    * @param login
    * @param password
    * @return
    * @throws DAOLogicalException
    */
   Account findByLoginAndPassword(String login, String password) throws DAOLogicalException;

   /**
    * Find all teachers
    * @return teacher list
    * @throws DAOLogicalException
    */
   List<Account> findAllTeachers() throws DAOLogicalException;

   /**
    * Update account without password
    * @param account
    * @return
    * @throws DAOLogicalException
    */
   boolean updateWithoutPassword(Account account) throws DAOLogicalException;

   /**
    * Check login for existing in database
    * @param login
    * @return
    * @throws DAOLogicalException
    */
   boolean checkLogin(String login) throws DAOLogicalException;
}
