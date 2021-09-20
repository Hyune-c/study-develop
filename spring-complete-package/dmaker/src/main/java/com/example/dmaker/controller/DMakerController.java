package com.example.dmaker.controller;

import com.example.dmaker.dto.CreateDeveloper;
import com.example.dmaker.dto.DeveloperDetailDto;
import com.example.dmaker.dto.DeveloperDto;
import com.example.dmaker.dto.EditDeveloper;
import com.example.dmaker.service.DMakerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	public CreateDeveloper.Response createDeveloper(
			@RequestBody final CreateDeveloper.Request request) {
		return dMakerService.createDeveloper(request);
	}

	@GetMapping("/developers")
	public List<DeveloperDto> getDevelopers() {
		return dMakerService.getAllEmployedDevelopers();
	}

	@GetMapping("/developer/{memberId}")
	public DeveloperDetailDto getDeveloper(
			@PathVariable final String memberId) {
		return dMakerService.getDeveloper(memberId);
	}

	@PutMapping("/developer/{memberId}")
	public DeveloperDetailDto updateDeveloper(
			@PathVariable final String memberId,
			@RequestBody final EditDeveloper.Request request) {
		return dMakerService.editDeveloper(memberId, request);
	}

	@DeleteMapping("/developer/{memberId}")
	public DeveloperDetailDto deleteDeveloper(
			@PathVariable final String memberId) {
		return dMakerService.deleteDeveloper(memberId);
	}
}
