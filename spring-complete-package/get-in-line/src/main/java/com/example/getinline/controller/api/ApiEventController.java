package com.example.getinline.controller.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiEventController {

	@GetMapping("/api/events")
	public List<String> getEvents() {
		return List.of("1", "2");
	}

	@PostMapping("/api/place/{placeId}/events")
	public Boolean createEvent() {
		return true;
	}

	@GetMapping("/api/events/{eventId}")
	public String getEvent(@PathVariable Long eventId) {
		return "done";
	}

	@PutMapping("/api/events/{eventId}")
	public Boolean modifyEvent(@PathVariable Long eventId) {
		return true;
	}

	@DeleteMapping("/api/events/{eventId}")
	public Boolean removeEvent(@PathVariable Long eventId) {
		return true;
	}
}
