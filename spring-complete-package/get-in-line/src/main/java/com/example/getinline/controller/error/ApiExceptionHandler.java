package com.example.getinline.controller.error;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.getinline.constant.ErrorCode;
import com.example.getinline.dto.ApiErrorResponse;
import com.example.getinline.exception.GeneralException;

@RestControllerAdvice(annotations = {RestController.class})
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
		return handleExceptionInternal(e, ErrorCode.VALIDATION_ERROR, request);
	}

	@ExceptionHandler
	public ResponseEntity<Object> general(final GeneralException e, final WebRequest request) {
		return handleExceptionInternal(e, e.getErrorCode(), request);
	}

	@ExceptionHandler
	public ResponseEntity<Object> exception(final Exception e, final WebRequest request) {
		return handleExceptionInternal(e, ErrorCode.INTERNAL_ERROR, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(final Exception ex, final Object body,
		final HttpHeaders headers,
		final HttpStatus status, final WebRequest request) {
		return handleExceptionInternal(ex, ErrorCode.valueOf(status), headers, status, request);
	}

	private ResponseEntity<Object> handleExceptionInternal(final Exception e, final ErrorCode errorCode,
		final WebRequest request) {
		return handleExceptionInternal(e, errorCode, HttpHeaders.EMPTY, errorCode.getHttpStatus(), request);
	}

	private ResponseEntity<Object> handleExceptionInternal(final Exception e, final ErrorCode errorCode,
		final HttpHeaders headers,
		final HttpStatus status, final WebRequest request) {
		return super.handleExceptionInternal(
			e,
			ApiErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)),
			headers,
			status,
			request
		);
	}
}
