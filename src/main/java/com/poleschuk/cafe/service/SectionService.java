package com.poleschuk.cafe.service;


import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.Section;

import java.util.List;
import java.util.Optional;

/**
 * SectionService interface works with sections.
 */
public interface SectionService {
    /**
     * Find all sections list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Section> findAllSections() throws ServiceException;

    /**
     * Add new section.
     *
     * @param sectionName the section name
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean addNewSection(String sectionName) throws ServiceException;

    /**
     * Find section by name.
     *
     * @param sectionName the section name
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Section> findSectionByName(String sectionName) throws ServiceException;

    /**
     * Update section name.
     *
     * @param newSection the new section
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Section> updateSectionName(Section newSection) throws ServiceException;

    /**
     * Delete section by id.
     *
     * @param sectionId the section id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean deleteSectionById(long sectionId) throws ServiceException;

    /**
     * Find all deleted sections list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Section> findAllDeletedSections() throws ServiceException;

    /**
     * Restore section by id boolean.
     *
     * @param sectionId the section id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean restoreSectionById(long sectionId) throws ServiceException;
}