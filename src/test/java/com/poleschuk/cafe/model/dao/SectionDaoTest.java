package com.poleschuk.cafe.model.dao;

import static org.testng.Assert.assertEquals;

import java.util.Optional;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.model.dao.impl.SectionDaoImpl;
import com.poleschuk.cafe.model.entity.Section;
import com.poleschuk.cafe.model.pool.ConnectionPool;

public class SectionDaoTest {
	private static final SectionDaoImpl SECTION_DAO = new SectionDaoImpl();
	private static final String SECTION_NAME = "Drinks (test)";
	private static Section section = new Section(31, SECTION_NAME, false);
    private static EntityTransaction transaction = new EntityTransaction();

    @BeforeMethod
    public void setUp() throws DaoException {
		ConnectionPool.getInstance();
        transaction.init(SECTION_DAO);
    	SECTION_DAO.insert(section);
    	Optional<Section> optionalSection = SECTION_DAO.findSectionByName(section.getSectionName());
    	section = optionalSection.get();
    }
    
    @AfterMethod
    public void tearDown() throws DaoException {
    	SECTION_DAO.delete(section.getSectionId());
    	transaction.end();
		ConnectionPool.getInstance()
				.destroyPool();
    }

    @Test
    public void findUserByEmailTest() throws DaoException {
    	Optional<Section> optionalUser = SECTION_DAO.findEntityById(section.getSectionId());
    	assertEquals(optionalUser.get()
    			.getSectionName(), section.getSectionName());
    }

}
