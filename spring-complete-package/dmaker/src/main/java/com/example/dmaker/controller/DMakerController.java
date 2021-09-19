package com.example.dmaker.controller;

import com.example.dmaker.dto.CreateDeveloper;
import com.example.dmaker.service.DMakerService;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
public class DMakerController {

	private final DMakerService dMakerService;

	@PostMapping("/create-developer")
	public ResponseEntity<CreateDeveloper.Response> createDeveloper(
			@RequestBody final CreateDeveloper.Request request) {
		return ResponseEntity.ok(dMakerService.createDeveloper(request));
	}

	@GetMapping("/developers")
	public List<String> getAllDevelopers() {
		log.info("### GET /developers");
		return Arrays.asList("snow", "Elsa", "Olaf");
	}
}
