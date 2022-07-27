package com.poleschuk.cafe.model.dao;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.model.entity.Section;

import java.util.List;
import java.util.Optional;

/**
 * SectionDao interface consists of operations with sections.
 */
public interface SectionDao {
    /**
     * Find section by name.
     *
     * @param sectionName the section name
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Section> findSectionByName(String sectionName) throws DaoException;

    /**
     * Find all deleted sections.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Section> findAllDeletedSections() throws DaoException;

    /**
     * Restore section by id.
     *
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean restoreSectionById(long sectionId) throws DaoException;
}