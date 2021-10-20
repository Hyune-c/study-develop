package com.example.getinline.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AdminPlaceMap {

	private Long id;

	private Admin admin;
	private Place place;

	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
}
