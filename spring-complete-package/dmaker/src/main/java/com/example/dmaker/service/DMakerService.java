package com.example.dmaker.service;

import static com.example.dmaker.code.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.example.dmaker.code.DMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH;
import static com.example.dmaker.constant.DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS;
import static com.example.dmaker.constant.DMakerConstant.MIN_SENIOR_EXPERIENCE_YEARS;
import static com.example.dmaker.type.DeveloperLevel.JUNGNIOR;
import static com.example.dmaker.type.DeveloperLevel.JUNIOR;
import static com.example.dmaker.type.DeveloperLevel.SENIOR;

import com.example.dmaker.dto.CreateDeveloper;
import com.example.dmaker.entity.Developer;
import com.example.dmaker.exception.DMakerException;
import com.example.dmaker.repository.DeveloperRepository;
import com.example.dmaker.type.DeveloperLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DMakerService {

	private final DeveloperRepository developerRepository;

	@Transactional
	public CreateDeveloper.Response createDeveloper(final CreateDeveloper.Request request) {
		validateCreateDeveloperRequest(request);

		final Developer developer = Developer.builder()
				.level(request.getDeveloperLevel())
				.skillType(request.getDeveloperSkillType())
				.experienceYears(request.getExperienceYears())
				.memberId(request.getMemberId())
				.name(request.getName())
				.age(request.getAge())
				.build();
		developerRepository.save(developer);
		return CreateDeveloper.Response.fromEntity(developer);
	}

	private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
		developerRepository.findByMemberId(request.getMemberId())
				.ifPresent(developer -> {
					throw new DMakerException(DUPLICATED_MEMBER_ID);
				});

		final DeveloperLevel developerLevel = request.getDeveloperLevel();
		final Integer experienceYears = request.getExperienceYears();
		
		if (developerLevel == SENIOR
				&& experienceYears < MIN_SENIOR_EXPERIENCE_YEARS) {
			throw new DMakerException(LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH);
		}

		if (developerLevel == JUNGNIOR
				&& (experienceYears > MIN_SENIOR_EXPERIENCE_YEARS
				|| experienceYears < MAX_JUNIOR_EXPERIENCE_YEARS)) {
			throw new DMakerException(LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH);
		}

		if (developerLevel == JUNIOR
				&& experienceYears > MAX_JUNIOR_EXPERIENCE_YEARS) {
			throw new DMakerException(LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH);
		}
	}
}
