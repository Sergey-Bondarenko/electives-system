package com.epamtraining.connection;

import com.epamtraining.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Database connection pool
 * @author  Sergey Bondarenko
 */
public class JdbcConnectionPool {

    private final Logger logger = Logger.getRootLogger();
    /**
     * Concurrent fair lock
     */
    private static Lock lock = new ReentrantLock(true);
    /**
     * Singleton instance
     */
    private static JdbcConnectionPool instance = null;
    /**
     * Connections
     */
    private BlockingQueue<Connection> connections;

    private JdbcConnectionPool() throws ConnectionPoolException {
        initializeConnectionPool();
    }
    /**
     * Get single instance
     * @return JdbcConnectionPool
     */
    public static JdbcConnectionPool getInstance() throws ConnectionPoolException{
        lock.lock();
        if (null == instance){
            instance = new JdbcConnectionPool();
        }
        lock.unlock();

        return instance;
    }
    /**
     * Create and fill connection pool
     */
    private void initializeConnectionPool() throws ConnectionPoolException {
        connections = new LinkedBlockingQueue<Connection>(Configuration.getInstance().DB_MAX_CONNECTIONS);
        while(!checkIfConnectionPoolIsFull()) {
            connections.add(createNewConnectionForPool());
        }
    }
    /**
     * Checker for connection pool
     * @return true if pool is full
     */
    private synchronized boolean checkIfConnectionPoolIsFull() {
        final int MAX_POOL_SIZE = Configuration.getInstance().DB_MAX_CONNECTIONS;

        if(connections.size() < MAX_POOL_SIZE) {
            return false;
        }
        return true;
    }
    /**
     * Create connection for pool
     * @return connection
     */
    private Connection createNewConnectionForPool() throws ConnectionPoolException {
        Configuration config = Configuration.getInstance();
        try {
            Class.forName(config.DB_DRIVER);
            Connection connection = DriverManager.getConnection(
                    config.DB_URL, config.DB_USER_NAME, config.DB_PASSWORD);
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            throw new ConnectionPoolException(e);
        }
    }
    /**
     * Get single connection from concurrent queue
     * @return connection to use
     */
    public synchronized Connection getConnectionFromPool() {
        Connection connection = connections.poll();
        if(connection != null) {
            logger.info("Connection " + connection + " took from connection pool");
        } else {
            logger.error("Couldn't retrieve a connection from pool");
        }
        return connection;
    }
    /**
     * Return connection
     * @param connection connection to return to the pool
     */
    public synchronized void returnConnectionToPool(Connection connection) {
        if (connection != null) {
            try {
                connections.put(connection);
                logger.info("Connection " + connection + " returned to connection pool");
                logger.info("There are(is) " + (connections.size() - connections.remainingCapacity()) + " connection(s) in the pool.");
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * Close all connections in pool
     */
    public void shutDown(){

        Iterator<Connection> iterator = connections.iterator();
        while(iterator.hasNext()){
            Connection connection = iterator.next();
            try {
                // close connection
                connection.close();
                // remove it to prevent the use of closed connection
                iterator.remove();
            } catch (SQLException e) {
                logger.error("Couldn't close connection: " + e.getMessage());
            }
        }

        logger.info("Connection pool is shut down");
    }
}

