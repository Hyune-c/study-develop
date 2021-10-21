package com.example.getinline.controller.error;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.example.getinline.constant.ErrorCode;
import com.example.getinline.exception.GeneralException;

@ControllerAdvice
public class BaseExceptionHandler {

	@ExceptionHandler
	public ModelAndView general(final GeneralException e) {
		final ErrorCode errorCode = e.getErrorCode();

		return new ModelAndView(
			"error",
			Map.of(
				"statusCode", errorCode.getHttpStatus().value(),
				"errorCode", errorCode,
				"message", errorCode.getMessage()
			),
			errorCode.getHttpStatus()
		);
	}

	@ExceptionHandler
	public ModelAndView exception(final Exception e, final HttpServletResponse response) {
		HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
		ErrorCode errorCode = httpStatus.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;

		if (httpStatus == HttpStatus.OK) {
			httpStatus = HttpStatus.FORBIDDEN;
			errorCode = ErrorCode.BAD_REQUEST;
		}

		return new ModelAndView(
			"error",
			Map.of(
				"statusCode", httpStatus.value(),
				"errorCode", errorCode,
				"message", errorCode.getMessage(e)
			),
			httpStatus
		);
	}
}
