package com.rakesh.securityjwt.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO implements UserDetails {

	private static final long serialVersionUID = 1L;
	private long userId;
	private String uname;
	private String password;
	private String firstName;
	private String lastName;
	private String mailId;
	private String mobileNo;
	private Set<UserRoleDTO> roles = new HashSet<>();


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		Set<AuthorityDTO> setOfAuthorityDTO = new HashSet<>();
		roles.forEach(role -> setOfAuthorityDTO.add(new AuthorityDTO(role.getRoleName())));
		return setOfAuthorityDTO;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
