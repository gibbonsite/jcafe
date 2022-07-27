package com.poleschuk.cafe.model.mapper.impl;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.model.entity.LoyalScoreBonus;
import com.poleschuk.cafe.model.entity.Section;
import com.poleschuk.cafe.model.mapper.EntityRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.poleschuk.cafe.model.mapper.ColumnNames.*;

/**
 * SectionMapper class extracts ResultSet row into Section object.
 */
public class SectionMapper implements EntityRowMapper<Section> {
    private static final EntityRowMapper<Section> instance = new SectionMapper();

	public static EntityRowMapper<Section> getInstance() {
		return instance;
	}

	private SectionMapper() {
	}
	
    /**
     * Map row to Section object.
     *
     * @param resultSet the result set
     * @return the optional
     * @throws DaoException the dao exception
     */
    @Override
    public Optional<Section> mapRow(ResultSet resultSet) throws DaoException {
        Section section = new Section();
        Optional<Section> optionalSection;
        try {
            section.setSectionId(resultSet.getLong(SECTION_ID));
            section.setSectionName(resultSet.getString(SECTION_NAME));
            section.setEnabled(resultSet.getBoolean(ENABLED_SECTION));
            optionalSection = Optional.of(section);
        } catch (SQLException e) {
            optionalSection = Optional.empty();
        }
        return optionalSection;
    }
}