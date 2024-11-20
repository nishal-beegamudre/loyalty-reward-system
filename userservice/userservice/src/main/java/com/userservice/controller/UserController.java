package com.userservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.dto.UserDTO;
import com.userservice.service.UserService;
import com.userservice.util.Constants;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private UserService userService;
	//private ObjectMapper objectMapper;
	
	/*@Autowired
	private UserService userService;*/
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public UserController(UserService userService) {
		
		this.userService = userService;
		
	}
	
	@PostMapping("/user/createUser")
    public String createUser(@RequestBody UserDTO userDTO){
		
		try {
			
			logger.info("Request received to create user in user service with payload "+objectMapper.writeValueAsString(userDTO));
			String isSuccessful = userService.createUser(userDTO);
			return isSuccessful;
			
		}catch(Exception e) {
			logger.debug("Exception occured : "+e.getMessage());
			return Constants.EXCEPTION_OCCURRED;
		}
		
		
	}
	
	@PutMapping("/user/updateUser")
    public String updateUser(@RequestBody UserDTO userDTO){
		
		try {
			
			logger.info("Request received to update user in user service with payload "+objectMapper.writeValueAsString(userDTO));
			String isSuccessful = userService.updateUser(userDTO);
			return isSuccessful;
			
		}catch(Exception e) {
			logger.debug("Exception occured : "+e.getMessage());
			return Constants.EXCEPTION_OCCURRED;
		}
		
		
	}
	
	@GetMapping("/user/getUserByEmail")
    public UserDTO getUserByEmail(@RequestParam("email") String email){
		
		try {
			
			logger.info("Request received to get user in user service with email "+email);
			String emailDecoded;
			if(email.contains("%40")) {
				emailDecoded = email.replace("%40", "@");
			}else {
				emailDecoded = email;
			}
			
			UserDTO user = userService.getUserByEmail(emailDecoded);
			logger.info("Request to get the user is completed in user service with payload : +"+
					objectMapper.writeValueAsString(user));
			return user;
			
		}catch(Exception e) {
			logger.debug("Exception occured : "+e.getMessage());
			return null;
		}
		
	}
	
	@GetMapping("/user/getBalanceByEmail")
    public Double getBalanceByEmail(@RequestParam("email") String email){
		
		try {
			
			logger.info("Request received to fetch balance of the user in user service with email "+email);
			Double balance = userService.getBalanceByEmail(email);
			logger.info("Request to fetch balance is completed for email "+email+" and balance is "+balance);
			return balance;
			
		}catch(Exception e) {
			logger.debug("Exception occured : "+e.getMessage());
			return null;
		}
		
	}
	
	
	@GetMapping("/user/getAllUsers")
    public List<UserDTO> getAllUsers(){
		
		try {
			
			logger.info("Request received to get all users ");
			List<UserDTO> users = userService.getAllUsers();
			logger.info("Request to get all users is completed");
			return users;
			
		}catch(Exception e) {
			logger.debug("Exception occured : "+e.getMessage());
			return null;
		}
		
	}
	
	
	@PutMapping("/user/updateBalance")
    public Boolean updateBalance(@RequestBody UserDTO userDTO){
		
		try {
			
			logger.info("Request received to update balance of the user in user service with payload "+objectMapper.writeValueAsString(userDTO));
			Boolean isSuccessful = userService.updateBalance(userDTO);
			return isSuccessful;
			
		}catch(Exception e) {
			logger.debug("Exception occured : "+e.getMessage());
			return false;
		}
		
		
	}
	
	
	@DeleteMapping("/user/deleteUser")
    public Boolean deleteUser(@RequestParam("email") String email){
		
		try {
			
			logger.info("Request received to delete the user in user service with email "+email);
			Boolean isSuccessful = userService.deleteUser(email);
			return isSuccessful;
			
		}catch(Exception e) {
			logger.debug("Exception occured : "+e.getMessage());
			return false;
		}
		
		
	}
	
	@GetMapping("/user/findAllById")
    public List<UserDTO> findAllById(@RequestBody List<String> userIds){
		
		try {
			
			logger.info("Request received to get all users by list of user ID inputs "+userIds);
			List<UserDTO> users = userService.findAllById(userIds);
			logger.info("Request to get all users by list of user ID inputs is completed");
			return users;
			
		}catch(Exception e) {
			logger.debug("Exception occured : "+e.getMessage());
			return null;
		}
		
	}
	
	
	@PutMapping("/user/updateBalanceInBulk")
    public Boolean updateBalanceInBulk(@RequestBody List<UserDTO> usersToBeUpdated){
		
		try {
			
			logger.info("Request received to update balance of the users in bulk user service");
			Boolean isSuccessful = userService.updateBalanceInBulk(usersToBeUpdated);
			logger.info("Request fulfilled with result : "+isSuccessful);
			return isSuccessful;
			
		}catch(Exception e) {
			logger.debug("Exception occured : "+e.getMessage());
			return false;
		}
		
		
	}

}
