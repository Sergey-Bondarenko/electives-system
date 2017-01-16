package com.epamtraining.services;

import com.epamtraining.dao.DaoFactory;
import com.epamtraining.dao.interfaces.UserTypeDAO;
import com.epamtraining.entities.UserType;
import com.epamtraining.exception.DAOLogicalException;
import com.epamtraining.exception.DAOTechnicalException;
import com.epamtraining.exception.ServiceLogicalException;
import com.epamtraining.exception.ServiceTechnicalException;

/**
 * Created by qopy on 16-Jan-17.
 */
public class UserTypeService {
    /**
     * Get student user type from database
     * @return student user type
     * @throws ServiceLogicalException
     * @throws ServiceTechnicalException
     */
    public static UserType getStudentUserType() throws ServiceLogicalException, ServiceTechnicalException {
        UserType student;

        try {
            UserTypeDAO dao = DaoFactory.getDaoFactory().getUserTypeDao();
            student = dao.findEntityById(3);
        } catch (DAOLogicalException e) {
            throw new ServiceLogicalException(e);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e);
        }
        return student;
    }

    /**
     * Get teacher user type from database
     * @return teacher user type
     * @throws ServiceLogicalException
     * @throws ServiceTechnicalException
     */
    public static UserType getTeacherUserType() throws ServiceLogicalException, ServiceTechnicalException {
        UserType teacher;

        try {
            UserTypeDAO dao = DaoFactory.getDaoFactory().getUserTypeDao();
            teacher = dao.findEntityById(2);
        } catch (DAOLogicalException e) {
            throw new ServiceLogicalException(e);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e);
        }
        return teacher;
    }

    /**
     * Get admin user type from database
     * @return admin user type
     * @throws ServiceLogicalException
     * @throws ServiceTechnicalException
     */
    public static UserType getAdminUserType() throws ServiceLogicalException, ServiceTechnicalException {
        UserType admin;

        try {
            UserTypeDAO dao = DaoFactory.getDaoFactory().getUserTypeDao();
            admin = dao.findEntityById(1);
        } catch (DAOLogicalException e) {
            throw new ServiceLogicalException(e);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e);
        }
        return admin;
    }
}
