package com.poleschuk.cafe.controller.command;

import java.util.List;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.internal.WhiteboxImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.poleschuk.cafe.controller.Router;
import com.poleschuk.cafe.controller.command.impl.admin.FindAllDeletedSectionsCommand;
import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.entity.Section;
import com.poleschuk.cafe.service.SectionService;

import jakarta.servlet.http.HttpServletRequest;
import static com.poleschuk.cafe.controller.PagePath.RESTORE_PAGE;
import static org.testng.Assert.assertEquals;

public class FindAllDeletedSectionsCommandTest {
	
	@Mock
    SectionService mockService;
	
	@Mock
    HttpServletRequest mock;
	
    FindAllDeletedSectionsCommand command;

    @BeforeMethod
    public void setUp() {
        command = new FindAllDeletedSectionsCommand();
		MockitoAnnotations.initMocks(this);
        WhiteboxImpl.setInternalState(command, "sectionService", mockService);
    }

    @DataProvider(name = "sections")
    public Object[][] signInData() {
        return new Object[][]{
                {List.of(new Section(1, "Завтраки", true)),
                	RESTORE_PAGE},
        };
    }

    @Test(dataProvider = "sections")
    public void blockUserByIdTest(List<Section> sectionList, String expected) throws CommandException, ServiceException {
        Mockito.when(mockService.findAllDeletedSections())
        		.thenReturn(sectionList);
        Router router = command.execute(mock);
        Mockito.verify(mockService, Mockito.times(1)).findAllDeletedSections();
        assertEquals(router.getPage(), expected);
    }
}
