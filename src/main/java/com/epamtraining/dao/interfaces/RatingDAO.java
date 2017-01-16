package com.epamtraining.dao.interfaces;

import com.epamtraining.entities.Rating;
import com.epamtraining.entities.Account;
import com.epamtraining.entities.Course;
import com.epamtraining.exception.DAOLogicalException;

import java.util.List;

/**
 * Interface for work Mark table
 * @author Sergey Bondarenko
 */
public interface RatingDAO {

    /**
     * Find all ratings
     * @return ratings list
     * @throws DAOLogicalException
     */
    List<Rating> findAll() throws DAOLogicalException;

    /**
     * Remove record from base by student id
     * @param courseId course id
     * @param studentId student id
     * @throws DAOLogicalException
     */
    boolean deleteForStudent(Integer courseId, Integer studentId) throws DAOLogicalException;

    /**
     * Find all ratings for one course
     * @param id
     * @return ratings list
     * @throws DAOLogicalException
     */
    List<Rating> findRatingsByCourse(Integer id) throws DAOLogicalException;

    /**
     * Get student rating by id
     * @param student - student
     * @return - student rating list
     * @throws DAOLogicalException
     */
    List<Rating> findRatingsByStudentId(Account student) throws DAOLogicalException;

    /**
     * Adding student rating
     * @param rating - student rating
     * @return success of operation
     * @throws DAOLogicalException
     */
    boolean addRating(Rating rating) throws DAOLogicalException;

    /**
     * Update student rating
     * @param rating
     * @return success of operation
     * @throws DAOLogicalException
     */
    boolean updateRating(Rating rating) throws DAOLogicalException;

    /**
     * Adding student to course
     * @param account - student account
     * @param courseId - course id
     * @return success of operation
     * @throws DAOLogicalException
     */
    boolean addStudentToCourse(Account account, Integer courseId) throws DAOLogicalException;

    /**
     * Student rating for course
     * @param student - student
     * @param course - course
     * @return rating
     * @throws DAOLogicalException
     */
    Rating findRatingByStudentCourse(Integer student, Integer course) throws DAOLogicalException;

    /**
     * Check course rating for empty
     * @param course
     * @return ratings status
     * @throws DAOLogicalException
     */
    boolean checkAllRatingsForCourse(Integer course) throws DAOLogicalException;
}
