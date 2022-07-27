package com.poleschuk.cafe.controller.command;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.internal.WhiteboxImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.impl.admin.DeleteAdminCommand;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import static com.poleschuk.cafe.controller.CommandPath.FIND_ALL_ADMINS_URL;
import static org.testng.Assert.assertEquals;

public class DeleteAdminCommandTest {
	private static final long TEST_ID = 1;
	private static final String TEST_STRING_ID = "1";
	
	@Mock
    UserService mockService;
	
	@Mock
    HttpServletRequest mock;
	
    DeleteAdminCommand command;

    @BeforeMethod
    public void setUp() {
        command = new DeleteAdminCommand();
		MockitoAnnotations.initMocks(this);
        WhiteboxImpl.setInternalState(command, "service", mockService);
    }

    @Test
    public void findAllClientsTest() throws CommandException, ServiceException {
        Mockito.when(mockService.deleteAdmin(Mockito.anyLong()))
                .thenReturn(true);
        Mockito.when(mock.getParameter(Mockito.anyString())).thenReturn(TEST_STRING_ID);

        Router router = command.execute(mock);
        Mockito.verify(mockService, Mockito.times(1)).deleteAdmin(TEST_ID);
        assertEquals(router.getUrl()
        		.get(), FIND_ALL_ADMINS_URL);
    }

}