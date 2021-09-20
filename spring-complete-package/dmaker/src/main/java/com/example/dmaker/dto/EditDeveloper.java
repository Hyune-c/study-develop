package com.example.dmaker.dto;

import com.example.dmaker.type.DeveloperLevel;
import com.example.dmaker.type.DeveloperSkillType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EditDeveloper {

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Request {

		private DeveloperLevel developerLevel;
		private DeveloperSkillType developerSkillType;
		private Integer experienceYears;
		private String name;
		private Integer age;
	}
}
