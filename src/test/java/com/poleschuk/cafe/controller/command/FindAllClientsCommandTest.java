package com.poleschuk.cafe.controller.command;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.impl.admin.FindAllClientsCommand;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.model.entity.UserRole;
import com.poleschuk.cafe.model.entity.UserState;
import com.poleschuk.cafe.service.UserService;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.internal.WhiteboxImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.testng.Assert.*;

public class FindAllClientsCommandTest {

	public static final int PAGE_SIZE = 15;
	public static final int OFFSET = 0;
	
	@Mock
    UserService mockService;
	
	@Mock
    HttpServletRequest mock;
	
    FindAllClientsCommand command;

    @BeforeMethod
    public void setUp(){
        command = new FindAllClientsCommand();
		MockitoAnnotations.initMocks(this);
        WhiteboxImpl.setInternalState(command, "userService", mockService);
    }

    @DataProvider(name = "users")
    public Object[][] signInData() {
        return new Object[][]{
                {List.of(new User(4, "Maxim", "Poleschuk", "maxipole",
                        "6d07a1ebf9c8aca9eb0366d39c13d3c4", "maxipole@gmail.com", 336998919,
                        LocalDate.parse("1989-03-28"), BigDecimal.ZERO, BigDecimal.ZERO,
                        BigDecimal.ZERO, UserRole.CLIENT, UserState.ACTIVE)),
                 "/jsp/pages/admin/clients.jsp"},
        };
    }

    @Test(dataProvider = "users")
    public void findAllClientsTest(List<User> userList, String expected) throws CommandException, ServiceException {
        Mockito.when(mockService.findClientSublist(PAGE_SIZE, OFFSET))
                .thenReturn(userList);
        Router router = command.execute(mock);
        Mockito.verify(mockService, Mockito.times(1)).findClientSublist(PAGE_SIZE, OFFSET);
        assertEquals(router.getPage(), expected);
    }

}