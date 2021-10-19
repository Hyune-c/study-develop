package com.example.getinline.controller.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiPlaceController {

	@GetMapping("/api/places")
	public List<String> getPlaces() {
		return List.of("1", "2");
	}

	@PostMapping("/api/places")
	public Boolean createPlace() {
		return true;
	}

	@GetMapping("/api/places/{placeId}")
	public String getPlace(@PathVariable Long placeId) {
		return "done";
	}

	@PutMapping("/api/places/{placeId}")
	public Boolean modifyPlace(@PathVariable Long placeId) {
		return true;
	}

	@DeleteMapping("/api/places/{placeId}")
	public Boolean removePlace(@PathVariable Long placeId) {
		return true;
	}
}
