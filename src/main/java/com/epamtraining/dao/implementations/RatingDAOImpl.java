package com.epamtraining.dao.implementations;

import com.epamtraining.connection.DataSource;
import com.epamtraining.dao.interfaces.RatingDAO;
import com.epamtraining.entities.*;
import com.epamtraining.exception.DAOLogicalException;

import java.util.List;

/**
 * Rating data access object implementation
 * @author Sergey Bondarenko
 */
public class RatingDAOImpl extends AbstractDAOImpl implements RatingDAO {
    public RatingDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Rating> findAll() throws DAOLogicalException {
            return findAll(set -> new Rating(new Account(set.getInt("a.id"),
                            set.getString("a.name"),
                            set.getString("a.surname"),
                            new UserType(set.getInt("ut.id"), set.getString("ut.user_type"))),
                            new Course(set.getInt("c.id"), set.getString("c.name"),
                                    set.getString("c.description"), new Status(set.getInt("s.id"),
                                    set.getString("s.course_status")),
                                    new Account(set.getInt("a1.id"),
                                            set.getString("a1.name"),
                                            set.getString("a1.surname"),
                                            new UserType(set.getInt("ut1.id"), set.getString("ut1.user_type"))),
                                    set.getInt("listeners"),
                                    set.getInt("c.max_listeners")),
                            set.getString("r.rating"),
                            set.getString("r.comment")),
                    "SELECT r.rating, r.comment, c.id, c.name, c.description, (select count(course) from rating where course = c.id) as listeners, c.max_listeners, \n" +
                            "s.id, s.course_status, a1.id, a1.name, a1.surname, \n" +
                            "ut1.id, ut1.user_type, a.id, a.name, a.surname, ut.id, ut.user_type\n" +
                            "FROM elective.rating r \n" +
                            "\tINNER JOIN elective.course c ON ( r.course = c.id  )  \n" +
                            "\t\tINNER JOIN elective.status s ON ( c.status = s.id  )  \n" +
                            "\t\tINNER JOIN elective.account a1 ON ( c.teacher = a1.id  )  \n" +
                            "\t\t\tINNER JOIN elective.user_type ut1 ON ( a1.user_type_id = ut1.id  )  \n" +
                            "\tINNER JOIN elective.account a ON ( r.student = a.id  )  \n" +
                            "\t\tINNER JOIN elective.user_type ut ON ( a.user_type_id = ut.id  )"
            );
    }

    @Override
    public List<Rating> findRatingsByStudentId(Account student) throws DAOLogicalException {
            return findAll(set -> new Rating(new Account(set.getInt("a.id"),
                        set.getString("a.name"),
                        set.getString("a.surname"),
                        new UserType(set.getInt("ut.id"), set.getString("ut.user_type"))),
                        new Course(set.getInt("c.id"), set.getString("c.name"),
                                   set.getString("c.description"), new Status(set.getInt("s.id"),
                                   set.getString("s.course_status")),
                                new Account(set.getInt("a1.id"),
                                set.getString("a1.name"),
                                set.getString("a1.surname"),
                                new UserType(set.getInt("ut1.id"), set.getString("ut1.user_type"))),
                             set.getInt("listeners"),
                             set.getInt("c.max_listeners")),
                        set.getString("r.rating"),
                        set.getString("r.comment")),
                "SELECT r.rating, r.comment, c.id, c.name, c.description, (select count(course) from rating where course = c.id) as listeners, c.max_listeners, \n" +
                        "s.id, s.course_status, a1.id, a1.name, a1.surname, \n" +
                        "ut1.id, ut1.user_type, a.id, a.name, a.surname, ut.id, ut.user_type\n" +
                        "FROM elective.rating r \n" +
                        "\tINNER JOIN elective.course c ON ( r.course = c.id  )  \n" +
                        "\t\tINNER JOIN elective.status s ON ( c.status = s.id  )  \n" +
                        "\t\tINNER JOIN elective.account a1 ON ( c.teacher = a1.id  )  \n" +
                        "\t\t\tINNER JOIN elective.user_type ut1 ON ( a1.user_type_id = ut1.id  )  \n" +
                        "\tINNER JOIN elective.account a ON ( r.student = a.id  )  \n" +
                        "\t\tINNER JOIN elective.user_type ut ON ( a.user_type_id = ut.id  )  \n" +
                        "WHERE a.id = ?", student.getId());
    }

    @Override
    public boolean addRating(Rating rating) throws DAOLogicalException {
            return sqlUpdate("INSERT INTO rating (student, course, rating, comment) VALUES ( ?, ?, ?, ?)",
                rating.getStudent().getId(), rating.getCourse().getId(), rating.getRating(), rating.getComment());
    }

