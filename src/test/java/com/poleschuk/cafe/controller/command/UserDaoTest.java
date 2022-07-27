package com.poleschuk.cafe.controller.command;

import static org.testng.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.model.dao.EntityTransaction;
import com.poleschuk.cafe.model.dao.impl.UserDaoImpl;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.model.entity.UserRole;
import com.poleschuk.cafe.model.entity.UserState;
import com.poleschuk.cafe.model.pool.ConnectionPool;

public class UserDaoTest {
	private static final UserDaoImpl USER_DAO = new UserDaoImpl();
	private static User user = new User(4, "Maxim", "Poleschuk", "test_maxipole",
            "6d07a1ebf9c8aca9eb0366d39c13d3c4", "maxipole@gmail.com", 336998919,
            LocalDate.parse("1989-03-28"), BigDecimal.ZERO, BigDecimal.ZERO,
            BigDecimal.ZERO, UserRole.CLIENT, UserState.ACTIVE);
    private static EntityTransaction transaction = new EntityTransaction();

    @BeforeMethod
    public void setUp() throws DaoException {
		ConnectionPool.getInstance();
        transaction.init(USER_DAO);
    	USER_DAO.insert(user);
    	Optional<User> optionalUser = USER_DAO.findUserByLogin(user.getLogin());
    	user = optionalUser.get();
    }
    
    @AfterMethod
    public void tearDown() throws DaoException {
    	USER_DAO.delete(user.getUserId());
    	transaction.end();
		ConnectionPool.getInstance()
				.destroyPool();
    }

    @Test
    public void findUserByEmailTest() throws DaoException {
    	Optional<User> optionalUser = USER_DAO.findUserByEmail(user.getEmail());
    	assertEquals(optionalUser.get()
    			.getUserId(), user.getUserId());
    }

}
