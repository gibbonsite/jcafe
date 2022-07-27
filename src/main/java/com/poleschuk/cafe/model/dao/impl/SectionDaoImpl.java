package com.poleschuk.cafe.model.dao.impl;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.model.dao.BaseDao;
import com.poleschuk.cafe.model.dao.SectionDao;
import com.poleschuk.cafe.model.entity.Section;
import com.poleschuk.cafe.model.mapper.EntityRowMapper;
import com.poleschuk.cafe.model.mapper.impl.SectionMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * SectionDaoImpl class implements SectionDao interface and executes requests
 * to the DB.
 */
public class SectionDaoImpl extends BaseDao<Section> implements SectionDao {
    private static final Logger logger = LogManager.getLogger();
    private static final int ONE_UPDATE = 1;
    private static final String SQL_SELECT_ALL_SECTIONS = """
            SELECT section_id, section_name, enabled FROM sections
            WHERE enabled = true ORDER BY section_id ASC""";
    private static final String SQL_INSERT_NEW_SECTION = """
            INSERT INTO sections(section_name, enabled) VALUES (?, ?)""";
    private static final String SQL_SELECT_SECTION_BY_NAME = """
            SELECT section_id, section_name, enabled FROM sections
            WHERE section_name = (?)""";
    private static final String SQL_SELECT_SECTION_BY_ID = """
            SELECT section_id, section_name, enabled FROM sections
            WHERE section_id = (?)""";
    private static final String SQL_UPDATE_SECTION_NAME = """
            UPDATE sections
            SET section_name = (?)
            WHERE section_id = (?)""";
    private static final String SQL_DELETE_SECTION_BY_ID = """
            UPDATE sections
            SET enabled = false
            WHERE section_id = (?)""";
    private static final String SQL_SELECT_ALL_REMOVING_SECTIONS = """
            SELECT section_id, section_name, enabled FROM sections
            WHERE enabled = false""";
    private static final String SQL_RESTORE_SECTION_BY_ID = """
            UPDATE sections
            SET enabled = true
            WHERE section_id = (?)""";

    @Override
    public List<Section> findAll() throws DaoException {
    	EntityRowMapper<Section> mapper = SectionMapper.getInstance();
        List<Section> sectionList = new ArrayList<>();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_SECTIONS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Section> optionalSection = mapper.mapRow(resultSet);
                    optionalSection.ifPresent(sectionList::add);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find all sections ");
            throw new DaoException("Exception in a findAll method. ", e);
        }
        return sectionList;
    }

    @Override
    public Optional<Section> findEntityById(long id) throws DaoException {
    	EntityRowMapper<Section> mapper = SectionMapper.getInstance();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_SECTION_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find section by id method ");
            throw new DaoException("Exception in a findEntityById section method. ", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(long id) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_DELETE_SECTION_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while delete section method ");
            throw new DaoException("Exception in a delete section method. ", e);
        }
    }


    @Override
    public boolean insert(Section entity) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_INSERT_NEW_SECTION)) {
            statement.setString(1, entity.getSectionName());
            statement.setBoolean(2, entity.isEnabled());
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while create new section  ");
            throw new DaoException("Exception in a create section method. ", e);
        }
    }

    @Override
    public Optional<Section> update(Section entity) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_UPDATE_SECTION_NAME)) {
            Optional<Section> section = findEntityById(entity.getSectionId());
            statement.setString(1, entity.getSectionName());
            statement.setLong(2, entity.getSectionId());
            return statement.executeUpdate() == ONE_UPDATE ? section : Optional.empty();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while update section ");
            throw new DaoException("Exception in a update section method. ", e);
        }
    }

    @Override
    public Optional<Section> findSectionByName(String sectionName) throws DaoException {
    	EntityRowMapper<Section> mapper = SectionMapper.getInstance();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_SECTION_BY_NAME)) {
            statement.setString(1, sectionName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while find section by name ");
            throw new DaoException("Exception in a findSectionByName method. ", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Section> findAllDeletedSections() throws DaoException {
    	EntityRowMapper<Section> mapper = SectionMapper.getInstance();
        List<Section> sectionList = new ArrayList<>();
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_REMOVING_SECTIONS);
        ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Optional<Section> optionalSection = mapper.mapRow(resultSet);
                optionalSection.ifPresent(sectionList::add);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while reading removing sections ");
            throw new DaoException("Exception while reading removing sections ", e);
        }
        return sectionList;
    }

    @Override
    public boolean restoreSectionById(long sectionId) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_RESTORE_SECTION_BY_ID)) {
            statement.setLong(1, sectionId);
            return statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception while restoring section ");
            throw new DaoException("Exception while restoring section ", e);
        }
    }
}