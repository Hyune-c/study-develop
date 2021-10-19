package com.example.getinline.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiAuthController {

	@PostMapping("/api/sign-up")
	public String signUp() {
		return "done.";
	}

	@PostMapping("/api/login")
	public String login() {
		return "done.";
	}
}
