package com.poleschuk.cafe.model.dao;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.model.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * EntityTransaction class provides transaction management.
 */
public class EntityTransaction {
    static final Logger logger = LogManager.getLogger();
    private Connection connection;

    /**
     * Init transaction.
     *
     * @param daos Dao objects
     */
    public void initTransaction(BaseDao...daos) {
        if (connection == null) {
            connection = ConnectionPool.getInstance().getConnection();
        }
        try{
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        for (BaseDao daoElement: daos) {
            daoElement.setConnection(connection);
        }
    }

    /**
     * End transaction and release connection to the pool.
     */
    public void endTransaction() {
        if (connection != null) {
            try{
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.log(Level.ERROR, e.getMessage());
            }
            ConnectionPool.getInstance().releaseConnection(connection);
            connection = null;
        }
    }

    /**
     * Commit a transaction.
     */
    public void commit() {
        try{
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    /**
     * Rollback a transaction.
     */
    public void rollback() {
        try{
            connection.rollback();
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    /**
     * Get connection from pool and set connection to Dao object.
     *
     * @param dao Dao object
     */
    public void init(BaseDao dao) {
        if (connection == null) {
            connection = ConnectionPool.getInstance().getConnection();
        }
        dao.setConnection(connection);
    }

    /**
     * Release connection.
     *
     * @param dao Dao object
     */
    public void end() {
        if (connection != null) {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        connection = null;
    }
}
