package com.adminservice.service;

import java.security.SecureRandom;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.adminservice.controller.AdminController;
import com.adminservice.entity.AdminSecret;
import com.adminservice.entity.LoginSession;
import com.adminservice.entity.SignupSession;
import com.adminservice.repository.AdminSecretRepository;
import com.adminservice.repository.LoginSessionRepository;
import com.adminservice.repository.SignupSessionRepository;
import com.adminservice.util.Constants;

@Service
public class AdminService {
	
	Logger logger = LoggerFactory.getLogger(AdminService.class);
	
	@Value("${spring.admin.master.key}")
    private String encodedMasterKey;
	
	@Autowired
	private AdminSecretRepository adminSecretRepository;
	
	@Autowired
	private LoginSessionRepository loginSessionRepository;
	
	@Autowired
	private SignupSessionRepository signupSessionRepository;

	public String generateAdminSecret(String masterKey) {
		
		byte[] decodedBytes = Base64.getDecoder().decode(encodedMasterKey);
        String decodedMasterKey = new String(decodedBytes);
        
		logger.info("decodedMasterKey is "+decodedMasterKey);
		logger.info("masterKey is "+masterKey);
		
        if(decodedMasterKey.equals(masterKey)) {
        	String adminSecret = generateAdminSecret();
        	
        	AdminSecret adminSecretEntry = new AdminSecret();
        	adminSecretEntry.setIsUsed(false);
        	adminSecretEntry.setSecret(adminSecret);
        	adminSecretEntry.setCreationDate(ZonedDateTime.now(ZoneId.of(Constants.IST)));
        	adminSecretEntry.setLastModifiedDate(ZonedDateTime.now(ZoneId.of(Constants.IST)));
        	adminSecretRepository.save(adminSecretEntry);
        	
        	return adminSecret;
        	
        	
        	
        }else {
        	return Constants.INCORRECT_MASTER_KEY;
        }
        
		
	}
	
	public static String generateAdminSecret() {
        StringBuilder builder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        
        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Loop to generate the string of the specified length
        for (int i = 0; i < 16; i++) {
            int randomIndex = secureRandom.nextInt(ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(randomIndex));
        }

        return builder.toString();
    }

	@Cacheable(value = "loginSessions", key = "#email")
	public List<LoginSession> getLoginSessionsByEmailId(String email) {
		
		return loginSessionRepository.getLoginSessionsByEmail(email);
	}

	@Cacheable(value = "signupSessions", key = "#email")
	public List<SignupSession> getAllSignUpSessionsByEmailId(String email) {

		return signupSessionRepository.getSignupSessionsByEmail(email);
	}

	public Boolean validateAdminSecret(String secret) {
		
		List<AdminSecret> adminSecrets = adminSecretRepository.getActiveAdminSecretBySecret(secret);
		
		if(adminSecrets.isEmpty()) {
			return false;
		}else {
			
			for(AdminSecret entry: adminSecrets) {
				entry.setIsUsed(true);
				entry.setLastModifiedDate(ZonedDateTime.now(ZoneId.of(Constants.IST)));
				adminSecretRepository.save(entry);
			}
			
			return true;
		}
	}

}
