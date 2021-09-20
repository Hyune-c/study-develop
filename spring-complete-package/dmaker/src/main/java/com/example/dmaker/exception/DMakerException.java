package com.example.dmaker.exception;

import com.example.dmaker.code.DMakerErrorCode;
import lombok.Getter;

@Getter
public class DMakerException extends RuntimeException {

	private final DMakerErrorCode dMakerErrorCode;
	private final String detailMessage;

	public DMakerException(final DMakerErrorCode dMakerErrorCode) {
		super(dMakerErrorCode.getMessage());
		this.dMakerErrorCode = dMakerErrorCode;
		this.detailMessage = dMakerErrorCode.getMessage();
	}

	public DMakerException(final DMakerErrorCode dMakerErrorCode, final String detailMessage) {
		super(detailMessage);
		this.dMakerErrorCode = dMakerErrorCode;
		this.detailMessage = detailMessage;
	}
}
