package com.example.dmaker.dto;

import com.example.dmaker.code.StatusCode;
import com.example.dmaker.entity.Developer;
import com.example.dmaker.type.DeveloperLevel;
import com.example.dmaker.type.DeveloperSkillType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperDetailDto {

	private DeveloperLevel developerLevel;
	private DeveloperSkillType developerSkillType;
	private Integer experienceYears;
	private String memberId;
	private String name;
	private Integer age;
	private StatusCode status;

	public static DeveloperDetailDto fromEntity(final Developer developer) {
		return DeveloperDetailDto.builder()
				.developerLevel(developer.getLevel())
				.developerSkillType(developer.getSkillType())
				.experienceYears(developer.getExperienceYears())
				.memberId(developer.getMemberId())
				.name(developer.getName())
				.age(developer.getAge())
				.status(developer.getStatus())
				.build();
	}
}
