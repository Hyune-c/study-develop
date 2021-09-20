package com.example.dmaker.exception;

import static com.example.dmaker.code.DMakerErrorCode.INTERNAL_SERVER_ERROR;
import static com.example.dmaker.code.DMakerErrorCode.INVALID_REQUEST;

import com.example.dmaker.dto.DMakerErrorResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DMakerExceptionHandler {

	@ExceptionHandler(DMakerException.class)
	@ResponseBody
	public DMakerErrorResponse handleDMakerException(final DMakerException e, final HttpServletRequest request) {
		log.error("errorCode: {}, url: {}, message: {}", e.getDMakerErrorCode(), request.getRequestURI(), e.getDetailMessage(), e);
		return DMakerErrorResponse.builder()
				.errorCode(e.getDMakerErrorCode())
				.errorMessage(e.getDetailMessage())
				.build();
	}

	@ExceptionHandler(value = {
			HttpRequestMethodNotSupportedException.class,
			MethodArgumentNotValidException.class
	})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public DMakerErrorResponse handleBadRequest(final Exception e, final HttpServletRequest request) {
		log.error("url: {}, message: {}", request.getRequestURI(), e.getMessage(), e);
		return DMakerErrorResponse.builder()
				.errorCode(INVALID_REQUEST)
				.errorMessage(INVALID_REQUEST.getMessage())
				.build();
	}


	@ExceptionHandler(Exception.class)
	@ResponseBody
	public DMakerErrorResponse handleException(final Exception e, final HttpServletRequest request) {
		log.error("url: {}, message: {}", request.getRequestURI(), e.getMessage(), e);
		return DMakerErrorResponse.builder()
				.errorCode(INTERNAL_SERVER_ERROR)
				.errorMessage(INTERNAL_SERVER_ERROR.getMessage())
				.build();
	}
}
