package com.example.spring.securitytrial.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping
	public String firstMethod() {
		return "public-API";
	}

	@GetMapping("/user")
	public String userMethod() {
		return "users-API";
	}

	@GetMapping("/admin")
	public String adminMethod() {
		return "admin-API";
	}
}
