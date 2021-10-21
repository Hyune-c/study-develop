package com.example.getinline.dto;

import com.example.getinline.constant.ErrorCode;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiErrorResponse {

	private final Boolean success;
	private final Integer errorCode;
	private final String message;

	public static ApiErrorResponse of(final Boolean success, final Integer errorCode, final String message) {
		return new ApiErrorResponse(success, errorCode, message);
	}

	public static ApiErrorResponse of(final Boolean success, final ErrorCode errorCode) {
		return new ApiErrorResponse(success, errorCode.getCode(), errorCode.getMessage());
	}

	public static ApiErrorResponse of(final Boolean success, final ErrorCode errorCode, final Exception e) {
		return new ApiErrorResponse(success, errorCode.getCode(), errorCode.getMessage(e));
	}

	public static ApiErrorResponse of(final Boolean success, final ErrorCode errorCode, final String message) {
		return new ApiErrorResponse(success, errorCode.getCode(), errorCode.getMessage(message));
	}
}
