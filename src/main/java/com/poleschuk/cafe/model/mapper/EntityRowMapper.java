package com.poleschuk.cafe.model.mapper;

import java.sql.ResultSet;
import java.util.Optional;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.model.entity.AbstractEntity;

/**
 * CustomRowMapper class serves as a base class of mapper hierarchy. Each mapper object maps
 * JDBC ResultSet into entity object.
 */
@FunctionalInterface
public interface EntityRowMapper<T extends AbstractEntity> {

	/**
	 * Map JDBC ResultSet object into entity object.
	 * @param resultSet
	 * @return result of the operation
	 * @throws DaoException the Dao exception
	 */
    Optional<T> mapRow(ResultSet resultSet) throws DaoException;
}
