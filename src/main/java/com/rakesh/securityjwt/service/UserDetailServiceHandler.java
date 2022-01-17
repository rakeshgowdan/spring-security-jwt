package com.rakesh.securityjwt.service;




import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rakesh.securityjwt.dao.UserRepository;
import com.rakesh.securityjwt.dto.UserDTO;
import com.rakesh.securityjwt.pojo.User;


import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserDetailServiceHandler implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	//this method does validation for user existence 
	@Override
	public UserDetails loadUserByUsername(String uname) throws UsernameNotFoundException {
		
		log.info("Service Layer | UserDetailServiceHandler()---> "+uname);
		
		User user1=userRepository.findByuname(uname);
		log.info("Service Layer | UserDetailServiceHandler()---> "+user1);
		
		if(user1==null){
			throw new UsernameNotFoundException("User does not exist!");
		}
        UserDTO userDTO=new UserDTO();
        BeanUtils.copyProperties(user1, userDTO);
		return userDTO;
		
		/*
		 * if(username.equals("rakesh")){//here you can make a DB call with the help of
		 * repository and do the validation return new User("rakesh", "secret", new
		 * ArrayList());//assume these are returned from DB upon success }else{ throw
		 * new UsernameNotFoundException("User does not exist!"); }
		 */
	}
	public UserDTO registerUser(UserDTO userDTO) {
		log.info("Service layer | registerUser()---> USERDTO   "+userDTO);
		User user=new User();
		BeanUtils.copyProperties(userDTO, user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		log.info("Service layer | registerUser()---> user object before saving  "+user);
		user= userRepository.save(user);
		log.info("Service layer | registerUser()---> USER object after save() "+user);
		BeanUtils.copyProperties(user, userDTO);
		return userDTO;
	}
	
}
