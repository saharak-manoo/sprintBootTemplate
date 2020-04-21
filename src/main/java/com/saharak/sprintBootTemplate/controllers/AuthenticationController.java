package com.saharak.sprintBootTemplate.controllers;

import java.text.MessageFormat;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.saharak.sprintBootTemplate.config.JwtTokenUtil;
import com.saharak.sprintBootTemplate.exception.UserNotFoundException;
import com.saharak.sprintBootTemplate.models.JwtRequest;
import com.saharak.sprintBootTemplate.models.JwtResponse;
import com.saharak.sprintBootTemplate.models.User;
import com.saharak.sprintBootTemplate.services.LineService;
import com.saharak.sprintBootTemplate.services.UserService;

@RestController
@CrossOrigin
public class AuthenticationController extends ApplicationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private LineService lineService;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> generateAuthenticationToken(@RequestBody final JwtRequest authRequest)
			throws Exception {
		final User user = authenticate(
			authRequest.getUsername(), 
			authRequest.getPassword()
		);

		final UserDetails userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(
			user.getUsername()
		);
		
		final String token = jwtTokenUtil.generateToken(userDetails);
		lineService.sendNoti(
			MessageFormat.format("คุณ {0} ได้ Login เข้าสู่ระบบ", user.getFullName())
		);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	private User authenticate(final String username, final String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			final User user = userService.findByUsername(username);

			if (user == null || !encoder.matches(password, user.getPassword())) {
				throw new UserNotFoundException("Username or password incorrect");
			}

			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password)
			);

			return user;
		} catch (final DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (final BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}