package com.userservice.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.dto.UserDTO;
import com.userservice.entity.Role;
import com.userservice.entity.User;
import com.userservice.repository.RoleRepository;
import com.userservice.repository.UserRepository;
import com.userservice.util.Constants;

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ObjectMapper objectMapper;

	
	public String createUser(UserDTO userDTO) {
		
		try {
		
		User user = userRepository.findByEmail(userDTO.getEmail()).orElse(null);
		Role role = roleRepository.findById(userDTO.getRoleId()).orElse(null);
		
		if(user!=null) {
			logger.info("User request received for "+userDTO.getEmail()+" is rejected as user exists already for that mail");
			return Constants.USER_EXISTS_ALREADY;
		}
		
		User newUser = new User();
		newUser.setBalance(0.0);
		newUser.setCreationDate(ZonedDateTime.now(ZoneId.of(Constants.IST)));
		newUser.setEmail(userDTO.getEmail());
		newUser.setName(userDTO.getName());
		newUser.setIsActive(true);
		newUser.setIsValidated(false);
		newUser.setPassword(userDTO.getPassword());
		newUser.setLastModifiedDate(ZonedDateTime.now(ZoneId.of(Constants.IST)));
		newUser.setRoleId(role.getId());
		userRepository.save(newUser);
		
		newUser.setPassword(null);	// Made as null so that password will be hidden in the log
		
			logger.info("User has been successfully created with payload "+objectMapper.writeValueAsString(newUser));
			return Constants.USER_CREATED_SUCCESSFULLY;
		} catch (Exception e) {
			logger.info(Constants.EXCEPTION_OCCURRED+" with message "+e.getMessage());
			return Constants.EXCEPTION_OCCURRED;
		}
		
		
		
	}

	
	public String updateUser(UserDTO userDTO) {
		
		try {
			
			User user = userRepository.findByEmail(userDTO.getEmail()).orElse(null);
			Boolean isRolePresent = false;
			Role role = new Role();
			if(userDTO.getRoleId()!=null) {
				isRolePresent = true;
				role = roleRepository.findById(userDTO.getRoleId()).orElse(null);
			}
			if(user==null) {
				logger.info("User request received for "+userDTO.getEmail()+" is rejected as user does not exist for that mail");
				return Constants.NO_USER_EXISTS;
			}else {
				
				user.setBalance((userDTO.getBalance()==null)?user.getBalance():userDTO.getBalance());
				user.setName((userDTO.getName()==null)?user.getName():userDTO.getName());
				user.setIsActive((userDTO.getIsActive()==null)?user.getIsActive():userDTO.getIsActive());
				user.setIsValidated((userDTO.getIsValidated()==null)?user.getIsValidated():userDTO.getIsValidated());
				user.setPassword((userDTO.getPassword()==null)?user.getPassword():userDTO.getPassword());
				user.setLastModifiedDate(ZonedDateTime.now(ZoneId.of(Constants.IST)));
				user.setRoleId(isRolePresent?role.getId():user.getRoleId());
				userRepository.save(user);
				
				user.setPassword(null);	// Made as null so that password will be hidden in the log
				
					logger.info("User has been successfully updated with payload "+objectMapper.writeValueAsString(user));
					return Constants.USER_CREATED_SUCCESSFULLY;
				
				
			}
			
			
			} catch (Exception e) {
				logger.info(Constants.EXCEPTION_OCCURRED+" with message "+e.getMessage());
				return Constants.EXCEPTION_OCCURRED;
			}
		
	}

	@Cacheable(value="userData", key="#userDTO.email")
	public UserDTO getUserByEmail(String email) {
		
		User user = userRepository.findByEmail(email).orElse(null);
		
		if(user==null) {
			
			return null;
		}else {
			
			return new UserDTO(user.getId(),user.getEmail(),"",user.getName(),user.getBalance(),
					user.getRoleId(),user.getIsValidated(),user.getIsActive(),
					user.getCreationDate(),user.getLastModifiedDate());
		}
		
	}

	@Cacheable(value="userBalance", key="#userDTO.email")
	public Double getBalanceByEmail(String email) {
		
		User user = userRepository.findByEmail(email).orElse(null);
		
		if(user==null) {
			return null;
		}else {
			return user.getBalance();
		}
		
	}

	public List<UserDTO> getAllUsers() {
		
		List<User> users = userRepository.findAll();
		
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		
		for(User user: users) {
			
			UserDTO userDTO = new UserDTO(user.getId(),user.getEmail(),"",user.getName(),user.getBalance(),
					user.getRoleId(),user.getIsValidated(),user.getIsActive(),
					user.getCreationDate(),user.getLastModifiedDate());
			
			userDTOs.add(userDTO);
			
		}
		
		return userDTOs;
		
	}

	//@CachePut(value="userData", key="#userDTO.email")
	public Boolean updateBalance(UserDTO userDTO) {
	
		try {
			
			User user = userRepository.findByEmail(userDTO.getEmail()).orElse(null);
			
			if(user==null) {
				logger.info("Balance update request received for "+userDTO.getEmail()+" is rejected as user does not exist for that mail");
				return false;
			}else {
				
				user.setBalance(userDTO.getBalance());
				user.setLastModifiedDate(ZonedDateTime.now(ZoneId.of(Constants.IST)));
				userRepository.save(user);
				
					logger.info("Balance update request has been successfully completed with payload "+objectMapper.writeValueAsString(user));
					return true;
				
			}
			
			
			} catch (Exception e) {
				logger.info(Constants.EXCEPTION_OCCURRED+" with message "+e.getMessage());
				return false;
			}
		
	}

	//@CacheEvict(value="userData", key="#userDTO.email")
	public Boolean deleteUser(String email) {
		
		try {
			
			User user = userRepository.findByEmail(email).orElse(null);
			
			if(user==null) {
				logger.info("Deletion of user request received for "+email+" is rejected as user does not exist for that mail");
				return false;
			}else {
				
				userRepository.delete(user);
				
					logger.info("Deletion of user request has been successfully completed for "+email);
					return true;
				
			}
			
			
			} catch (Exception e) {
				logger.info(Constants.EXCEPTION_OCCURRED+" with message "+e.getMessage());
				return false;
			}
		
	}

	
	public List<UserDTO> findAllById(List<String> userIds) {
		
		List<User> users = userRepository.findAllById(userIds);
		
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		
		for(User user: users) {
			
			UserDTO userDTO = new UserDTO(user.getId(),user.getEmail(),"",user.getName(),user.getBalance(),
					user.getRoleId(),user.getIsValidated(),user.getIsActive(),
					user.getCreationDate(),user.getLastModifiedDate());
			
			userDTOs.add(userDTO);
			
		}
		
		return userDTOs;
		
	}

	public Boolean updateBalanceInBulk(List<UserDTO> usersToBeUpdated) {
		
		try {
			
			List<String> userIds = new ArrayList<String>();
			Map<String,UserDTO> userIdToUserDTOMap = new HashMap<String,UserDTO>();
			
			for(UserDTO user: usersToBeUpdated) {
				
				userIds.add(user.getId());
				userIdToUserDTOMap.put(user.getId(), user);
				
			}
			
			List<User> users = userRepository.findAllById(userIds);
			List<User> usersToUpdate = new ArrayList<User>();
			
			
			
			
			if(users.isEmpty()) {
				logger.info("Mass Balance update request received is rejected as no user does not exist for given email IDs");
				return false;
			}else {
				
				for(User user: users) {
					
					UserDTO userDTO = userIdToUserDTOMap.get(user.getId());
					user.setBalance(userDTO.getBalance());
					user.setLastModifiedDate(ZonedDateTime.now(ZoneId.of(Constants.IST)));
					usersToUpdate.add(user);
				}
				
				userRepository.saveAll(usersToUpdate);
				
					logger.info("Mass Balance update request has been successfully completed with payload ");
					return true;
				
			}
			
			
			} catch (Exception e) {
				logger.info(Constants.EXCEPTION_OCCURRED+" with message "+e.getMessage());
				return false;
			}
		
	}

}
