package com.poleschuk.cafe.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.Optional;

import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.poleschuk.cafe.exception.CommandException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.dao.impl.SectionDaoImpl;
import com.poleschuk.cafe.model.entity.Section;
import com.poleschuk.cafe.service.impl.SectionServiceImpl;

public class SectionServiceTest {
	private static final String SECTION_NAME = "Drinks";
	private static final Section SECTION = new Section(31, SECTION_NAME, false);

    @Test
    public void findSectionByNameTest() throws CommandException, ServiceException {
        try (MockedConstruction<SectionDaoImpl> mocked = Mockito.mockConstruction(SectionDaoImpl.class,
		      (mock, context) -> {
		          when(mock.findSectionByName(anyString())).thenReturn(Optional.of(SECTION));
		      })) {

	    SectionServiceImpl sectionService = SectionServiceImpl.getInstance();
	    Optional<Section> optionalSection = sectionService.findSectionByName(SECTION_NAME);
	    assertEquals(SECTION, optionalSection.get());
        }
    }

}
