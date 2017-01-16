/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epamtraining.dao.implementations;

import com.epamtraining.dao.interfaces.UserTypeDAO;
import com.epamtraining.connection.DataSource;
import com.epamtraining.entities.UserType;
import com.epamtraining.exception.DAOLogicalException;

import java.util.List;

/**
 *
 * @author Sergey Bondarenko
 */
public class UserTypeDAOImpl extends AbstractDAOImpl implements UserTypeDAO {

    public UserTypeDAOImpl(final DataSource userConn) {
        super(userConn);
    }

    @Override
    public List<UserType> findAll() throws DAOLogicalException {
            return findAll(set ->  new UserType(set.getInt("ID"), set.getString("USER_TYPE")),
                "SELECT * FROM USER_TYPE");
    }

    @Override
    public UserType findEntityById(Integer id) throws DAOLogicalException {
            return findFirst(set -> new UserType(set.getInt("ID"),set.getString("USER_TYPE")),
                "SELECT * FROM USER_TYPE WHERE ID = ?", id);

    }

    @Override
    public boolean update(UserType entity) throws DAOLogicalException {
            return sqlUpdate("UPDATE USER_TYPE SET USER_TYPE = ? WHERE ID = ?",
                    entity.getUserType(), entity.getId());
    }

    @Override
    public boolean delete(Integer id) throws DAOLogicalException {
            return sqlUpdate("DELETE FROM USER_TYPE WHERE ID = ?", id);
    }

    @Override
    public boolean create(UserType entity) throws DAOLogicalException {
            return sqlUpdate("INSERT INTO USER_TYPE (ID, USER_TYPE) VALUES ( ?, ? )",
                    entity.getId(), entity.getUserType());
    }
}