    @Override
    public boolean updateRating(Rating rating) throws DAOLogicalException {
        return sqlUpdate("UPDATE rating SET rating = ?, comment = ? WHERE student = ? AND course = ?",
                rating.getRating(), rating.getComment(), rating.getStudent().getId(), rating.getCourse().getId());
    }

    @Override
    public boolean addStudentToCourse(Account account, Integer courseId) throws DAOLogicalException {
            return sqlUpdate("INSERT INTO rating (student, course) VALUES ( ?, ?)", account.getId(), courseId);
    }

    @Override
    public Rating findRatingByStudentCourse(Integer student, Integer course) throws DAOLogicalException {
            return findFirst(set -> new Rating(new Account(set.getInt("a1.id"),
                        set.getString("a1.name"),
                        set.getString("a1.surname"),
                        new UserType(set.getInt("ut1.id"), set.getString("ut1.user_type"))),
                        new Course(set.getInt("c.id"), set.getString("c.name"),
                                set.getString("c.description"), new Status(set.getInt("s.id"),
                                set.getString("s.course_status")),
                                new Account(set.getInt("a.id"),
                                        set.getString("a.name"),
                                        set.getString("a.surname"),
                                        new UserType(set.getInt("ut.id"), set.getString("ut.user_type"))),
                                set.getInt("listeners"),
                                set.getInt("c.max_listeners")),
                        set.getString("r.rating"),
                        set.getString("r.comment")),
                "SELECT r.rating, r.comment, c.id, c.name, c.description, (select count(course) from rating where course = c.id) as listeners, \n" +
                        "c.max_listeners, s.id, s.course_status, a.id, a.name, \n" +
                        "a.surname, ut.id, ut.user_type, a1.id, a1.name, a1.surname, ut1.id, ut1.user_type\n" +
                        "FROM elective.rating r \n" +
                        "\tINNER JOIN elective.course c ON ( r.course = c.id  )  \n" +
                        "\t\tINNER JOIN elective.status s ON ( c.status = s.id  )  \n" +
                        "\t\tINNER JOIN elective.account a ON ( c.teacher = a.id  )  \n" +
                        "\t\t\tINNER JOIN elective.user_type ut ON ( a.user_type_id = ut.id  )  \n" +
                        "\tINNER JOIN elective.account a1 ON ( r.student = a1.id  )  \n" +
                        "\t\tINNER JOIN elective.user_type ut1 ON ( a1.user_type_id = ut1.id  )  \n" +
                        "WHERE r.student = ? AND r.course = ?", student, course);
    }

    @Override
    public boolean checkAllRatingsForCourse(Integer course) throws DAOLogicalException {
            List<Rating> ratings = findAll(set -> new Rating(new Account(set.getInt("a.id"),
                        set.getString("a.name"),
                        set.getString("a.surname"),
                        new UserType(set.getInt("a.user_type_id"), set.getString("ut.user_type"))),
                        set.getString("r.rating"),
                         set.getString("r.comment"))
                    ,"SELECT r.rating, r.comment, a.id, a.name, a.surname, a.user_type_id, ut.user_type\n" +
                        "FROM elective.rating r \n" +
                        "\tINNER JOIN elective.account a ON ( r.student = a.id  )  \n" +
                        "\t\tINNER JOIN elective.user_type ut ON ( a.user_type_id = ut.id  )  \n" +
                        "WHERE r.rating is null AND r.course = ?", course);
        return ratings.isEmpty();
    }

    @Override
    public boolean deleteForStudent(Integer cid, Integer sid) throws DAOLogicalException {
            return sqlUpdate("DELETE FROM RATING WHERE STUDENT = ? AND COURSE = ?", sid, cid);
    }

    @Override
    public List<Rating> findRatingsByCourse(Integer id) throws DAOLogicalException {
            return findAll(set -> new Rating(new Account(set.getInt("a.id"),
                        set.getString("a.name"),
                        set.getString("a.surname"),
                        new UserType(set.getInt("a.user_type_id"), set.getString("ut.user_type"))),
                        set.getString("r.rating"),
                        set.getString("r.comment")),
                "SELECT r.rating, r.comment, a.id, a.name, a.surname, a.user_type_id, ut.user_type\n" +
                        "FROM elective.rating r \n" +
                        "\tINNER JOIN elective.account a ON ( r.student = a.id  )  \n" +
                        "\t\tINNER JOIN elective.user_type ut ON ( a.user_type_id = ut.id  )  \n" +
                        "WHERE r.course = ?", id);
    }
}
