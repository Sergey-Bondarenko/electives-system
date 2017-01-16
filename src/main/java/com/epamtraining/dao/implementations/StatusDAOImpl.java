package com.epamtraining.dao.implementations;

import com.epamtraining.connection.DataSource;
import com.epamtraining.dao.interfaces.StatusDAO;
import com.epamtraining.entities.Status;
import com.epamtraining.exception.DAOLogicalException;

import java.util.List;

/**
 * @author Sergey Bondarenko
 */
public class StatusDAOImpl extends AbstractDAOImpl implements StatusDAO {

    public StatusDAOImpl(final DataSource userConn) {
        super(userConn);
    }

    @Override
    public List<Status> findAll() throws DAOLogicalException {
            return findAll(set -> new Status(set.getInt("ID"),
                set.getString("COURSE_STATUS")),"SELECT * FROM STATUS");
    }

    @Override
    public Status findEntityById(Integer id) throws DAOLogicalException {
            return findFirst(set -> new Status(set.getInt("ID"),
                            set.getString("COURSE_STATUS")),
                    "SELECT * FROM STATUS WHERE ID = ?", id);
    }

    @Override
    public boolean update(Status entity) throws DAOLogicalException {
            return sqlUpdate("UPDATE STATUS SET COURSE_STATUS = ? WHERE ID = ?",
                    entity.getCourseStatus(), entity.getId());
    }

    @Override
    public boolean delete(Integer id) throws DAOLogicalException {
            return sqlUpdate("DELETE FROM STATUS WHERE ID = ?", id);
    }

    @Override
    public boolean create(Status entity) throws DAOLogicalException {
            return sqlUpdate("INSERT INTO STATUS (ID, COURSE_STATUS) VALUES ( ?, ? )",
                    entity.getId(), entity.getCourseStatus());
    }
}
