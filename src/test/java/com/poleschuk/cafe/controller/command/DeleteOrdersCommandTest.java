package com.poleschuk.cafe.controller.command;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.internal.WhiteboxImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.impl.admin.DeleteOrdersCommand;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

import static com.poleschuk.cafe.controller.CommandPath.FIND_ALL_ORDERS_URL;
import static org.testng.Assert.assertEquals;

public class DeleteOrdersCommandTest {
	
	@Mock
    OrderService mockService;
	
	@Mock
    HttpServletRequest mock;
    
	DeleteOrdersCommand command;

    @BeforeMethod
    public void setUp() {
        command = new DeleteOrdersCommand();
		MockitoAnnotations.initMocks(this);
        WhiteboxImpl.setInternalState(command, "service", mockService);
    }

    @Test
    public void deleteOldOrdersTest() throws CommandException, ServiceException {
        Mockito.when(mockService.deleteOldOrders())
                .thenReturn(true);

        Router router = command.execute(mock);
        Mockito.verify(mockService, Mockito.times(1)).deleteOldOrders();
        assertEquals(router.getUrl()
        		.get(), FIND_ALL_ORDERS_URL);
    }
}
