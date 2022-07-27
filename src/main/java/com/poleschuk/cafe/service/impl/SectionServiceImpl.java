package com.poleschuk.cafe.service.impl;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.dao.BaseDao;
import com.poleschuk.cafe.model.dao.EntityTransaction;
import com.poleschuk.cafe.model.dao.impl.MenuDaoImpl;
import com.poleschuk.cafe.model.dao.impl.SectionDaoImpl;
import com.poleschuk.cafe.model.entity.Section;
import com.poleschuk.cafe.service.SectionService;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * SectionServiceImpl class implements SectionService interface and contains
 * business logic for section processing.
 */
public class SectionServiceImpl implements SectionService {
    private static final Logger logger = LogManager.getLogger();
    private static final SectionServiceImpl instance = new SectionServiceImpl();

    private SectionServiceImpl() {}

    public static SectionServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Section> findAllSections() throws ServiceException {
        BaseDao<Section> abstractDao = new SectionDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(abstractDao);
        try {
            return abstractDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findAllSections method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean addNewSection(String sectionName) throws ServiceException {
        BaseDao<Section> abstractDao = new SectionDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(abstractDao);
        try {
            return abstractDao.insert(new Section(sectionName, true));
        } catch (DaoException e) {
            throw new ServiceException("Exception in a addNewSection method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public Optional<Section> findSectionByName(String sectionName) throws ServiceException {
        SectionDaoImpl sectionDao = new SectionDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(sectionDao);
        try {
            return sectionDao.findSectionByName(sectionName);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findSectionByName method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public Optional<Section> updateSectionName(Section newSection) throws ServiceException {
        BaseDao<Section> abstractDao = new SectionDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(abstractDao);
        try {
            return abstractDao.update(newSection);
        } catch (DaoException e) {
            throw new ServiceException("Exception in a updateSectionName method. ", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean deleteSectionById(long sectionId) throws ServiceException {
        BaseDao<Section> abstractDao = new SectionDaoImpl();
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(abstractDao, menuDao);
        boolean isDelete;
        try {
            isDelete = abstractDao.delete(sectionId);
            menuDao.deleteMenuBySectionId(sectionId);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException("Exception in a deleteSectionById method. ", e);
        } finally {
            transaction.endTransaction();
        }
        return isDelete;
    }

    @Override
    public List<Section> findAllDeletedSections() throws ServiceException {
        SectionDaoImpl sectionDao = new SectionDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(sectionDao);
        try {
            return sectionDao.findAllDeletedSections();
        } catch (DaoException e) {
            throw new ServiceException("Exception in a findAllRemovingSections method", e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean restoreSectionById(long sectionId) throws ServiceException {
        SectionDaoImpl sectionDao = new SectionDaoImpl();
        MenuDaoImpl menuDao = new MenuDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(sectionDao, menuDao);
        boolean isRestore;
        try {
            isRestore = sectionDao.restoreSectionById(sectionId);
            logger.log(Level.INFO, "isRestore = " + isRestore);
            menuDao.restoreAllMenuBySectionId(sectionId);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException("Exception in a restoreSectionById service method ", e);
        } finally {
            transaction.endTransaction();
        }
        return isRestore;
    }
}