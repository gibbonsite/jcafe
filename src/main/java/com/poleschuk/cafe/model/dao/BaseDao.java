package com.poleschuk.cafe.model.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.model.entity.AbstractEntity;

/**
 * BaseDao class encapsulates base functionality of Dao pattern.
 */
public abstract class BaseDao<T extends AbstractEntity> {
    private static final Logger logger = LogManager.getLogger();
    protected Connection proxyConnection;

	public abstract boolean insert(T t) throws DaoException;
	public abstract boolean delete(long id) throws DaoException;
	public abstract Optional<T> findEntityById(long id) throws DaoException;
	public abstract List<T> findAll() throws DaoException;
	public abstract Optional<T> update(T t) throws DaoException;

    protected void setConnection(Connection connection) {
        this.proxyConnection = connection;
    }
}
