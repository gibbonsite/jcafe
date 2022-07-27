package com.poleschuk.cafe.model.mapper.impl;


import static com.poleschuk.cafe.model.mapper.ColumnNames.DESCRIPTION;
import static com.poleschuk.cafe.model.mapper.ColumnNames.ENABLED_MENU_PRODUCT;
import static com.poleschuk.cafe.model.mapper.ColumnNames.MENU_ID;
import static com.poleschuk.cafe.model.mapper.ColumnNames.MENU_LOYAL_SCORE;
import static com.poleschuk.cafe.model.mapper.ColumnNames.MENU_NAME;
import static com.poleschuk.cafe.model.mapper.ColumnNames.PICTURE_PATH;
import static com.poleschuk.cafe.model.mapper.ColumnNames.PRICE;
import static com.poleschuk.cafe.model.mapper.ColumnNames.MENU_SECTION_ID;
import static com.poleschuk.cafe.model.mapper.ColumnNames.WEIGHT;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.model.entity.Menu;
import com.poleschuk.cafe.model.mapper.EntityRowMapper;

/**
 * MenuMapper class extracts ResultSet row into Menu object.
 */
public class MenuMapper implements EntityRowMapper<Menu> {
    private static final Logger logger = LogManager.getLogger();
    private static final EntityRowMapper<Menu> instance = new MenuMapper();


	public static EntityRowMapper<Menu> getInstance() {
		return instance;
	}

	private MenuMapper() {
	}
	
    @Override
    public Optional<Menu> mapRow(ResultSet resultSet) throws DaoException {
        Menu menu = new Menu();
        Optional<Menu> optionalMenu;
        try{
            menu.setMenuId(resultSet.getLong(MENU_ID));
            menu.setName(resultSet.getString(MENU_NAME));
            menu.setPicturePath(resultSet.getString(PICTURE_PATH));
            menu.setDescription(resultSet.getString(DESCRIPTION));
            menu.setWeight(resultSet.getInt(WEIGHT));
            menu.setLoyalScore(resultSet.getBigDecimal(MENU_LOYAL_SCORE));
            menu.setPrice(resultSet.getBigDecimal(PRICE));
            menu.setSectionId(resultSet.getLong(MENU_SECTION_ID));
            logger.log(Level.INFO, "Enabled - " + resultSet.getBoolean(ENABLED_MENU_PRODUCT));
            menu.setEnabled(resultSet.getBoolean(ENABLED_MENU_PRODUCT));
            logger.log(Level.INFO, "Enabled - " + menu.isEnabled());
            optionalMenu = Optional.of(menu);
        } catch (SQLException e) {
            logger.log(Level.WARN, "Not found menu item! ");
            optionalMenu = Optional.empty();
        }
        return optionalMenu;
    }
}