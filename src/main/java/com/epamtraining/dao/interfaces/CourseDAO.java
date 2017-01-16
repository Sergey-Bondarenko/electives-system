package com.epamtraining.dao.interfaces;

import com.epamtraining.entities.Account;
import com.epamtraining.entities.Course;
import com.epamtraining.exception.DAOLogicalException;

import java.util.List;

/**
 * Interface for working with Course entity
 * @author Sergey Bondarenko
 */
public interface CourseDAO extends DaoBase<Course, Integer> {
    /**
     * Find courses for teacher
     * @param account teacher
     * @return courses list
     * @throws DAOLogicalException
     */
    List<Course> findCoursesForTeacher(Account account) throws DAOLogicalException;

    /**
     * Find courses for student
     * @return courses list
     * @throws DAOLogicalException
     */
    List<Course> findCoursesForStudent(Account account) throws DAOLogicalException;

    /**
     * Find open and started courses
     * @return courses list
     * @throws DAOLogicalException
     */
    List<Course> findActiveCourses() throws DAOLogicalException;

    /**
     * Find all archived courses
     * @return courses list
     * @throws DAOLogicalException
     */
    List<Course> findArchivedCourses() throws DAOLogicalException;

    /**
     * Update course status to "ended"
     * @param course
     * @return success operation
     * @throws DAOLogicalException
     */
    boolean updateCourseStatus(Integer course) throws DAOLogicalException;
}
