package com.epamtraining.dao.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper for storing ResultSet
 *
 * @author Sergey Bondarenko
 */
public interface Mapper<T> {
    /**
     * Map method for returning ResultSet.
     *
     * @param set
     * @return T
     * @throws SQLException
     */
    T map(ResultSet set) throws SQLException;
}
