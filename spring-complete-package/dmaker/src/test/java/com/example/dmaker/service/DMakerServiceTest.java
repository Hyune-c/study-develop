package com.example.dmaker.service;

import static com.example.dmaker.code.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.example.dmaker.code.DMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH;
import static com.example.dmaker.constant.DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS;
import static com.example.dmaker.constant.DMakerConstant.MIN_SENIOR_EXPERIENCE_YEARS;
import static com.example.dmaker.entity.DeveloperMock.createDeveloper;
import static com.example.dmaker.type.DeveloperLevel.JUNGNIOR;
import static com.example.dmaker.type.DeveloperLevel.JUNIOR;
import static com.example.dmaker.type.DeveloperLevel.SENIOR;
import static com.example.dmaker.type.DeveloperSkillType.FRONT_END;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.dmaker.dto.CreateDeveloper;
import com.example.dmaker.dto.DeveloperDetailDto;
import com.example.dmaker.entity.Developer;
import com.example.dmaker.exception.DMakerException;
import com.example.dmaker.repository.DeveloperRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DMakerServiceTest {

	@Mock
	private DeveloperRepository developerRepository;

	@InjectMocks
	private DMakerService dMakerService;

	@Test
	void getDeveloperTest() {
		// given
		final Developer juniorFEDeveloper = createDeveloper(JUNIOR, FRONT_END, MAX_JUNIOR_EXPERIENCE_YEARS, "memberId");
		given(developerRepository.findByMemberId(anyString()))
				.willReturn(Optional.of(juniorFEDeveloper));

		// when
		final DeveloperDetailDto developer = dMakerService.getDeveloper("memberId");

		// then
		assertEquals(JUNIOR, developer.getDeveloperLevel());
		assertEquals(FRONT_END, developer.getDeveloperSkillType());
		assertEquals(MAX_JUNIOR_EXPERIENCE_YEARS, developer.getExperienceYears());
	}

	@Test
	void createDeveloperTest_success() {
		// given
		final CreateDeveloper.Request request = CreateDeveloper.Request.builder()
				.developerLevel(JUNGNIOR)
				.developerSkillType(FRONT_END)
				.experienceYears(7)
				.memberId("memberId")
				.name("name")
				.age(28)
				.build();
		final ArgumentCaptor<Developer> captor = ArgumentCaptor.forClass(Developer.class);

		// when
		final CreateDeveloper.Response response = dMakerService.createDeveloper(request);

		// then
		verify(developerRepository, times(1))
				.save(captor.capture());
		final Developer savedDeveloper = captor.getValue();
		assertEquals(JUNGNIOR, savedDeveloper.getLevel());
		assertEquals(FRONT_END, savedDeveloper.getSkillType());
		assertEquals(7, savedDeveloper.getExperienceYears());

		assertEquals(JUNGNIOR, response.getDeveloperLevel());
		assertEquals(FRONT_END, response.getDeveloperSkillType());
		assertEquals(7, response.getExperienceYears());
	}

	@Test
	void createDeveloperTest_failed_with_duplicated() {
		// given
		final Developer juniorFEDeveloper = createDeveloper(JUNIOR, FRONT_END, MAX_JUNIOR_EXPERIENCE_YEARS, "memberId");
		final CreateDeveloper.Request request = CreateDeveloper.Request.builder()
				.developerLevel(JUNIOR)
				.developerSkillType(FRONT_END)
				.experienceYears(3)
				.memberId("memberId")
				.name("name")
				.age(28)
				.build();
		given(developerRepository.findByMemberId(anyString()))
				.willReturn(Optional.of(juniorFEDeveloper));

		// when
		final DMakerException exception = assertThrows(DMakerException.class, () -> dMakerService.createDeveloper(request));

		// then
		assertEquals(DUPLICATED_MEMBER_ID, exception.getDMakerErrorCode());
	}

	@Test
	void createDeveloperTest_failed_with_invalid_experience() {
		// given
		final CreateDeveloper.Request request = CreateDeveloper.Request.builder()
				.developerLevel(SENIOR)
				.developerSkillType(FRONT_END)
				.experienceYears(MIN_SENIOR_EXPERIENCE_YEARS - 3)
				.memberId("memberId")
				.name("name")
				.age(28)
				.build();

		// when
		final DMakerException exception = assertThrows(DMakerException.class, () -> dMakerService.createDeveloper(request));

		// then
		assertEquals(LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH, exception.getDMakerErrorCode());
	}
}
