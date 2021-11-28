package com.rakesh.securityjwt.service;


import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailServiceHandler implements UserDetailsService {

	
	//this method does validation for user existence 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if(username.equals("rakesh")){//here you can make a DB call with the help of repository and do the validation
            return new User("rakesh", "secret", new ArrayList());//assume these are returned from DB upon success
        }else{
            throw new UsernameNotFoundException("User does not exist!");
        }
	}

	
}
