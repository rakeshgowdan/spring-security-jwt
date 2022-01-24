package com.rakesh.securityjwt.controller;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rakesh.securityjwt.dto.CommonResponse;
import com.rakesh.securityjwt.dto.UserDTO;
import com.rakesh.securityjwt.dto.UserRequestDTO;
import com.rakesh.securityjwt.dto.UserResponseDTO;
import com.rakesh.securityjwt.service.UserServiceImple;
import com.rakesh.securityjwt.utilities.JWTUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/api/auth")

public class UserController {

	@Autowired
	private JWTUtil jwtUtil;
	
	

	@Autowired
	private UserServiceImple userDetailServiceHandler;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/userAuthenticate")
	@Operation(summary = "authenticate user in the System ", responses = { 
			@ApiResponse(responseCode="200",description="Data Found",content=@Content(mediaType="application/json") ),
			@ApiResponse(responseCode="404",description="Data Not Found",content=@Content(mediaType="application/json")),
			@ApiResponse(responseCode="500",description="Internal Error",content=@Content(mediaType="application/json") )
	})
	public CommonResponse userAuthenticate(@RequestBody UserRequestDTO userRequestDTO) {
		UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userRequestDTO.getUname(), userRequestDTO.getPassword());
		
		// authenticate user
		authenticationManager.authenticate(authToken);
		
		// Generate token
		UserDetails details = userDetailServiceHandler.loadUserByUsername(userRequestDTO.getUname());
		
		String token = jwtUtil.generateToken(details);
		
		UserResponseDTO userResponseDTO = new UserResponseDTO(userRequestDTO.getUname(), token);
		
		return new CommonResponse(200, false, "User authenticated! login success", new Date(), userResponseDTO, null);
	}

	@PostMapping("/register")
	@Operation(summary = "register user in the System ", responses = { 
			@ApiResponse(responseCode="200",description="Data Found",content=@Content(mediaType="application/json") ),
			@ApiResponse(responseCode="404",description="Data Not Found",content=@Content(mediaType="application/json")),
			@ApiResponse(responseCode="500",description="Internal Error",content=@Content(mediaType="application/json") )
	})
	public CommonResponse registerUser(@RequestBody UserDTO userDTO) {
		UserDTO userdto=userDetailServiceHandler.registerUser(userDTO);
		return new CommonResponse(201, false, "User registration success", new Date(), userdto, null);
	
	}
	@GetMapping("/user/currentUser")
	@Operation(summary = "get current logged in user in the System", responses = { 
			@ApiResponse(responseCode="200",description="Data Found",content=@Content(mediaType="application/json") ),
			@ApiResponse(responseCode="404",description="Data Not Found",content=@Content(mediaType="application/json")),
			@ApiResponse(responseCode="500",description="Internal Error",content=@Content(mediaType="application/json") )
	})
	public CommonResponse getCurrentUser(Principal authentication) {
		UserDetails details=null;
		if(authentication!=null) {
			details=userDetailServiceHandler.loadUserByUsername(authentication.getName());
		}
		return new CommonResponse(200, false, "Current user fetched successfully", new Date(), (UserDTO) details, null);
		
	}
}