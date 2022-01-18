package com.rakesh.securityjwt.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1")
//@Api(value = "HomeController Resource", produces = "application/json")
public class HomeController {
	
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String admin() {
		return "Hello!! admin";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public String user() {
		return "Hello!! user";
	}
	@GetMapping("/manager")
	public String manager() {
		return "Hello!! manager";
	}
	
}
