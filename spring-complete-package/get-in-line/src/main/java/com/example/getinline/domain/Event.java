package com.example.getinline.domain;

import java.time.LocalDateTime;

import com.example.getinline.constant.EventStatus;

import lombok.Data;

@Data
public class Event {

	private Long id;

	private Place place;
	private String eventName;
	private EventStatus eventStatus;

	private LocalDateTime eventStartDatetime;
	private LocalDateTime eventEndDatetime;

	private Integer currentNumberOfPeople;
	private Integer capacity;
	private String memo;

	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
}
