package com.epamtraining.dao.implementations;

import com.epamtraining.connection.DataSource;
import com.epamtraining.dao.interfaces.CourseDAO;
import com.epamtraining.entities.Account;
import com.epamtraining.entities.Course;
import com.epamtraining.entities.Status;
import com.epamtraining.entities.UserType;
import com.epamtraining.exception.DAOLogicalException;

import java.util.List;

/**
 * Course data access object
 * @author Sergey Bondarenko
 */
public class CourseDAOImpl extends AbstractDAOImpl implements CourseDAO {

    public CourseDAOImpl(final DataSource userConn) {
        super(userConn);
    }

    @Override
    public List<Course> findAll() throws DAOLogicalException {
            return findAll(set -> new Course(set.getInt("c.id"), set.getString("c.name"),
                            set.getString("c.description"), new Status(set.getInt("s.id"),
                            set.getString("s.course_status")),
                                new Account(set.getInt("a.id"),
                                        set.getString("a.name"),
                                        set.getString("a.surname"),
                                        new UserType(set.getInt("ut.id"), set.getString("ut.user_type"))),
                            set.getInt("listeners"),
                            set.getInt("c.max_listeners")
                    ),
                    "SELECT c.id, c.name, c.description, (select count(course) from rating where course = c.id) as listeners, \n" +
                            "c.max_listeners, s.id, s.course_status, a.id, a.name, a.surname, ut.id, ut.user_type\n" +
                            "FROM elective.course c \n" +
                            "\tINNER JOIN elective.status s ON ( c.status = s.id  )  \n" +
                            "\tINNER JOIN elective.account a ON ( c.teacher = a.id  )  \n" +
                            "\t\tINNER JOIN elective.user_type ut ON ( a.user_type_id = ut.id  )");
    }

    @Override
    public Course findEntityById(Integer id) throws DAOLogicalException {
            return findFirst(set -> new Course(set.getInt("c.id"), set.getString("c.name"),
                            set.getString("c.description"), new Status(set.getInt("s.id"),
                            set.getString("s.course_status")),
                            new Account(set.getInt("a.id"),
                                    set.getString("a.name"),
                                    set.getString("a.surname"),
                                    new UserType(set.getInt("ut.id"), set.getString("ut.user_type"))),
                            set.getInt("listeners"),
                            set.getInt("c.max_listeners")
                    ),
                    "SELECT c.id, c.name, c.description, (select count(course) from rating where course = c.id) as listeners, \n" +
                            "c.max_listeners, s.id, s.course_status, a.id, a.name, a.surname, ut.id, ut.user_type\n" +
                            "FROM elective.course c \n" +
                            "\tINNER JOIN elective.status s ON ( c.status = s.id  )  \n" +
                            "\tINNER JOIN elective.account a ON ( c.teacher = a.id  )  \n" +
                            "\t\tINNER JOIN elective.user_type ut ON ( a.user_type_id = ut.id  )" +
                            "WHERE c.id = ?", id);
    }

    @Override
    public boolean update(Course entity) throws DAOLogicalException {
            return sqlUpdate("UPDATE course SET name = ?, description = ?, status = ?, teacher = ?, max_listeners = ? WHERE ID = ?",
                        entity.getName(), entity.getDescription(), entity.getStatus().getId(), entity.getTeacher().getId(),
                        entity.getMaxListeners(), entity.getId());
    }

    @Override
    public boolean updateCourseStatus(Integer course) throws DAOLogicalException {
            return sqlUpdate("UPDATE course SET status = 3 WHERE id = ?", course);
    }

    @Override
    public boolean delete(Integer id) throws DAOLogicalException {
            return sqlUpdate("DELETE FROM course WHERE id = ?" , id);
    }

    @Override
    public boolean create(Course entity) throws DAOLogicalException {
            return sqlUpdate("INSERT INTO course (name, description, teacher, status, max_listeners) VALUES ( ?, ?, ?, ?, ?)",
                        entity.getName(), entity.getDescription(), entity.getTeacher().getId(), entity.getStatus().getId(),
                        entity.getMaxListeners());
    }

