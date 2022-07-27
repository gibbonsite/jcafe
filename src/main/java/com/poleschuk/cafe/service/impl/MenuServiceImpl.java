package com.poleschuk.cafe.service.impl;


import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.dao.BaseDao;
import com.poleschuk.cafe.model.dao.EntityTransaction;
import com.poleschuk.cafe.model.dao.impl.MenuDaoImpl;
import com.poleschuk.cafe.model.entity.Menu;
import com.poleschuk.cafe.service.MenuService;
import com.poleschuk.cafe.validator.Validator;
import com.poleschuk.cafe.validator.impl.ValidatorImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.poleschuk.cafe.controller.Parameter.*;

/**
 * MenuServiceImpl class implements MenuService interface and contains
 * business logic for menu products.
 */
public class MenuServiceImpl implements MenuService {
    private static final Logger logger = LogManager.getLogger();
    private static final MenuServiceImpl instance = new MenuServiceImpl();
    private final Validator validator = ValidatorImpl.getInstance();

    private MenuServiceImpl() {}

    /**
     * Get instance of the MenuServiceImpl class.
     *
     * @return the menu service
     */
    public static MenuServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Menu> findMenuSublist(int pageSize, int offset) throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        logger.log(Level.INFO, "Parameter: " + pageSize + " " + offset);
        try {
            return menuDao.findMenuSublist(pageSize, offset);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findMenuSublist method. ", e);
        }finally {
            transaction.end();
        }
    }

    @Override
    public boolean addNewProduct(Map<String,String> map, String defaultImage) throws ServiceException {
        if (!validator.checkProductData(map)) {
            return false;
        }
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.init(menuDao);
        try {
            String name = map.get(PRODUCT_NAME);
            if (menuDao.findMenuByName(name).isPresent()) {
                map.put(PRODUCT_NAME, NOT_UNIQ_PRODUCT_NAME);
                return false;
            }
            String description = map.get(PRODUCT_DESCRIPTION);
            int weight = Integer.parseInt(map.get(PRODUCT_WEIGHT));
            BigDecimal loyalScore = BigDecimal.valueOf(Double.parseDouble(map.get(PRODUCT_LOYAL_SCORE)));
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(map.get(PRODUCT_PRICE)));
            long sectionId = Long.parseLong(map.get(PRODUCT_SECTION));
            Menu menu = new Menu(name, defaultImage, description, weight, loyalScore, price, sectionId, true);
            return menuDao.insert(menu);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a addNewProduct service method ", e);
        }finally {
            entityTransaction.end();
        }
    }

    @Override
    public boolean updateProductPhoto(String image, String name) throws ServiceException{
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.init(menuDao);
        try {
            return menuDao.updateImagePathByName(name, image);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a updateProductPhoto service method ", e);
        } finally {
            entityTransaction.end();
        }
    }

    @Override
    public Optional<Menu> findProductById(long id) throws ServiceException{
        BaseDao<Menu> abstractDao = new MenuDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.init(abstractDao);
        try {
            return abstractDao.findEntityById(id);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findProductById service method", e);
        } finally {
            entityTransaction.end();
        }
    }

    /**
	@see deleteProductById method of the MenuService interface.
	*/
    @Override
    public boolean deleteProductById(long id) throws ServiceException{
        BaseDao<Menu> abstractDao = new MenuDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.init(abstractDao);
        try{
            return abstractDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a deleteProductById service method ", e);
        } finally {
            entityTransaction.end();
        }
    }

    @Override
    public Optional<Menu> updateProduct(long id, Map<String, String> updateData) throws ServiceException {
        if (!validator.checkProductData(updateData)) {
            return Optional.empty();
        }
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.init(menuDao);
        try {
            String name = updateData.get(PRODUCT_NAME);
            logger.log(Level.INFO, "name = " + name);
            if (menuDao.findMenuByName(name).isPresent() && menuDao.findEntityById(id).isPresent()) {
                Menu findMenu = menuDao.findMenuByName(name).get();
                if (!findMenu.getName().equals(menuDao.findEntityById(id).get().getName())) {
                    updateData.put(PRODUCT_NAME, NOT_UNIQ_PRODUCT_NAME);
                    return Optional.empty();
                }
            }
            String description = updateData.get(PRODUCT_DESCRIPTION);
            int weight = Integer.parseInt(updateData.get(PRODUCT_WEIGHT));
            BigDecimal loyalScore = BigDecimal.valueOf(Double.parseDouble(updateData.get(PRODUCT_LOYAL_SCORE)));
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(updateData.get(PRODUCT_PRICE)));
            long sectionId = Long.parseLong(updateData.get(PRODUCT_SECTION));
            Menu newMenu = new Menu(id, name, description, weight, loyalScore, price, sectionId, true);
            return menuDao.update(newMenu);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a updateProduct service method ", e);
        }finally {
            entityTransaction.end();
        }
    }

    @Override
    public boolean deleteProductFromCart(Map<Menu, Integer> map, long id) throws ServiceException{
        BaseDao<Menu> abstractDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(abstractDao);
        try {
            Optional<Menu> menu = abstractDao.findEntityById(id);
            if (menu.isPresent()) {
                return map.remove(menu.get()) != null;
            }

        } catch (DaoException e) {
            throw new ServiceException("Exception in a deleteProductFromBasket service method ", e);
        } finally {
            transaction.end();
        }
        return false;
    }

    @Override
    public boolean addProductToCart(Map<Menu, Integer> map, long id, int numberProduct) throws ServiceException{
        BaseDao<Menu> abstractDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(abstractDao);
        try {
            Optional<Menu> menu = abstractDao.findEntityById(id);
            if (menu.isPresent() && map.containsKey(menu.get())) {
                int value = map.get(menu.get()) + numberProduct;
                map.put(menu.get(), value);
                return true;
            }
            if (menu.isPresent()) {
                map.put(menu.get(), numberProduct);
                return true;
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception in a addProductToBasket service method ", e);
        } finally {
            transaction.end();
        }
        return false;
    }

    @Override
    public int readRowCount() throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        try {
            return menuDao.readRowCount();
        } catch (DaoException e) {
            throw new ServiceException("Exception in a readRowCount service method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<Menu> findMenuSublistBySectionId(int pageSize, int offset, long sectionId) throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        logger.log(Level.INFO, "Parameter: " + pageSize + " " + offset);
        try {
            return menuDao.findMenuSublistBySectionId(pageSize, offset, sectionId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findMenuSublist service method. ", e);
        }finally {
            transaction.end();
        }
    }

    @Override
    public List<Menu> sortAllMenuByPrice(int pageSize, int offset) throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        try {
            logger.log(Level.INFO, "page size = " + pageSize + " offset = " + offset);
            return menuDao.findAllSortedMenuByPrice(pageSize, offset);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a sortAllMenuByPrice service method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<Menu> sortSectionMenuByPrice(int pageSize, int offset, long sectionId) throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        try {
            return menuDao.findSortedSectionMenuByPrice(pageSize, offset, sectionId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a sortSectionMenuByPrice service method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public int readRowCountBySection(long sectionId) throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        try {
            return menuDao.readRowCountBySection(sectionId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a readRowCountBySection service method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<Menu> findAllDeletedMenu() throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        try {
            return menuDao.findAllDeletedMenu();
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findAllRemovingMenu service method ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean restoreMenuProductById(long menuId) throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        try {
            return menuDao.restoreMenuById(menuId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a restoreMenuProductById method ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<Menu> findSortedMenuSubListByPopularity(int pageSize, int offset) throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        try {
            return menuDao.findAllSortedMenuByPopularity(pageSize, offset);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findSortedMenuSubListByPopularity service method", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<Menu> findSortedMenuSectionSubListByPopularity(int pageSize, int offset, long sectionId) throws ServiceException {
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(menuDao);
        try {
            return menuDao.findAllSortedSectionMenuByPopularity(pageSize, offset, sectionId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findSortedMenuSectionSubListByPopularity service method", e);
        }finally {
            transaction.end();
        }
    }
}