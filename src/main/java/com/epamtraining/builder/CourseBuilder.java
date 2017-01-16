package com.epamtraining.builder;

import com.epamtraining.dao.DaoFactory;
import com.epamtraining.entities.Account;
import com.epamtraining.entities.Course;
import com.epamtraining.entities.Status;
import com.epamtraining.exception.BuildException;
import com.epamtraining.exception.DAOLogicalException;
import com.epamtraining.exception.DAOTechnicalException;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Build a course object from request
 * @author Sergey Bondarenko
 */
public class CourseBuilder extends EntityBuilder<Course> {

    private Logger logger = Logger.getRootLogger();
    @Override
    public Course build(Map<String, String[]> map) throws BuildException {
        Course course = new Course();
        build(map, course);

        return course;
    }

    /**
     * Fill entity
     * @param map
     * @param course
     * @throws BuildException
     */
    public void build(Map<String, String[]> map, Course course) throws BuildException{
        boolean name = !buildName(map.get("name"), course);
        boolean description = !buildDescription(map.get("description"), course);
        boolean status = !buildStatus(map.get("status"), course);
        boolean teacher = !buildTeacher(map.get("teacher"), course);
        boolean maxListeners = !buildMaxListeners(map.get("max_listeners"), course);

        if (name || description || status || teacher || maxListeners ){
            throw new BuildException();
        }
    }


    /**
     * Build course name
     * @param args parameter values
     * @param course target course
     */
    private boolean buildName(String[] args, Course course) {
        if (args != null && args.length > 0) {
            String name = args[0];
            if (name.length() > 0){
                try {
                    course.setTitle(new String(args[0].getBytes("ISO-8859-1"),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.error(e);
                }
                return true;
            }
        }
        return false;
    }


    /**
     * Build course description
     * @param args parameter values
     * @param course target course
     */
    private boolean buildDescription(String[] args, Course course) {
        if (args != null && args.length > 0 && args[0].length() > 0) {
            String description = args[0];
            if (description.length() > 0) {
                try{
                    course.setDescription(new String(args[0].getBytes("ISO-8859-1"),"UTF-8"));
                } catch (UnsupportedEncodingException e){
                    logger.error(e);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Build course status
     * @param args parameter values
     * @param course target course
     * @throws BuildException
     */
    private boolean buildStatus(String[] args, Course course) throws BuildException {
        if (args != null && args.length > 0 && args[0].length() > 0) {
            int statusId = Integer.parseInt(args[0]);
            Status status = null;
            try {
                status = DaoFactory.getDaoFactory().getStatusDao().findEntityById(statusId);
            } catch (DAOLogicalException e) {
                throw new BuildException(e);
            } catch (DAOTechnicalException e) {
                throw new BuildException(e);
            }
            if (status != null) {
                course.setStatus(status);
                return true;
            }
        }
        return false;
    }

    /**
     * Build course teacher
     * @param args parameter values
     * @param course target course
     * @throws BuildException
     */
    private boolean buildTeacher(String[] args, Course course) throws BuildException {
        if (args != null && args.length > 0 && args[0].length() > 0) {
            int teacherId = Integer.parseInt(args[0]);
            Account account = null;
            try {
                account = DaoFactory.getDaoFactory().getAccountDao().findEntityById(teacherId);
            } catch (DAOLogicalException e) {
                throw new BuildException(e);
            } catch (DAOTechnicalException e) {
                throw new BuildException(e);
            }
            if (account != null) {
                course.setTeacher(account);
                return true;
            }
        }
        return false;
    }

    /**
     * Build course max listeners
     * @param args parameter values
     * @param course target course
     * @throws BuildException
     */
    private boolean buildMaxListeners(String[] args, Course course) throws BuildException {
        if (args != null && args.length > 0 && args[0].length() > 0) {
            try {
                int maxListeners = Integer.parseInt(args[0]);
                if (maxListeners >= 0) {
                    course.setMaxListeners(maxListeners);
                    return true;
                } else {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
}
