package com.poleschuk.cafe.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.dao.impl.UserDaoImpl;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.model.entity.UserRole;
import com.poleschuk.cafe.model.entity.UserState;
import com.poleschuk.cafe.service.impl.UserServiceImpl;

public class UserServiceTest {
	private static final String LOGIN = "maxipole";
	private static final String PASSWORD = "6d07a1ebf9c8aca9eb0366d39c13d3c4";
	private static final User USER = new User(4, "Maxim", "Poleschuk", "maxipole",
            "6d07a1ebf9c8aca9eb0366d39c13d3c4", "maxipole@gmail.com", 336998919,
            LocalDate.parse("1989-03-28"), BigDecimal.ZERO, BigDecimal.ZERO,
            BigDecimal.ZERO, UserRole.CLIENT, UserState.ACTIVE);

    @Test
    public void authenticationTest() throws CommandException, ServiceException {
        try (MockedConstruction<UserDaoImpl> mocked = Mockito.mockConstruction(UserDaoImpl.class,
		      (mock, context) -> {
		          when(mock.findUserByLoginAndPassword(anyString(), anyString())).thenReturn(Optional.of(USER));
		      })) {

	    UserServiceImpl userService = UserServiceImpl.getInstance();
	    Optional<User> optionalUser = userService.authenticate(LOGIN, PASSWORD);
	    assertEquals(USER, optionalUser.get());
        }
    }

}
