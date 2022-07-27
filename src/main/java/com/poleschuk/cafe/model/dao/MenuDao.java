package com.poleschuk.cafe.model.dao;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.model.entity.Menu;

import java.util.List;
import java.util.Optional;

/**
 * MenuDao interface consists of operations with menu.
 */
public interface MenuDao {
    /**
     * Update image path by name.
     *
     * @param name      the menu name
     * @param filePath  the file path
     * @return boolean  result of the operation
     * @throws DaoException the dao exception
     */
    boolean updateImagePathByName(String name, String filePath) throws DaoException;

    /**
     * Find food by name.
     *
     * @param name   the menu name
     * @return optional, result of the operation
     * @throws DaoException the dao exception
     */
    Optional<Menu> findMenuByName(String name) throws DaoException;

    /**
     * Find menu sublist by section id.
     *
     * @param pageSize  the page size
     * @param offset    the offset
     * @param sectionId the section id
     * @return list     result of the operation
     * @throws DaoException the dao exception
     */
    List<Menu> findMenuSublistBySectionId(int pageSize, int offset, long sectionId) throws DaoException;

    /**
     * Find menu sublist.
     *
     * @param pageSize  the page size
     * @param offset    the offset
     * @return list     result of the operation
     * @throws DaoException the dao exception
     */
    List<Menu> findMenuSublist(int pageSize, int offset) throws DaoException;

    /**
     * Read row count.
     *
     * @return the int  result of the operation
     * @throws DaoException the dao exception
     */
    int readRowCount() throws DaoException;

    /**
     * Find sorted menu list by price.
     *
     * @param pageSize   the page size
     * @param offset     the offset
     * @return the list  result of the operation
     * @throws DaoException the dao exception
     */
    List<Menu> findAllSortedMenuByPrice(int pageSize, int offset) throws DaoException;

    /**
     * Find sorted section menu list.
     *
     * @param pageSize   the page size
     * @param offset     the offset
     * @param sectionId  the section id
     * @return the list  result of the operation
     * @throws DaoException the dao exception
     */
    List<Menu> findSortedSectionMenuByPrice(int pageSize, int offset, long sectionId) throws DaoException;

    /**
     * Find sorted menu by popularity.
     *
     * @param pageSize   the page size
     * @param offset     the offset
     * @return the list  result of the operation
     * @throws DaoException the dao exception
     */
    List<Menu> findAllSortedMenuByPopularity(int pageSize, int offset) throws DaoException;

    /**
     * Find sorted section menu by popularity.
     *
     * @param pageSize   the page size
     * @param offset     the offset
     * @param sectionId  the section id
     * @return the list  result of the operation
     * @throws DaoException the dao exception
     */
    List<Menu> findAllSortedSectionMenuByPopularity(int pageSize, int offset, long sectionId) throws DaoException;

    /**
     * Read row count by section.
     *
     * @param sectionId   the section id
     * @return int        result of the operation
     * @throws DaoException the dao exception
     */
    int readRowCountBySection(long sectionId) throws DaoException;

    /**
     * Delete menu by section id.
     *
     * @param sectionId    the section id
     * @return boolean     result of the operation
     * @throws DaoException the dao exception
     */
    boolean deleteMenuBySectionId(long sectionId) throws DaoException;

    /**
     * Restore menu by id.
     *
     * @param menuId     the menu id
     * @return boolean   result of the operation
     * @throws DaoException the dao exception
     */
    boolean restoreMenuById(long menuId) throws DaoException;

    /**
     * Find all deleted menu.
     *
     * @return the list  result of the operation
     * @throws DaoException the dao exception
     */
    List<Menu> findAllDeletedMenu() throws DaoException;

    /**
     * Restore all menu by section id.
     *
     * @param sectionId   the section id
     * @return boolean    result of the operation
     * @throws DaoException the dao exception
     */
    boolean restoreAllMenuBySectionId(long sectionId) throws DaoException;
}