package com.poleschuk.cafe.controller.command;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.impl.admin.FindAllAdminsCommand;
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

public class FindAllAdminsCommandTest {
	
	@Mock
    UserService mockService;
	
	@Mock
    HttpServletRequest mock;
	
    FindAllAdminsCommand command;

    @BeforeMethod
    public void setUp(){
        command = new FindAllAdminsCommand();
		MockitoAnnotations.initMocks(this);
        WhiteboxImpl.setInternalState(command, "service", mockService);
    }

    @DataProvider(name = "users")
    public Object[][] signInData() {
        return new Object[][] {
                {List.of(new User(4, "Max", "Paliashchuk", "admin1",
                        "6d07a1ebf9c8aca9eb0366d39c13d3c4", "maxipole2@gmail.com", 336998920,
                        LocalDate.parse("1989-03-28"), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                        UserRole.ADMIN, UserState.ACTIVE)),
                 "/jsp/pages/admin/admins.jsp"},
        };
    }

    @Test(dataProvider = "users")
    public void findAllClientsTest(List<User> userList, String expected) throws CommandException, ServiceException {
        Mockito.when(mockService.findAllAdmins())
                .thenReturn(userList);
        Router router = command.execute(mock);
        Mockito.verify(mockService, Mockito.times(1)).findAllAdmins();
        assertEquals(router.getPage(), expected);
    }

}