package com.poleschuk.cafe.controller.command;


import java.math.BigDecimal;
import java.util.List;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.internal.WhiteboxImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.impl.admin.FindAllDeletedProductsCommand;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.Menu;
import com.poleschuk.cafe.service.MenuService;

import jakarta.servlet.http.HttpServletRequest;

import static com.poleschuk.cafe.controller.PagePath.*;
import static org.testng.Assert.assertEquals;

public class FindAllDeletedProductsCommandTest {
	
	@Mock
    MenuService mockService;
	
	@Mock
    HttpServletRequest mock;
	
    FindAllDeletedProductsCommand command;

    @BeforeMethod
    public void setUp() {
        command = new FindAllDeletedProductsCommand();
		MockitoAnnotations.initMocks(this);
        WhiteboxImpl.setInternalState(command, "menuService", mockService);
    }

    @DataProvider(name = "menus")
    public Object[][] menuData() {
        return new Object[][]{
                {List.of(new Menu(1, "Блинчики с творогом", "",
                		"Блинчики, творог, яйцо, сахарная пудра, соус клубничный, сметана, мята",
                        286, BigDecimal.valueOf(54.0), BigDecimal.valueOf(5.40),
                        2, true)),
                	RESTORE_PAGE},
        };
    }

    @Test(dataProvider = "menus")
    public void blockUserByIdTest(List<Menu> menuList, String expected) throws CommandException, ServiceException {
        Mockito.when(mockService.findAllDeletedMenu())
        		.thenReturn(menuList);
        Router router = command.execute(mock);
        Mockito.verify(mockService, Mockito.times(1)).findAllDeletedMenu();
        assertEquals(router.getPage(), expected);
    }

}