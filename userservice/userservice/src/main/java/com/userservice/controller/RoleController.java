package com.userservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.dto.RoleDTO;
import com.userservice.dto.UserDTO;
import com.userservice.service.RoleService;
import com.userservice.util.Constants;

@RestController
public class RoleController {
	
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@PostMapping("/user/createRole")
    public String createRole(@RequestBody RoleDTO roleDTO){
		
		try {
			
			logger.info("Request received to create role in user service with payload "+objectMapper.writeValueAsString(roleDTO));
			String isSuccessful = roleService.createRole(roleDTO);
			return isSuccessful;
			
		}catch(Exception e) {
			logger.debug("Exception occured : "+e.getMessage());
			return Constants.EXCEPTION_OCCURRED;
		}
		
		
	}
	
	@DeleteMapping("/user/deleteRole")
    public Boolean deleteRole(@RequestParam("name") String name){
		
		try {
			
			logger.info("Request received to delete the role in user service with name "+name);
			Boolean isSuccessful = roleService.deleteRole(name);
			return isSuccessful;
			
		}catch(Exception e) {
			logger.debug("Exception occured : "+e.getMessage());
			return false;
		}
		
		
	}
	
	@GetMapping("/user/getRoleByName")
	public RoleDTO getRoleByName(@RequestParam("name") String roleName) {
		
		logger.info("Request received to get the role by name "+roleName);
		
		RoleDTO role = roleService.getRoleByName(roleName);
		
		logger.info("Request completed to get the role by name "+roleName);
		
		return role;
		
	}
	
}