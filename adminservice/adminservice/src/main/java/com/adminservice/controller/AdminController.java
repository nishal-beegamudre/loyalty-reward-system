package com.adminservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adminservice.entity.LoginSession;
import com.adminservice.entity.SignupSession;
import com.adminservice.service.AdminService;
import com.adminservice.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class AdminController {
	
Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
    private AdminService adminService;
	
	@PostMapping("/auth/admin/generateAdminSecret")
    public String generateAdminSecret(@RequestParam("masterKey") String masterKey){
		
		try {
			
			logger.info("Request received to generate admin secret");
			String adminSecret = adminService.generateAdminSecret(masterKey);
			logger.info("Admin secret has been generated");
			return adminSecret;
			
		}catch(Exception e) {
			logger.debug("Exception occured : "+e.getMessage());
			return Constants.EXCEPTION_OCCURRED;
		}
		
		
	}
	
	@GetMapping("/admin/getAllLoginSessionsByEmailId")
    public List<LoginSession> getAllLoginSessionsByEmailId(@RequestParam("email") String email){
		
		try {
			
			logger.info("Request received to get list of all login sessions for the email : "+email);
			List<LoginSession> loginSessions = adminService.getLoginSessionsByEmailId(email);
			logger.info("Request fulfilled to get list of all login sessions for the email : "+email);
			return loginSessions;
			
		}catch(Exception e) {
			logger.debug("Exception occured : "+e.getMessage());
			return null;
		}
		
		
	}
	
	@GetMapping("/admin/getAllSignUpSessionsByEmailId")
    public List<SignupSession> getAllSignUpSessionsByEmailId(@RequestParam("email") String email){
		
		try {
			
			logger.info("Request received to get list of all signup sessions for the email : "+email);
			List<SignupSession> signupSessions = adminService.getAllSignUpSessionsByEmailId(email);
			logger.info("Request fulfilled to get list of all signup sessions for the email : "+email);
			return signupSessions;
			
		}catch(Exception e) {
			logger.debug("Exception occured : "+e.getMessage());
			return null;
		}
		
		
	}
	
	
	@GetMapping("/admin/validateAdminSecret/{adminSecret}")
    public Boolean validateAdminSecret(@PathVariable("adminSecret") String adminSecret) {
		
		try {
			
			logger.info("Request received to validate admin secret : "+adminSecret);
			Boolean isValidationSuccessful = adminService.validateAdminSecret(adminSecret);
			logger.info("Request to validate admin secret : Result - "+isValidationSuccessful);
			return isValidationSuccessful;
			
		}catch(Exception e) {
			logger.debug("Exception occured : "+e.getMessage());
			return false;
		}
		
	}
	
	
	@GetMapping("/auth/admin/test")
    public String test() {
		
		return "Admin Auth Test Passed";
		
	}

}
