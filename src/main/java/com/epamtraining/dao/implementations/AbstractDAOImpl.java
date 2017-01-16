package com.epamtraining.dao.implementations;

import com.epamtraining.connection.DataSource;
import com.epamtraining.dao.interfaces.Mapper;
import com.epamtraining.exception.DAOLogicalException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract data access object
 * @author Sergey Bondarenko
 */
public class AbstractDAOImpl {
    protected DataSource dataSource;

    public AbstractDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    /**
     * Find first record
     * @return record
     */
    protected  <T> T findFirst(Mapper<T> mapper, String sql, Object... preparedArgs) throws DAOLogicalException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            if (preparedArgs != null){
                for (int i = 0; i < preparedArgs.length; i++) {
                    preparedStatement.setObject(i + 1, preparedArgs[i]);
                }
            }
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapper.map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOLogicalException();
        } finally {
            Query.close(resultSet, preparedStatement, connection);
        }
        return null;
    }
    /**
     * Find one record, if found more than one throws exception
     * @return record
     * @throws SQLException
     */
    protected  <T> T findOne(Mapper<T> mapper, String sql, Object... preparedArgs) throws DAOLogicalException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            if (preparedArgs != null) {
                for (int i = 0; i < preparedArgs.length; i++) {
                    preparedStatement.setObject(i + 1, preparedArgs[i]);
                }
            }
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.isLast()) {
                    return mapper.map(resultSet);
                }
            }
            throw new SQLException("Founded two or more resultSets.");
        } catch (SQLException e) {
            throw new DAOLogicalException(e);
        } finally {
            Query.close(resultSet, preparedStatement, connection);
        }
    }
    /**
     * Find all records
     * @return list
     * @throws DAOLogicalException
     */
    protected  <T> List<T> findAll(Mapper<T> mapper, String sql, Object... preparedArgs) throws DAOLogicalException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T> list = new ArrayList<T>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            if (preparedArgs != null) {
                for (int i = 0; i < preparedArgs.length; i++) {
                    preparedStatement.setObject(i + 1, preparedArgs[i]);
                }
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(mapper.map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOLogicalException(e);
        } finally {
            Query.close(resultSet, preparedStatement, connection);
        }
        return list;
    }

    /**
     * Perform sql query for update db
     * @param sql
     * @param preparedArgs
     * @return
     */
    protected boolean sqlUpdate(String sql, Object... preparedArgs) throws DAOLogicalException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = null;
        Integer resultValue = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < preparedArgs.length; i++ ){
                preparedStatement.setObject( i+1, preparedArgs[i]);
            }
            resultValue = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOLogicalException(e);
        } finally {
            Query.close(null, preparedStatement, connection);
        }
        return resultValue > 0;
    }
}

