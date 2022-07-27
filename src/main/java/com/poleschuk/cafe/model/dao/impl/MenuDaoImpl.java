package com.poleschuk.cafe.model.dao.impl;


import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.model.dao.BaseDao;
import com.poleschuk.cafe.model.dao.MenuDao;
import com.poleschuk.cafe.model.entity.Menu;
import com.poleschuk.cafe.model.mapper.EntityRowMapper;
import com.poleschuk.cafe.model.mapper.impl.MenuMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * MenuDaoImpl class implements MenuDao interface and executes requests
 * to the DB.
 */
public class MenuDaoImpl extends BaseDao<Menu> implements MenuDao {
    private static final Logger logger = LogManager.getLogger();
    private static final int ONE_UPDATE = 1;
    private static final String SQL_SELECT_ALL_MENU = """
            SELECT menu_id, name, picture_path, description, weight,
            loyal_score, price, section_id, enabled FROM menu
            WHERE enabled = true""";
    private static final String SQL_SELECT_MENU_BY_ID = """
            SELECT menu_id, name, picture_path, description, weight,
            loyal_score, price, section_id, enabled FROM menu
            WHERE menu_id = (?)""";
    private static final String SQL_INSERT_NEW_MENU_ITEM = """
            INSERT INTO menu(name, picture_path, description, weight,
            loyal_score, price, section_id, enabled)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)""";
    private static final String SQL_DELETE_MENU_ITEM = """
            UPDATE menu
            SET enabled = false
            WHERE menu_id = (?)""";
    private static final String SQL_DELETE_MENU_BY_SECTION_ID = """
            UPDATE menu
            SET enabled = false
            WHERE section_id = (?)""";
    private static final String SQL_UPDATE_MENU = """
            UPDATE menu SET name = (?), description = (?),
            weight = (?), loyal_score = (?),
            price = (?), section_id = (?) WHERE menu_id = (?)""";
    private static final String SQL_UPDATE_IMAGE_PATH_BY_NAME = """
            UPDATE menu SET picture_path = (?) WHERE name = (?)""";
    private static final String SQL_SELECT_MENU_BY_NAME = """
            SELECT menu_id, name, picture_path, description, weight,
            loyal_score, price, section_id, enabled FROM menu
            WHERE name = (?)""";
    private static final String SQL_FIND_MENU_SUBLIST_BY_SECTION_ID = """
            SELECT menu_id, name, picture_path, description, weight,
            loyal_score, price,  section_id, enabled FROM menu
            WHERE section_id = (?) AND enabled = true
            LIMIT ? OFFSET ?""";
    private static final String SQL_SELECT_ALL_MENU_ROW_COUNT = """
            SELECT COUNT(*) FROM menu
            WHERE enabled = true""";
    private static final String SQL_SELECT_MENU_SUBLIST = """
            SELECT menu_id, name, picture_path, description, weight,
            loyal_score, price, section_id, enabled FROM menu
            WHERE enabled = true
            LIMIT ? OFFSET ?""";
    private static final String SQL_SELECT_ALL_SORTED_MENU = """
            SELECT menu_id, name, picture_path, description, weight,
            loyal_score, price, section_id, enabled FROM menu
            WHERE enabled = true
            ORDER BY price
            LIMIT ? OFFSET ?""";
    private static final String SQL_SELECT_ALL_SORTED_MENU_BY_POPULARITY = """
            SELECT menu.menu_id, name, picture_path, description, weight,
            loyal_score, price, section_id, enabled, all_dish FROM menu
            LEFT JOIN (SELECT menu_id, SUM(menu_number) AS all_dish FROM orders_menu
            GROUP BY menu_id) AS food ON food.menu_id = menu.menu_id
            WHERE enabled = true
            ORDER BY all_dish DESC
            LIMIT ? OFFSET ?""";
    private static final String SQL_SELECT_SORTED_SECTION_MENU = """
            SELECT menu_id, name, picture_path, description, weight,
            loyal_score, price, section_id, enabled FROM menu
            WHERE section_id = ? AND enabled = true
            ORDER BY price
            LIMIT ? OFFSET ?""";
    private static final String SQL_SELECT_ALL_SORTED_SECTION_MENU_BY_POPULARITY = """
            SELECT menu.menu_id, name, picture_path, description, weight,
            loyal_score, price, section_id, enabled, all_dish FROM menu
            LEFT JOIN (SELECT menu_id, SUM(menu_number) AS all_dish FROM orders_menu
            GROUP BY menu_id) AS year_food ON year_food.menu_id = menu.menu_id
            WHERE enabled = true AND section_id = ?
            ORDER BY all_dish DESC
            LIMIT ? OFFSET ?""";
    private static final String SQL_SELECT_MENU_ROW_COUNT_BY_SECTION_ID = """
            SELECT COUNT(*) FROM menu WHERE section_id = ? AND enabled = true""";
    private static final String SQL_SELECT_ALL_DELETED_MENU_PRODUCTS = """
            SELECT menu_id, name, picture_path, description, weight,
            loyal_score, price, menu.section_id, menu.enabled FROM menu
            JOIN sections ON sections.section_id = menu.section_id
            WHERE menu.enabled = false AND sections.enabled = true""";
    private static final String SQL_RESTORE_MENU_BY_PRODUCT_ID = """
            UPDATE menu
            SET enabled = true
            WHERE menu_id = (?)""";
    private static final String SQL_RESTORE_MENU_BY_SECTION_ID = """
            UPDATE menu
            SET enabled = true
            WHERE section_id = (?)""";

