package com.example.getinline.constant;

import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	OK(0, HttpStatus.OK, "Ok"),

	BAD_REQUEST(10000, HttpStatus.BAD_REQUEST, "Bad request"),
	SPRING_BAD_REQUEST(10001, HttpStatus.BAD_REQUEST, "Spring-detected bad request"),

	INTERNAL_ERROR(20000, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),
	SPRING_INTERNAL_ERROR(20001, HttpStatus.INTERNAL_SERVER_ERROR, "Spring-detected internal error"),
	;

	private final Integer code;
	private final HttpStatus httpStatus;
	private final String message;

	public String getMessage(final Throwable e) {
		return this.getMessage(this.getMessage() + " - " + e.getMessage());
	}

	public String getMessage(final String message) {
		return Optional.ofNullable(message)
			.filter(Predicate.not(String::isBlank))
			.orElse(this.getMessage());
	}

	@Override
	public String toString() {
		return String.format("%s (%d)", this.name(), this.getCode());
	}
}
