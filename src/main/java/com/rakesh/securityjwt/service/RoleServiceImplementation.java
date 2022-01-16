package com.rakesh.securityjwt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rakesh.securityjwt.dao.RoleRepository;
import com.rakesh.securityjwt.dto.UserRoleDTO;
import com.rakesh.securityjwt.pojo.UserRole;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleServiceImplementation implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	@Override
	public UserRoleDTO createRole(UserRoleDTO userRoleDTO) {
		log.info("-----------------Role Repository--------");
		UserRole userRole = new UserRole();
		BeanUtils.copyProperties(userRoleDTO, userRole); // DTO-->POJO
		log.info(userRole+"--------->userRole");
		UserRole userRoleResponse = roleRepository.save(userRole);
		BeanUtils.copyProperties(userRoleResponse, userRoleDTO);// POJO-->DTO
		log.info(userRoleDTO+"--------->userRoleDTO");
		return userRoleDTO;
	
	}

	@Override
	public UserRoleDTO getUserRoleById(Long roleId) {
		UserRole userRole=roleRepository.findById(roleId).get();
		UserRoleDTO userRoleDTO=new UserRoleDTO();
		BeanUtils.copyProperties(userRole, userRoleDTO);
				return userRoleDTO;
	}

	@Override
	public List<UserRoleDTO> getAllRoles() {
		List<UserRole> listOfUserRole=roleRepository.findAll();
		List<UserRoleDTO> listOfUserRoleDTO=new ArrayList<>();
		UserRoleDTO userRoleDTO=null;
		for(UserRole role:listOfUserRole) {
			userRoleDTO =new UserRoleDTO();
			BeanUtils.copyProperties(role, userRoleDTO);
			listOfUserRoleDTO.add(userRoleDTO);
		}
		return listOfUserRoleDTO;
	}

	@Override
	public void deleteUserRoleById(Long roleId) {
		roleRepository.deleteById(roleId);
		
	}
}














