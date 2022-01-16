package com.rakesh.securityjwt.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rakesh.securityjwt.dto.UserRoleDTO;

import com.rakesh.securityjwt.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping("/role")
	public UserRoleDTO createRole(@RequestBody UserRoleDTO roleDTO) {
		log.info("Role Controller called | CreateRole");
		log.info(roleDTO +"--> roleDTO");
		return roleService.createRole(roleDTO);
	}

	@GetMapping("/role")
	public List<UserRoleDTO> getAllRoles() {
		log.info("Role Controller called | getAllRoles");
		return roleService.getAllRoles();
		}
	@DeleteMapping("/role/{roleId}")
	public void deleteRole(@PathVariable Long roleId) {
		roleService.deleteUserRoleById(roleId);
	}
}








