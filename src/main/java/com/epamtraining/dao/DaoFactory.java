package com.epamtraining.dao;

import com.epamtraining.connection.DataSource;
import com.epamtraining.dao.interfaces.*;
import com.epamtraining.exception.ConnectionPoolException;
import com.epamtraining.exception.DAOTechnicalException;

import java.sql.Connection;

public abstract class DaoFactory {

    public static DaoFactory getDaoFactory() throws DAOTechnicalException {
        try {
            return new DataSource();
        } catch (ConnectionPoolException e) {
            throw new DAOTechnicalException(e);
        }
    }

    public abstract Connection getConnection();

    public abstract StatusDAO getStatusDao();

    public abstract UserTypeDAO getUserTypeDao();

    public abstract CourseDAO getCourseDao();

    public abstract AccountDAO getAccountDao();

    public abstract RatingDAO getRatingDao();

}
