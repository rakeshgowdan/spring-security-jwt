package com.rakesh.securityjwt.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rakesh.securityjwt.dto.UserDTO;
import com.rakesh.securityjwt.dto.UserRequestDTO;
import com.rakesh.securityjwt.dto.UserResponseDTO;
import com.rakesh.securityjwt.service.UserDetailServiceHandler;
import com.rakesh.securityjwt.utilities.JWTUtil;


import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class UserController {

	@Autowired
	private JWTUtil jwtUtil;
	
	

	@Autowired
	private UserDetailServiceHandler userDetailServiceHandler;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/userAuthenticate")
	public ResponseEntity<UserResponseDTO> userAuthenticate(@RequestBody UserRequestDTO userRequestDTO) {

		log.info("UserController | userAuthenticate()------>" +userRequestDTO);
		
		UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userRequestDTO.getUname(), userRequestDTO.getPassword());
		// authenticate user
		authenticationManager.authenticate(authToken);
		
		// Generate token
		UserDetails details = userDetailServiceHandler.loadUserByUsername(userRequestDTO.getUname());
		String token = jwtUtil.generateToken(details);
		
		
		UserResponseDTO userResponseDTO = new UserResponseDTO(userRequestDTO.getUname(),"Token Generated Sucessfully", token, new java.util.Date());
		
		return new ResponseEntity<UserResponseDTO>(userResponseDTO, HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
		log.info("Controller layer | Register user----->"+userDTO);
		UserDTO userdto=userDetailServiceHandler.registerUser(userDTO);
		return new ResponseEntity<UserDTO>(userdto, HttpStatus.CREATED);
	}
	@GetMapping("/user/currentUser")
	public UserDTO getCurrentUser(Principal authentication) {
		log.info("UserController | getCurrentUser()------>" +authentication);
		UserDetails details=null;
		if(authentication!=null) {
			details=userDetailServiceHandler.loadUserByUsername(authentication.getName());
		
		}
		return (UserDTO) details;
	}
}