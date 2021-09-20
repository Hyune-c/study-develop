package com.example.dmaker.service;

import static com.example.dmaker.code.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.example.dmaker.code.DMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH;
import static com.example.dmaker.code.DMakerErrorCode.NO_DEVELOPER;
import static com.example.dmaker.constant.DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS;
import static com.example.dmaker.constant.DMakerConstant.MIN_SENIOR_EXPERIENCE_YEARS;
import static com.example.dmaker.type.DeveloperLevel.JUNGNIOR;
import static com.example.dmaker.type.DeveloperLevel.JUNIOR;
import static com.example.dmaker.type.DeveloperLevel.SENIOR;

import com.example.dmaker.code.StatusCode;
import com.example.dmaker.dto.CreateDeveloper;
import com.example.dmaker.dto.DeveloperDetailDto;
import com.example.dmaker.dto.DeveloperDto;
import com.example.dmaker.dto.EditDeveloper;
import com.example.dmaker.entity.Developer;
import com.example.dmaker.entity.RetiredDeveloper;
import com.example.dmaker.exception.DMakerException;
import com.example.dmaker.repository.DeveloperRepository;
import com.example.dmaker.repository.RetiredDeveloperRepository;
import com.example.dmaker.type.DeveloperLevel;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class DMakerService {

	private final DeveloperRepository developerRepository;
	private final RetiredDeveloperRepository retiredDeveloperRepository;

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

	private void validateCreateDeveloperRequest(final CreateDeveloper.Request request) {
		validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

		developerRepository.findByMemberId(request.getMemberId())
				.ifPresent(developer -> {
					throw new DMakerException(DUPLICATED_MEMBER_ID);
				});
	}

	private void validateDeveloperLevel(final DeveloperLevel developerLevel, final Integer experienceYears) {
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

	public List<DeveloperDto> getAllEmployedDevelopers() {
		return developerRepository.findDevelopersByStatusEquals(StatusCode.EMPLOYED)
				.stream().map(DeveloperDto::fromEntity)
				.collect(Collectors.toList());
	}

	public DeveloperDetailDto getDeveloper(final String memberId) {
		return developerRepository.findByMemberId(memberId)
				.map(DeveloperDetailDto::fromEntity)
				.orElseThrow(() -> new DMakerException(NO_DEVELOPER));
	}

	@Transactional
	public DeveloperDetailDto editDeveloper(final String memberId, final EditDeveloper.Request request) {
		validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

		final Developer developer = developerRepository.findByMemberId(memberId)
				.orElseThrow(() -> new DMakerException(NO_DEVELOPER));
		developer.setLevel(request.getDeveloperLevel());
		developer.setSkillType(request.getDeveloperSkillType());
		developer.setExperienceYears(request.getExperienceYears());
		developer.setName(request.getName());
		developer.setAge(request.getAge());

		return DeveloperDetailDto.fromEntity(developer);
	}

	@Transactional
	public DeveloperDetailDto deleteDeveloper(final String memberId) {
		final Developer developer = developerRepository.findByMemberId(memberId)
				.orElseThrow(() -> new DMakerException(NO_DEVELOPER));
		developer.setStatus(StatusCode.RETIRED);

		final RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
				.memberId(developer.getMemberId())
				.name(developer.getName())
				.build();
		retiredDeveloperRepository.save(retiredDeveloper);

		return DeveloperDetailDto.fromEntity(developer);
	}
}
