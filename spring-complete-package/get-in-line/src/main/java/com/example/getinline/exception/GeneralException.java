package com.example.getinline.exception;

import com.example.getinline.constant.ErrorCode;

import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {

	private final ErrorCode errorCode;

	public GeneralException() {
		super(ErrorCode.INTERNAL_ERROR.getMessage());
		this.errorCode = ErrorCode.INTERNAL_ERROR;
	}

	public GeneralException(final String message) {
		super(ErrorCode.INTERNAL_ERROR.getMessage(message));
		this.errorCode = ErrorCode.INTERNAL_ERROR;
	}

	public GeneralException(final String message, final Throwable cause) {
		super(ErrorCode.INTERNAL_ERROR.getMessage(message), cause);
		this.errorCode = ErrorCode.INTERNAL_ERROR;
	}

	public GeneralException(final Throwable cause) {
		super(ErrorCode.INTERNAL_ERROR.getMessage(cause));
		this.errorCode = ErrorCode.INTERNAL_ERROR;
	}

	public GeneralException(final ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public GeneralException(final ErrorCode errorCode, final String message) {
		super(errorCode.getMessage(message));
		this.errorCode = errorCode;
	}

	public GeneralException(final ErrorCode errorCode, final String message, final Throwable cause) {
		super(errorCode.getMessage(message), cause);
		this.errorCode = errorCode;
	}

	public GeneralException(final ErrorCode errorCode, final Throwable cause) {
		super(errorCode.getMessage(cause), cause);
		this.errorCode = errorCode;
	}
}
