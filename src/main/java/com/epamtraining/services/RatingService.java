package com.epamtraining.services;

import com.epamtraining.dao.DaoFactory;
import com.epamtraining.dao.interfaces.RatingDAO;
import com.epamtraining.entities.Account;
import com.epamtraining.entities.Rating;
import com.epamtraining.exception.DAOLogicalException;
import com.epamtraining.exception.DAOTechnicalException;
import com.epamtraining.exception.ServiceLogicalException;
import com.epamtraining.exception.ServiceTechnicalException;

/**
 * Service for working with Ratings
 * @author Sergey Bondarenko
 */
public class RatingService {
    /**
     * Remove student from course by id
     * @param courseId course id
     * @param studentId student id
     * @throws ServiceLogicalException
     * @throws ServiceTechnicalException
     */
    public static boolean removeStudentFromCourse(Integer courseId, Integer studentId) throws ServiceLogicalException, ServiceTechnicalException {
        try {
            RatingDAO dao = DaoFactory.getDaoFactory().getRatingDao();
            if (dao.deleteForStudent(courseId, studentId)) {
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
     * Add student to course
     * @param courseId course id
     * @param student student account
     * @throws ServiceTechnicalException
     * @throws ServiceLogicalException
     */
    public static boolean addStudentToCourse(Integer courseId, Account student) throws ServiceTechnicalException, ServiceLogicalException {
        try {
            RatingDAO dao = DaoFactory.getDaoFactory().getRatingDao();
            if (dao.addStudentToCourse(student, courseId)) {
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
     * Check if course doesn't have any student
     * @param courseId course id
     * @throws ServiceLogicalException
     * @throws ServiceTechnicalException
     */
    public static boolean isCourseEmpty(Integer courseId) throws ServiceLogicalException, ServiceTechnicalException {
        try {
            RatingDAO ratingDao = DaoFactory.getDaoFactory().getRatingDao();
            if (ratingDao.checkAllRatingsForCourse(courseId)) {
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
     * Update student rating
     * @param rating student rating
     * @throws ServiceLogicalException
     * @throws ServiceTechnicalException
     */
    public static boolean updateStudentRating(Rating rating) throws ServiceLogicalException, ServiceTechnicalException {
        try {
            RatingDAO ratingDao = DaoFactory.getDaoFactory().getRatingDao();
            if (ratingDao.updateRating(rating)) {
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
     * Get student ratings of course by id
     * @param studentId
     * @param courseId
     * @return rating
     * @throws ServiceTechnicalException
     * @throws ServiceLogicalException
     */
    public static Rating getStudentRating(Integer studentId, Integer courseId) throws ServiceTechnicalException, ServiceLogicalException {
        Rating rating;
        try {
            RatingDAO ratingDao = DaoFactory.getDaoFactory().getRatingDao();
            rating = ratingDao.findRatingByStudentCourse(studentId, courseId);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e);
        } catch (DAOLogicalException e) {
            throw new ServiceLogicalException(e);
        }
        return rating;
    }
}