    @Override
    public List<Course> findCoursesForTeacher(Account account) throws DAOLogicalException {
            return findAll(set -> new Course(set.getInt("c.id"), set.getString("c.name"),
                            set.getString("c.description"), new Status(set.getInt("s.id"),
                            set.getString("s.course_status")),
                            new Account(set.getInt("a.id"),
                                    set.getString("a.name"),
                                    set.getString("a.surname"),
                                    new UserType(set.getInt("ut.id"), set.getString("ut.user_type"))),
                            set.getInt("listeners"),
                            set.getInt("c.max_listeners")
                    ),
                    "SELECT c.id, c.name, c.description, (select count(course) from rating where course = c.id) as listeners, \n" +
                            "c.max_listeners, a.id, a.name, a.surname, ut.id, ut.user_type, s.id, s.course_status\n" +
                            "FROM elective.course c \n" +
                            "\tINNER JOIN elective.account a ON ( c.teacher = a.id  )  \n" +
                            "\t\tINNER JOIN elective.user_type ut ON ( a.user_type_id = ut.id  )  \n" +
                            "\tINNER JOIN elective.status s ON ( c.status = s.id  )  \n" +
                            "WHERE a.id = ? AND c.status <> 3", account.getId());
    }

    @Override
    public List<Course> findCoursesForStudent(Account account) throws DAOLogicalException {
            return findAll(set -> new Course(set.getInt("c.id"), set.getString("c.name"),
                            set.getString("c.description"), new Status(set.getInt("s.id"),
                            set.getString("s.course_status")),
                            new Account(set.getInt("a.id"),
                                    set.getString("a.name"),
                                    set.getString("a.surname"),
                                    new UserType(set.getInt("ut.id"), set.getString("ut.user_type"))),
                            set.getInt("listeners"),
                            set.getInt("c.max_listeners")
            ),
                    "SELECT c.id, c.name, c.description, (select count(course) from rating where course = c.id) as listeners, \n" +
                            "c.max_listeners, s.id, s.course_status, a.id, a.name, a.surname, ut.id, ut.user_type, r.student\n" +
                            "FROM elective.course c \n" +
                            "\tINNER JOIN elective.status s ON ( c.status = s.id  )  \n" +
                            "\tINNER JOIN elective.account a ON ( c.teacher = a.id  )  \n" +
                            "\t\tINNER JOIN elective.user_type ut ON ( a.user_type_id = ut.id  )  \n" +
                            "\tINNER JOIN elective.rating r ON ( c.id = r.course  )  \n" +
                            "WHERE r.student = ? AND s.id <> 3", account.getId());
    }

    @Override
    public List<Course> findActiveCourses() throws DAOLogicalException {
            return findAll(set -> new Course(set.getInt("c.id"), set.getString("c.name"),
                            set.getString("c.description"), new Status(set.getInt("s.id"),
                            set.getString("s.course_status")),
                            new Account(set.getInt("a.id"),
                                    set.getString("a.name"),
                                    set.getString("a.surname"),
                                    new UserType(set.getInt("ut.id"), set.getString("ut.user_type"))),
                            set.getInt("listeners"),
                            set.getInt("c.max_listeners")
            ),
                    "SELECT c.id, c.name, c.description, (select count(course) from rating where course = c.id) as listeners, \n" +
                            "c.max_listeners, a.id, a.name, a.surname, ut.id, ut.user_type, s.id, s.course_status\n" +
                            "FROM elective.course c \n" +
                            "\tINNER JOIN elective.account a ON ( c.teacher = a.id  )  \n" +
                            "\t\tINNER JOIN elective.user_type ut ON ( a.user_type_id = ut.id  )  \n" +
                            "\tINNER JOIN elective.status s ON ( c.status = s.id  )  \n" +
                            "WHERE s.id <> 3");
    }

    @Override
    public List<Course> findArchivedCourses() throws DAOLogicalException {
            return findAll(set -> new Course(set.getInt("c.id"), set.getString("c.name"),
                            set.getString("c.description"), new Status(set.getInt("s.id"),
                            set.getString("s.course_status")),
                            new Account(set.getInt("a.id"),
                                    set.getString("a.name"),
                                    set.getString("a.surname"),
                                    new UserType(set.getInt("ut.id"), set.getString("ut.user_type"))),
                            set.getInt("listeners"),
                            set.getInt("c.max_listeners")
            ),
                    "SELECT c.id, c.name, c.description, (select count(course) from rating where course = c.id) as listeners, \n" +
                            "c.max_listeners, a.id, a.name, a.surname, ut.id, ut.user_type, s.id, s.course_status\n" +
                            "FROM elective.course c \n" +
                            "\tINNER JOIN elective.account a ON ( c.teacher = a.id  )  \n" +
                            "\t\tINNER JOIN elective.user_type ut ON ( a.user_type_id = ut.id  )  \n" +
                            "\tINNER JOIN elective.status s ON ( c.status = s.id  )  \n" +
                            "WHERE s.id = 3");

    }
}
