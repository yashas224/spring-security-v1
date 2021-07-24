package com.example.spring.springsecurityjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.springsecurityjwt.dto.AuthenticationRequest;
import com.example.spring.springsecurityjwt.dto.AuthenticationResponse;
import com.example.spring.springsecurityjwt.util.JwtUtil;

@RestController
public class HomeController {

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	AuthenticationManager authenticationManager;

	@GetMapping("/hello")
	public String homePage() {
		return "home-page";
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest request) {
		Authentication authenticatedObject;
		try {
			authenticatedObject = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
		} catch (BadCredentialsException e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (AuthenticationException e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok(
				new AuthenticationResponse(jwtUtil.generateToken((UserDetails) authenticatedObject.getPrincipal())));
	}
}
