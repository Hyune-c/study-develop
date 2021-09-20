package com.example.dmaker.dto;

import com.example.dmaker.code.DMakerErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DMakerErrorResponse {

	private DMakerErrorCode errorCode;
	private String errorMessage;
}
