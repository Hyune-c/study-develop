package com.example.dmaker.controller;

import com.example.dmaker.dto.CreateDeveloper;
import com.example.dmaker.dto.DeveloperDetailDto;
import com.example.dmaker.dto.DeveloperDto;
import com.example.dmaker.dto.EditDeveloper;
import com.example.dmaker.service.DMakerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<List<DeveloperDto>> getDevelopers() {
		return ResponseEntity.ok(dMakerService.getAllEmployedDevelopers());
	}

	@GetMapping("/developer/{memberId}")
	public ResponseEntity<DeveloperDetailDto> getDeveloper(
			@PathVariable final String memberId) {
		return ResponseEntity.ok(dMakerService.getDeveloper(memberId));
	}

	@PutMapping("/developer/{memberId}")
	public ResponseEntity<DeveloperDetailDto> updateDeveloper(
			@PathVariable final String memberId,
			@RequestBody final EditDeveloper.Request request) {
		return ResponseEntity.ok(dMakerService.editDeveloper(memberId, request));
	}

	@DeleteMapping("/developer/{memberId}")
	public ResponseEntity<DeveloperDetailDto> deleteDeveloper(
			@PathVariable final String memberId) {
		return ResponseEntity.ok(dMakerService.deleteDeveloper(memberId));
	}
}
