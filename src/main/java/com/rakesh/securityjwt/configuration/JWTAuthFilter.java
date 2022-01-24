package com.rakesh.securityjwt.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rakesh.securityjwt.service.UserServiceImple;
import com.rakesh.securityjwt.utilities.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTAuthFilter extends OncePerRequestFilter {

	@Autowired
	private UserServiceImple userDetailServiceHandler;

	@Autowired
	private JWTUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// get the JWT token from request header & validate
		// gets called before any controller

		String bearerToken = request.getHeader("Authorization");
		String uname = null;
		String token = null;
		
		
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			// extract jwt from bearertoken
			token = bearerToken.substring(7);
			try {
				// get username from token
				
				uname = jwtUtil.extractUname(token);
				
				UserDetails userDetails= userDetailServiceHandler.loadUserByUsername(uname);
				// Once we get the token validate it.
				if (uname != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					
					UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
					
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						
					// After setting the Authentication in the context, we specify
					// that the current user is authenticated. So it passes the
					// Spring Security Configurations successfully.
					SecurityContextHolder.getContext().setAuthentication(authToken);
				} else {
					log.info("--------------------------------------------------------------------------------------");
					log.error("Invalid bearer token ");
					log.info("--------------------------------------------------------------------------------------");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			log.info("--------------------------------------------------------------------------------------");
			log.error("Invalid bearer token format");
			log.info("--------------------------------------------------------------------------------------");
		}
		
		filterChain.doFilter(request, response);
	}
	
	

}
