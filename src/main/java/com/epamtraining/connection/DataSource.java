package com.epamtraining.connection;

import com.epamtraining.dao.implementations.*;
import com.epamtraining.dao.interfaces.*;
import com.epamtraining.dao.DaoFactory;
import com.epamtraining.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * @author Sergey Bondarenko
 */
public class DataSource extends DaoFactory {
    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    private JdbcConnectionPool pool = JdbcConnectionPool.getInstance();

    public DataSource() throws ConnectionPoolException {
    }
    /**
     * Get single connection from concurrent queue
     * @return connection to use
     */
    public Connection getConnection() {
        Connection connection = pool.getConnectionFromPool();
        if (connection != null) {
            logger.info("Connection " + connection + " took from connection pool");
        } else {
            logger.error("Couldn't retrieve a connection from pool");
        }
        return new PoolConnection(connection, this);
    }

    public void closeConnection(Connection connection) {
        pool.returnConnectionToPool(connection);
    }

    @Override
    public StatusDAO getStatusDao() {
        return new StatusDAOImpl(this);
    }

    @Override
    public UserTypeDAO getUserTypeDao() {
        return new UserTypeDAOImpl(this);
    }

    @Override
    public CourseDAO getCourseDao() {
        return new CourseDAOImpl(this);
    }

    @Override
    public AccountDAO getAccountDao() {
        return new AccountDAOImpl(this);
    }

    @Override
    public RatingDAO getRatingDao() {
        return new RatingDAOImpl(this);
    }


}
