package com.example.springpractice.controller;

import com.example.springpractice.service.SortService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MainController {

	private final SortService sortService;

	@GetMapping("/")
	public String hello(@RequestParam final List<String> list) {
		return sortService.doSort(list).toString();
	}
}
