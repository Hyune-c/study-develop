package com.example.dmaker.service;

import static com.example.dmaker.code.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.example.dmaker.code.DMakerErrorCode.NO_DEVELOPER;

import com.example.dmaker.code.StatusCode;
import com.example.dmaker.dto.CreateDeveloper;
import com.example.dmaker.dto.CreateDeveloper.Request;
import com.example.dmaker.dto.DeveloperDetailDto;
import com.example.dmaker.dto.DeveloperDto;
import com.example.dmaker.dto.EditDeveloper;
import com.example.dmaker.entity.Developer;
import com.example.dmaker.entity.RetiredDeveloper;
import com.example.dmaker.exception.DMakerException;
import com.example.dmaker.repository.DeveloperRepository;
import com.example.dmaker.repository.RetiredDeveloperRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
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

		final Developer developer = createDeveloperFromRequest(request);
		developerRepository.save(developer);
		return CreateDeveloper.Response.fromEntity(developer);
	}

	private Developer createDeveloperFromRequest(final Request request) {
		return Developer.builder()
				.level(request.getDeveloperLevel())
				.skillType(request.getDeveloperSkillType())
				.experienceYears(request.getExperienceYears())
				.memberId(request.getMemberId())
				.name(request.getName())
				.age(request.getAge())
				.build();
	}

	private void validateCreateDeveloperRequest(@NonNull final CreateDeveloper.Request request) {
		request.getDeveloperLevel().validateExperienceYears(request.getExperienceYears());

		developerRepository.findByMemberId(request.getMemberId())
				.ifPresent(developer -> {
					throw new DMakerException(DUPLICATED_MEMBER_ID);
				});
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
		request.getDeveloperLevel().validateExperienceYears(request.getExperienceYears());

		final Developer developer = getDeveloperByMemberId(memberId);
		developer.setLevel(request.getDeveloperLevel());
		developer.setSkillType(request.getDeveloperSkillType());
		developer.setExperienceYears(request.getExperienceYears());
		developer.setName(request.getName());
		developer.setAge(request.getAge());

		return DeveloperDetailDto.fromEntity(developer);
	}

	@Transactional
	public DeveloperDetailDto deleteDeveloper(final String memberId) {
		final Developer developer = getDeveloperByMemberId(memberId);
		developer.setStatus(StatusCode.RETIRED);

		final RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
				.memberId(developer.getMemberId())
				.name(developer.getName())
				.build();
		retiredDeveloperRepository.save(retiredDeveloper);

		return DeveloperDetailDto.fromEntity(developer);
	}

	private Developer getDeveloperByMemberId(final String memberId) {
		return developerRepository.findByMemberId(memberId)
				.orElseThrow(() -> new DMakerException(NO_DEVELOPER));
	}
}
