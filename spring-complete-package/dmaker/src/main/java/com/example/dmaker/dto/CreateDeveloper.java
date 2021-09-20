package com.example.dmaker.dto;

import com.example.dmaker.entity.Developer;
import com.example.dmaker.type.DeveloperLevel;
import com.example.dmaker.type.DeveloperSkillType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CreateDeveloper {

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Request {

		@NotNull
		private DeveloperLevel developerLevel;

		@NotNull
		private DeveloperSkillType developerSkillType;

		@NotNull
		@Min(0)
		private Integer experienceYears;

		@NotNull
		@Size(min = 3, max = 50, message = "invalid memberId")
		private String memberId;

		@NotNull
		@Size(min = 2, max = 50, message = "invalid name")
		private String name;

		@NotNull
		@Min(18)
		private Integer age;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Response {

		private DeveloperLevel developerLevel;
		private DeveloperSkillType developerSkillType;
		private Integer experienceYears;
		private String memberId;
		private String name;
		private Integer age;

		public static Response fromEntity(final Developer developer) {
			return Response.builder()
					.developerLevel(developer.getLevel())
					.developerSkillType(developer.getSkillType())
					.experienceYears(developer.getExperienceYears())
					.memberId(developer.getMemberId())
					.name(developer.getName())
					.age(developer.getAge())
					.build();
		}
	}
}
