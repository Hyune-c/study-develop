package com.example.getinline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {

	@GetMapping("/admin/places")
	public String adminPlaces() {
		return "admin/places";
	}

	@GetMapping("/admin/places/{placeId}")
	public String adminPlaceDetail(@PathVariable final Long placeId) {
		return "admin/place-detail";
	}

	@GetMapping("/admin/events")
	public String adminEvents() {
		return "admin/events";
	}

	@GetMapping("/admin/events/{eventId}")
	public String adminEventDetail(@PathVariable final Long eventId) {
		return "admin/event-detail";
	}
}
