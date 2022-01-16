package com.rakesh.securityjwt.dto;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityDTO implements GrantedAuthority {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String authority;
}
