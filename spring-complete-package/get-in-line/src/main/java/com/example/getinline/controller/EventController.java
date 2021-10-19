package com.example.getinline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Validated
@RequestMapping("/events")
@Controller
public class EventController {

	@GetMapping("/events")
	public String events() {
		return "/event/index";
	}

	@GetMapping("/events/{eventId}")
	public String eventDetail(@PathVariable final Long eventId) {
		return "/event/detail";
	}
}
