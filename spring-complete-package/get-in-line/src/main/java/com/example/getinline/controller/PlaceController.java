package com.example.getinline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PlaceController {

	@GetMapping("/places")
	public String places() {
		return "place/index";
	}

	@GetMapping("/places/{placeId}")
	public String placeDetail(@PathVariable final Long placeId) {
		return "place/detail";
	}
}
