package com.example.dmaker.type;

import static com.example.dmaker.code.DMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH;
import static com.example.dmaker.constant.DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS;
import static com.example.dmaker.constant.DMakerConstant.MIN_SENIOR_EXPERIENCE_YEARS;

import com.example.dmaker.exception.DMakerException;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeveloperLevel {

	NEW("신입 개발자", years -> years == 0),
	JUNIOR("주니어 개발자", years -> years <= MAX_JUNIOR_EXPERIENCE_YEARS),
	JUNGNIOR("중니어 개발자", years -> years > MAX_JUNIOR_EXPERIENCE_YEARS && years < MIN_SENIOR_EXPERIENCE_YEARS),
	SENIOR("시니어 개발자", years -> years >= MIN_SENIOR_EXPERIENCE_YEARS),
	;

	private final String description;
	private final Function<Integer, Boolean> validateFunction;

	public void validateExperienceYears(final int years) {
		if (!validateFunction.apply(years)) {
			throw new DMakerException(LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH);
		}
	}
}
