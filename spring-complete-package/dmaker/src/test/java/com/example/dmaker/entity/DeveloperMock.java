package com.example.dmaker.entity;

import com.example.dmaker.code.StatusCode;
import com.example.dmaker.type.DeveloperLevel;
import com.example.dmaker.type.DeveloperSkillType;

public class DeveloperMock {

	public static Developer createDeveloper(
			final DeveloperLevel developerLevel,
			final DeveloperSkillType developerSkillType,
			final Integer experienceYears,
			final String memberId) {
		return Developer.builder()
				.level(developerLevel)
				.skillType(developerSkillType)
				.experienceYears(experienceYears)
				.memberId(memberId)
				.name("name")
				.age(28)
				.status(StatusCode.EMPLOYED)
				.build();
	}
}
