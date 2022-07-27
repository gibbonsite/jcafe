package com.poleschuk.cafe.controller.command;


import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.internal.WhiteboxImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.impl.admin.DeleteProductCommand;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.service.MenuService;

import jakarta.servlet.http.HttpServletRequest;

import static com.poleschuk.cafe.controller.CommandPath.FIND_ALL_MENU_URL;
import static org.testng.Assert.assertEquals;

public class DeleteProductCommandTest {
	private static final long TEST_ID = 1;
	private static final String TEST_STRING_ID = "1";
	
	@Mock
    MenuService mockService;
	
	@Mock
    HttpServletRequest mock;
	
    DeleteProductCommand command;

    @BeforeMethod
    public void setUp() {
        command = new DeleteProductCommand();
		MockitoAnnotations.initMocks(this);
        WhiteboxImpl.setInternalState(command, "service", mockService);
    }

    @Test
    public void deleteProductByIdTest() throws CommandException, ServiceException {
        Mockito.when(mockService.deleteProductById(Mockito.anyLong()))
        		.thenReturn(true);
        Mockito.when(mock.getParameter(Mockito.anyString())).thenReturn(TEST_STRING_ID);
        Router router = command.execute(mock);
        Mockito.verify(mockService, Mockito.times(1)).deleteProductById(TEST_ID);
        assertEquals(router.getUrl()
        		.get(), FIND_ALL_MENU_URL);
    }

}