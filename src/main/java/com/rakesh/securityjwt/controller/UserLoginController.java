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

import com.rakesh.securityjwt.dto.UserRequestDTO;
import com.rakesh.securityjwt.dto.UserResponseDTO;
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
	public ResponseEntity<UserResponseDTO> userAuthenticate(@RequestBody UserRequestDTO userRequestDTO) {

		// authenticate user
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userRequestDTO.getUserName(), userRequestDTO.getPassword()));

		//Generate token
		UserDetails details = userDetailServiceHandler.loadUserByUsername(userRequestDTO.getUserName());
		String token=jwtUtil.generateToken(details);
		UserResponseDTO userResponseDTO= new UserResponseDTO(userRequestDTO.getUserName(), "Token Generated Sucessfully", token, new java.util.Date());
		return new ResponseEntity<UserResponseDTO>(userResponseDTO,HttpStatus.OK);
	}
}
