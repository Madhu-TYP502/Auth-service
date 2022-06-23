package com.tyss.dashboard.auth.service;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tyss.dashboard.auth.entity.UserEntity;
import com.tyss.dashboard.auth.model.AuthRequest;
import com.tyss.dashboard.auth.model.AuthResponse;
import com.tyss.dashboard.auth.util.JwtUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {

	@Autowired
	private JwtUtil jwt;

	@Autowired
	MongoTemplate mongoTemplate;

	@Value("${jwt.secret}")
	private String secret;

	private Key key;

	Logger logger = LoggerFactory.getLogger(AuthService.class);


	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
	}

	public ResponseEntity<AuthResponse> userLogin(AuthRequest authRequest) {

		logger.info("Email    : " + authRequest.getEmail());
		logger.info("Password : " + authRequest.getPassword());
		UserEntity userEntity = mongoTemplate.findOne(
				new Query().addCriteria(
						Criteria.where("email").is(authRequest.getEmail()).and("phone").is(authRequest.getPassword())),
				UserEntity.class);

		if (userEntity != null) {
			String accessToken = jwt.generate(userEntity, "ACCESS");
			String refreshToken = jwt.generate(userEntity, "REFRESH");
			List<String> roles = new ArrayList<>();
			roles.add(userEntity.getRole());
			AuthResponse authResponse = new AuthResponse(accessToken, refreshToken, roles);
			return ResponseEntity.ok().header("userid", userEntity.getId()).body(authResponse);
		}

		return ResponseEntity.badRequest().body(null);
	}

	public ResponseEntity<AuthResponse> refreshToken(String rfToken) {

		logger.info("INSIDE REFRESH TOKEN");
		String email = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(rfToken).getBody().getSubject();

		UserEntity userEntity = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("email").is(email)),
				UserEntity.class);

		if (userEntity != null) {
			String accessToken = jwt.generate(userEntity, "ACCESS");
			String refreshToken = jwt.generate(userEntity, "REFRESH");
			List<String> roles = new ArrayList<>();
			roles.add(userEntity.getRole());
			AuthResponse authResponse = new AuthResponse(accessToken, refreshToken, roles);
			return ResponseEntity.ok().header("userid", userEntity.getId()).body(authResponse);
		}

		return ResponseEntity.badRequest().body(null);
	}
}
