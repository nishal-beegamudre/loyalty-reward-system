package com.userservice.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.dto.RoleDTO;
import com.userservice.entity.Role;
import com.userservice.repository.RoleRepository;
import com.userservice.util.Constants;

@Service
public class RoleService {
	
	private static final Logger logger = LoggerFactory.getLogger(RoleService.class);
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ObjectMapper objectMapper;

	//@CachePut(value="roleData", key="#roleDTO.name")
	public String createRole(RoleDTO roleDTO) {
		
		
		try {
			
			
			Role role = roleRepository.findByName(roleDTO.getName()).orElse(null);
			
			if(role!=null) {
				logger.info("Role request received for "+role.getName()+" is rejected as role exists already for that name");
				return Constants.ROLE_EXISTS_ALREADY;
			}
			

			Role newRole = new Role();
			newRole.setName(roleDTO.getName());
			newRole.setIsAdmin(roleDTO.getIsAdmin());
			newRole.setIsSuperAdmin(roleDTO.getIsSuperAdmin());
			newRole.setIsCustomerUser(roleDTO.getIsCustomerUser());
			newRole.setCreationDate(ZonedDateTime.now(ZoneId.of(Constants.IST)));
			
			roleRepository.save(newRole);
			
				logger.info("Role has been successfully created with payload "+objectMapper.writeValueAsString(newRole));
				return Constants.ROLE_CREATED_SUCCESSFULLY;
			} catch (Exception e) {
				logger.info(Constants.EXCEPTION_OCCURRED+" with message "+e.getMessage());
				return Constants.EXCEPTION_OCCURRED;
			}
		
	}
	
	//@CacheEvict(value="roleData", key="#roleDTO.name")
	public Boolean deleteRole(String name) {
		
		try {
			
			Role role = roleRepository.findByName(name).orElse(null);
			
			if(role==null) {
				logger.info("Deletion of role request received for "+name+" is rejected as role does not exist for that name");
				return false;
			}else {
				
				roleRepository.delete(role);
				
					logger.info("Deletion of role request has been successfully completed for "+name);
					return true;
				
			}
			
			
			} catch (Exception e) {
				logger.info(Constants.EXCEPTION_OCCURRED+" with message "+e.getMessage());
				return false;
			}
		
	}

	@Cacheable(value="roleData", key="#roleDTO.name")
	public RoleDTO getRoleByName(String roleName) {
		
		Role role = roleRepository.findByName(roleName).orElse(null);
		
		if(role!=null) {
			return new RoleDTO(role.getId(),role.getName(),role.getIsAdmin(),
					role.getIsSuperAdmin(),role.getIsCustomerUser(),role.getCreationDate());		
					
		}
		
		return null;
		
	}
	

}
