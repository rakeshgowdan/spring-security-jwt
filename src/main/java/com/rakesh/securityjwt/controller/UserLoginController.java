package com.rakesh.securityjwt.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rakesh.securityjwt.dto.UserRequest;
import com.rakesh.securityjwt.dto.UserResponse;
import com.rakesh.securityjwt.service.UserDetailServiceHandler;
import com.rakesh.securityjwt.utilities.JWTUtil;

@RestController
@RequestMapping("/api/auth")
public class UserLoginController {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private UserDetailServiceHandler userDetailServiceHandler;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/userAuthenticate")
	public ResponseEntity<UserResponse> userAuthenticate(@RequestBody UserRequest userRequest) {

		// authenticate user
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userRequest.getUserName(), userRequest.getPassword()));

		//Generate token
		UserDetails details = userDetailServiceHandler.loadUserByUsername(userRequest.getUserName());
		String token=jwtUtil.generateToken(details);
		UserResponse userResponse= new UserResponse(userRequest.getUserName(), "Token Generated succssfully", token, new java.util.Date());
		return new ResponseEntity<UserResponse>(userResponse,HttpStatus.OK);
	}
}
