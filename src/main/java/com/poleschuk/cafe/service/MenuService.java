package com.poleschuk.cafe.service;


import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.Menu;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface MenuService provides functions to work with cafe menu.
 */
public interface MenuService {
    /**
     * Find menu sublist.
     *
     * @param pageSize the page size
     * @param offset   the offset
     * @return the sublist
     * @throws ServiceException the service exception
     */
    List<Menu> findMenuSublist(int pageSize, int offset) throws ServiceException;

    /**
     * Find menu sublist by section id.
     *
     * @param pageSize  the page size
     * @param offset    the offset
     * @param sectionId the section id
     * @return the sublist
     * @throws ServiceException the service exception
     */
    List<Menu> findMenuSublistBySectionId(int pageSize, int offset, long sectionId) throws ServiceException;

    /**
     * Add new product.
     *
     * @param map          the map
     * @param defaultImage the default image
     * @return the boolean true, if result was successful
     * @throws ServiceException the service exception
     */
    boolean addNewProduct(Map<String, String> map, String defaultImage) throws ServiceException;

    /**
     * Update product photo.
     *
     * @param image the image
     * @param name  the name
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateProductPhoto(String image, String name) throws ServiceException;

    /**
     * Find product by id.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Menu> findProductById(long id) throws ServiceException;

    /**
     * Delete product by id.
     *
     * @param id the id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean deleteProductById(long id) throws ServiceException;

    /**
     * Update product.
     *
     * @param id the id
     * @param updateData the update data
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Menu> updateProduct(long id, Map<String, String> updateData) throws ServiceException;

    /**
     * Delete product from cart.
     *
     * @param map the map
     * @param id the id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean deleteProductFromCart(Map<Menu, Integer> map, long id) throws ServiceException;

    /**
     * Adds the product to cart.
     *
     * @param map the map
     * @param id the id
     * @param numberProduct the number product
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean addProductToCart(Map<Menu, Integer> map, long id, int numberProduct) throws ServiceException;

    /**
     * Read row count.
     *
     * @return the int
     * @throws ServiceException the service exception
     */
    int readRowCount() throws ServiceException;

    /**
     * Sort all menu by price.
     *
     * @param pageSize the page size
     * @param offset the offset
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Menu> sortAllMenuByPrice(int pageSize, int offset) throws ServiceException;

    /**
     * Sort section menu by price.
     *
     * @param pageSize the page size
     * @param offset the offset
     * @param sectionId the section id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Menu> sortSectionMenuByPrice(int pageSize, int offset, long sectionId) throws ServiceException;

    /**
     * Read row count by section.
     *
     * @param sectionId the section id
     * @return the int
     * @throws ServiceException the service exception
     */
    int readRowCountBySection(long sectionId) throws ServiceException;

    /**
     * Find all deleted menu.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Menu> findAllDeletedMenu() throws ServiceException;

    /**
     * Restore menu product by id.
     *
     * @param menuId the menu id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean restoreMenuProductById(long menuId) throws ServiceException;

    /**
     * Find sorted menu sub list by popularity.
     *
     * @param pageSize the page size
     * @param offset the offset
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Menu> findSortedMenuSubListByPopularity(int pageSize, int offset) throws ServiceException;

    /**
     * Find sorted menu section sub list by popularity.
     *
     * @param pageSize the page size
     * @param offset the offset
     * @param sectionId the section id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Menu> findSortedMenuSectionSubListByPopularity(int pageSize, int offset, long sectionId) throws ServiceException;
}