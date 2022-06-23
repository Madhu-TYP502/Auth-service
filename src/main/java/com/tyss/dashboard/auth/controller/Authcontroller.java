package com.tyss.dashboard.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.dashboard.auth.model.AuthRequest;
import com.tyss.dashboard.auth.model.AuthResponse;
import com.tyss.dashboard.auth.service.AuthService;

@RestController
@RequestMapping(value = "/auth")
public class Authcontroller {

	private final AuthService authService;
	Logger logger = LoggerFactory.getLogger(Authcontroller.class);

	@Autowired
	public Authcontroller(final AuthService authService) {
		this.authService = authService;
	}

	@PostMapping(value = "/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
		logger.info("inside AuthService-Authcontroller login()");
		return authService.userLogin(authRequest);
	}

	@GetMapping(value = "/refresh/token")
	public ResponseEntity<AuthResponse> refreshToken(@RequestHeader(value = "Authorization") String token) {

		logger.info("inside AuthService-Authcontroller refreshToken()");

		return authService.refreshToken(token.replace("Bearer", "").trim());
	}
}