    @Override
    public List<Menu> findAll() throws DaoException {
    	EntityRowMapper<Menu> mapper = MenuMapper.getInstance();
        List<Menu> menuList = new ArrayList<>();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_MENU);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Optional<Menu> optionalMenu = mapper.mapRow(resultSet);
                if (optionalMenu.isPresent()) {
                    menuList.add(optionalMenu.get());
                    logger.log(Level.INFO, "Present");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find all menu method: " + e.getMessage());
            throw new DaoException("Exception while find all menu method ", e);
        }
        return menuList;
    }

    @Override
    public Optional<Menu> findEntityById(long id) throws DaoException {
    	EntityRowMapper<Menu> mapper = MenuMapper.getInstance();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_MENU_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find menu by id method: " + e.getMessage());
            throw new DaoException("Exception while find menu by id method ", e);
        }
        logger.log(Level.INFO, "Menu item is empty ");
        return Optional.empty();
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_DELETE_MENU_ITEM)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while delete menu by id method: " + e.getMessage());
            throw new DaoException("Exception while delete menu by id method ", e);
        }
    }

    @Override
    public boolean insert(Menu entity) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_INSERT_NEW_MENU_ITEM)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getPicturePath());
            statement.setString(3, entity.getDescription());
            statement.setDouble(4, entity.getWeight());
            statement.setBigDecimal(5, entity.getLoyalScore());
            statement.setBigDecimal(6, entity.getPrice());
            statement.setLong(7, entity.getSectionId());
            statement.setBoolean(8, entity.isEnabled());
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while create menu method: " + e.getMessage());
            throw new DaoException("Exception while create menu method ", e);
        }
    }
    @Override
    public Optional<Menu> update(Menu entity) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_UPDATE_MENU)) {
            Optional<Menu> menu = findEntityById(entity.getMenuId());
            logger.log(Level.INFO, entity.getName());
            statement.setString(1, entity.getName());
            logger.log(Level.INFO, entity.getDescription());
            statement.setString(2, entity.getDescription());
            logger.log(Level.INFO, entity.getWeight());
            statement.setDouble(3, entity.getWeight());
            logger.log(Level.INFO, entity.getLoyalScore());
            statement.setBigDecimal(4, entity.getLoyalScore());
            logger.log(Level.INFO, entity.getPrice());
            statement.setBigDecimal(5, entity.getPrice());
            logger.log(Level.INFO, entity.getSectionId());
            statement.setLong(6, entity.getSectionId());
            logger.log(Level.INFO, entity.getMenuId());
            statement.setLong(7, entity.getMenuId());
            return statement.executeUpdate() == ONE_UPDATE ? menu : Optional.empty();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while update menu method: " + e.getMessage());
            throw new DaoException("Exception while update menu method ", e);
        }
    }

    @Override
    public boolean updateImagePathByName(String name, String filePath) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_UPDATE_IMAGE_PATH_BY_NAME)) {
            statement.setString(1, filePath);
            statement.setString(2, name);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while update image path by name menu method: " + e.getMessage());
            throw new DaoException("Exception while update image path by name menu method ", e);
        }
    }

    @Override
    public Optional<Menu> findMenuByName(String name) throws DaoException {
        Optional<Menu> food = Optional.empty();
    	EntityRowMapper<Menu> mapper = MenuMapper.getInstance();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_MENU_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    food = mapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find food by name method: " + e.getMessage());
            throw new DaoException("Exception while find food by name method ", e);
        }
        return food;
    }

    @Override
    public List<Menu> findMenuSublistBySectionId(int pageSize, int offset, long sectionId) throws DaoException {
        List<Menu> menuList = new ArrayList<>();
    	EntityRowMapper<Menu> mapper = MenuMapper.getInstance();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_FIND_MENU_SUBLIST_BY_SECTION_ID)) {
            statement.setLong(1, sectionId);
            statement.setInt(2, pageSize);
            statement.setInt(3, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Menu> optionalMenu = mapper.mapRow(resultSet);
                    optionalMenu.ifPresent(menuList::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find menu sublist by section id method: " + e.getMessage());
            throw new DaoException("Exception in a findMenuSublistBySection method. ", e);
        }
        return menuList;
    }

    @Override
    public int readRowCount() throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_MENU_ROW_COUNT)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt(1) : 0;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while read row count from menu table method: " + e.getMessage());
            throw new DaoException("Exception in a readRowCount method. ", e);
        }
    }

    @Override
    public List<Menu> findMenuSublist(int pageSize, int offset) throws DaoException {
        List<Menu> menuSublist = new ArrayList<>();
    	EntityRowMapper<Menu> mapper = MenuMapper.getInstance();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_MENU_SUBLIST)) {
            statement.setInt(1, pageSize);
            statement.setInt(2, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Menu> optionalMenu = mapper.mapRow(resultSet);
                    optionalMenu.ifPresent(menuSublist::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find menu sublist method: " + e.getMessage());
            throw new DaoException("Exception in a findMenuSublist method. ", e);
        }
        return menuSublist;
    }

    @Override
    public List<Menu> findAllSortedMenuByPrice(int pageSize, int offset) throws DaoException {
        List<Menu> sortedList = new ArrayList<>();
    	EntityRowMapper<Menu> mapper = MenuMapper.getInstance();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_SORTED_MENU)) {
            statement.setInt(1, pageSize);
            statement.setInt(2, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Menu> optionalMenu = mapper.mapRow(resultSet);
                    optionalMenu.ifPresent(sortedList::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find sorted menu method: " + e.getMessage());
            throw new DaoException("Exception in a findAllSortedMenu method. ", e);
        }
        return sortedList;
    }

    @Override
    public List<Menu> findSortedSectionMenuByPrice(int pageSize, int offset, long sectionId) throws DaoException {
        List<Menu> sortedList = new ArrayList<>();
    	EntityRowMapper<Menu> mapper = MenuMapper.getInstance();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_SORTED_SECTION_MENU)) {
            statement.setLong(1, sectionId);
            statement.setInt(2, pageSize);
            statement.setInt(3, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Menu> optionalMenu = mapper.mapRow(resultSet);
                    optionalMenu.ifPresent(sortedList::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find sorted menu sublist by section id method: " + e.getMessage());
            throw new DaoException("Exception in a findSortedSectionMenu method. ", e);
        }
        return sortedList;
    }
    @Override
    public List<Menu> findAllSortedMenuByPopularity(int pageSize, int offset) throws DaoException {
        List<Menu> menuList = new ArrayList<>();
    	EntityRowMapper<Menu> mapper = MenuMapper.getInstance();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_SORTED_MENU_BY_POPULARITY)) {
            statement.setInt(1, pageSize);
            statement.setInt(2, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Menu> optionalMenu = mapper.mapRow(resultSet);
                    optionalMenu.ifPresent(menuList::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find sorted menu sublist by popularity method: " + e.getMessage());
            throw new DaoException("Exception in a findAllSortedMenuByPopularity method. ", e);
        }
        return menuList;
    }

    @Override
    public List<Menu> findAllSortedSectionMenuByPopularity(int pageSize, int offset, long sectionId) throws DaoException {
        List<Menu> menuList = new ArrayList<>();
    	EntityRowMapper<Menu> mapper = MenuMapper.getInstance();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_SORTED_SECTION_MENU_BY_POPULARITY)) {
            statement.setLong(1, sectionId);
            statement.setInt(2, pageSize);
            statement.setInt(3, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Menu> optionalMenu = mapper.mapRow(resultSet);
                    optionalMenu.ifPresent(menuList::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find sorted menu sublist by section id and by popularity method: " + e.getMessage());
            throw new DaoException("Exception in a findAllSortedSectionMenuByPopularity method. ", e);
        }
        return menuList;
    }
    @Override
    public int readRowCountBySection(long sectionId) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_MENU_ROW_COUNT_BY_SECTION_ID)) {
            statement.setLong(1, sectionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt(1) : 0;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while read row count from menu by section table method: " + e.getMessage());
            throw new DaoException("Exception in a readRowCountBySection method. ", e);
        }
    }

    @Override
    public boolean deleteMenuBySectionId(long sectionId) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_DELETE_MENU_BY_SECTION_ID)) {
            statement.setLong(1, sectionId);
            return statement.executeUpdate() >= ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while delete menu by section id = " + sectionId);
            throw new DaoException("Exception while delete menu by section id = " + sectionId, e);
        }
    }

    @Override
    public boolean restoreMenuById(long menuId) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_RESTORE_MENU_BY_PRODUCT_ID)) {
            statement.setLong(1, menuId);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while restoring menu by product id: " + e.getMessage());
            throw new DaoException("Exception while restoring menu by product id ", e);
        }
    }

    @Override
    public List<Menu> findAllDeletedMenu() throws DaoException {
    	EntityRowMapper<Menu> mapper = MenuMapper.getInstance();
        List<Menu> menuList = new ArrayList<>();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_DELETED_MENU_PRODUCTS);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Optional<Menu> optionalMenu = mapper.mapRow(resultSet);
                optionalMenu.ifPresent(menuList::add);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while reading deleted menu: " + e.getMessage());
            throw new DaoException("Exception while reading deleted menu ", e);
        }
        return menuList;
    }

    @Override
    public boolean restoreAllMenuBySectionId(long sectionId) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_RESTORE_MENU_BY_SECTION_ID)) {
            statement.setLong(1, sectionId);
            return statement.executeUpdate() >= ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while restoring menu by section id ");
            throw new DaoException("Exception while restoring menu by section id ", e);
        }
    }
}