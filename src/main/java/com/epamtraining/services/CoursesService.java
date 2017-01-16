package com.epamtraining.services;

import com.epamtraining.dao.DaoFactory;
import com.epamtraining.entities.Account;
import com.epamtraining.entities.Course;
import com.epamtraining.entities.Rating;
import com.epamtraining.entities.Status;
import com.epamtraining.exception.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Service for getting courses
 * @author Sergey Bondarenko
 */
public class CoursesService {

    /**
     * Fills all courses with rating list
     * @throws ServiceLogicalException
     * @throws ServiceTechnicalException
     */
    public static void setAllCourses(HttpServletRequest request) throws ServiceTechnicalException, ServiceLogicalException {
        List<Course> courses;

        try {
            courses = DaoFactory.getDaoFactory().getCourseDao().findAll();
            for (Course course: courses) {
                List<Rating> ratings = DaoFactory.getDaoFactory().getRatingDao().findRatingsByCourse(course.getId());
                course.setRatingList(ratings);
            }
            request.setAttribute("courses", courses);
        } catch (DAOLogicalException e) {
            throw new ServiceLogicalException(e);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e);
        }
    }

    /**
     * Fills each course with rating list for student
     * @throws ServiceLogicalException
     * @throws ServiceTechnicalException
     */
    public static void setCoursesWithRatingsForStudent(HttpServletRequest request, Account student) throws ServiceTechnicalException, ServiceLogicalException {
        List<Course> courses;

        try {
            courses = DaoFactory.getDaoFactory().getCourseDao().findCoursesForStudent(student);
            for (Course course: courses) {
                List<Rating> ratings = DaoFactory.getDaoFactory().getRatingDao().findRatingsByCourse(course.getId());
                course.setRatingList(ratings);
            }
            request.setAttribute("courses", courses);
        } catch (DAOLogicalException e) {
            throw new ServiceLogicalException(e);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e);
        }
    }

    /**
     * Fills ratings list for each course
     * @throws ServiceLogicalException
     * @throws ServiceTechnicalException
     */
    public static void setCoursesWithRatings(HttpServletRequest request) throws ServiceTechnicalException, ServiceLogicalException {
        List<Course> courses;

        try {
            courses = DaoFactory.getDaoFactory().getCourseDao().findActiveCourses();
            for (Course course: courses) {
                List<Rating> ratings = DaoFactory.getDaoFactory().getRatingDao().findRatingsByCourse(course.getId());
                course.setRatingList(ratings);
            }
            request.setAttribute("courses", courses);
        } catch (DAOLogicalException e) {
            throw new ServiceLogicalException(e);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e);
        }
    }

    /**
     * Fills all archived courses
     * @throws ServiceLogicalException
     * @throws ServiceTechnicalException
     */
    public static void setArchivedCourses(HttpServletRequest request) throws ServiceLogicalException, ServiceTechnicalException {
        List<Course> courses;

        try {
            courses = DaoFactory.getDaoFactory().getCourseDao().findArchivedCourses();
            for (Course course: courses) {
                List<Rating> ratings = DaoFactory.getDaoFactory().getRatingDao().findRatingsByCourse(course.getId());
                course.setRatingList(ratings);
        }
        request.setAttribute("archivedcourses", courses);
    } catch (DAOLogicalException e) {
        throw new ServiceLogicalException(e);
    } catch (DAOTechnicalException e) {
        throw new ServiceTechnicalException(e);
    }
}

    /**
     * Set courses for teacher
     * @param request
     * @param teacher teacher account
     * @throws ServiceLogicalException
     * @throws ServiceTechnicalException
     */
    public static void setCoursesForTeacher(HttpServletRequest request, Account teacher) throws ServiceLogicalException, ServiceTechnicalException {
        List<Course> courses;

        try {
            courses = DaoFactory.getDaoFactory().getCourseDao().findCoursesForTeacher(teacher);
            for (Course course: courses) {
                List<Rating> ratings = DaoFactory.getDaoFactory().getRatingDao().findRatingsByCourse(course.getId());
                course.setRatingList(ratings);
            }
            request.setAttribute("courses", courses);
        } catch (DAOLogicalException e) {
            throw new ServiceLogicalException(e);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e);
        }
    }

    /**
     * Set info for editing Courses
     * @param request
     * @throws ServiceTechnicalException
     * @throws ServiceLogicalException
     */
    public static void setInfoForCourse(HttpServletRequest request) throws ServiceTechnicalException, ServiceLogicalException {
        try {
            List<Status> statuses = DaoFactory.getDaoFactory().getStatusDao().findAll();
            request.setAttribute("courseStatuses", statuses);
            List<Account> teachers = DaoFactory.getDaoFactory().getAccountDao().findAllTeachers();
            request.setAttribute("teachers", teachers);
            List<Rating> students = DaoFactory.getDaoFactory().getRatingDao().findRatingsByCourse(Integer.parseInt(request.getParameter("cid")));
            request.setAttribute("students", students);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e);
        } catch (DAOLogicalException e) {
            throw new ServiceLogicalException(e);
        }
    }

    /**
     * Set info for editing Courses
     * @param request
     * @throws ServiceTechnicalException
     * @throws ServiceLogicalException
     */
    public static void setInfoForCreateCourse(HttpServletRequest request) throws ServiceTechnicalException, ServiceLogicalException {
        try {
            List<Status> statuses = DaoFactory.getDaoFactory().getStatusDao().findAll();
            request.setAttribute("courseStatuses", statuses);
            List<Account> teachers = DaoFactory.getDaoFactory().getAccountDao().findAllTeachers();
            request.setAttribute("teachers", teachers);
        } catch (DAOTechnicalException e) {
            throw new ServiceTechnicalException(e);
        } catch (DAOLogicalException e) {
            throw new ServiceLogicalException(e);
        }
    }
}
