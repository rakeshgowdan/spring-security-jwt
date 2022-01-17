package com.rakesh.securityjwt.service;




import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rakesh.securityjwt.dao.RoleRepository;
import com.rakesh.securityjwt.dao.UserRepository;
import com.rakesh.securityjwt.dto.UserDTO;
import com.rakesh.securityjwt.dto.UserRoleDTO;
import com.rakesh.securityjwt.pojo.User;
import com.rakesh.securityjwt.pojo.UserRole;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserDetailServiceHandler implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
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
        
      //convert UserRole to UserRoleDTO
        Set<UserRoleDTO> setOfUserRoleDTO = new HashSet<>();
        UserRoleDTO setRoleDTO = null;
        for(UserRole userRole :user1.getRoles()){
        	setRoleDTO = new UserRoleDTO();
        	setRoleDTO.setRoleName(userRole.getRoleName());
        	setRoleDTO.setRoleId(userRole.getRoleId());
        	setOfUserRoleDTO.add(setRoleDTO);
        }
        userDTO.setRoles(setOfUserRoleDTO);
        
		return userDTO;
		
		/*
		 * if(username.equals("rakesh")){//here you can make a DB call with the help of
		 * repository and do the validation return new User("rakesh", "secret", new
		 * ArrayList());//assume these are returned from DB upon success }else{ throw
		 * new UsernameNotFoundException("User does not exist!"); }
		 */
	}
	public UserDTO registerUser(UserDTO userDTO) {
		
		User user=new User();
		BeanUtils.copyProperties(userDTO, user); //it doesnt do deep copy
		
		Set<UserRole> setOfUserRole=new HashSet<>();
		
		//fetch every role from DB based on role id and than set this role to user entity roles
		for(UserRoleDTO roleDTO:userDTO.getRoles()) {
			log.info("in register| roleDTO ---------------->"+ roleDTO);
			Optional<UserRole> userRole=roleRepository.findById(roleDTO.getRoleId());
			log.info("in register| roleDTO--------------->"+userRole);
			if(userRole.isPresent()) {
				setOfUserRole.add(userRole.get());
			}
		}
		
		user.setRoles(setOfUserRole);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		log.info(" IN register--before save-->"+user);
		user= userRepository.save(user);
		BeanUtils.copyProperties(user, userDTO);
		log.info(" IN register--after save-->"+userDTO);
		
		
		//convert UserRole to UserRoleDTO
		log.info(" IN register--before response convertion-->"+userDTO);
        Set<UserRoleDTO> setOfUserRoleDTO = new HashSet<>();
        UserRoleDTO setRoleDTO = null;
        for(UserRole userRole :user.getRoles()){
        	setRoleDTO = new UserRoleDTO();
        	setRoleDTO.setRoleName(userRole.getRoleName());
        	setRoleDTO.setRoleId(userRole.getRoleId());
        	setOfUserRoleDTO.add(setRoleDTO);
        }
        userDTO.setRoles(setOfUserRoleDTO);
		log.info(" IN register--after response convertion-->"+userDTO);
		return userDTO;
	}
	
}
