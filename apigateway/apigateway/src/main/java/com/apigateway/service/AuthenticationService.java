package com.apigateway.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apigateway.config.TokenProvider;
import com.apigateway.dto.LoginUserDTO;
import com.apigateway.dto.RegisterUserDTO;
import com.apigateway.dto.RoleDTO;
import com.apigateway.dto.SessionEntryDTO;
import com.apigateway.dto.SignupResponseDTO;
import com.apigateway.dto.UserDTO;
import com.apigateway.dto.ValidateOtpDTO;
import com.apigateway.entity.User;
import com.apigateway.feignclient.AdminServiceFeignClient;
import com.apigateway.feignclient.EmailServiceFeignClient;
import com.apigateway.feignclient.UserServiceFeignClient;
import com.apigateway.kafka.producer.KafkaProducer;
import com.apigateway.repository.UserRepository;
import com.apigateway.util.Constants;
import com.apigateway.util.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	
	@Value("${spring.kafka.topic.signup}")
    private String signupSessionEntry; 
    
    @Value("${spring.kafka.topic.logout}")
    private String logoutSessionEntry; 
	
	@Autowired
    private final UserRepository userRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
    
	@Autowired
    private final PasswordEncoder passwordEncoder;
	
	@Autowired
	private final AdminServiceFeignClient adminServiceFeignClient;
	
	@Autowired
	private final UserServiceFeignClient userServiceFeignClient;
	
	@Autowired
	private final EmailServiceFeignClient emailServiceFeignClient;
	
	@Autowired
	private final KafkaProducer kafkaProducer;
	
	@Autowired
    private final ReactiveUserDetailsService userDetailsService;
    
	@Autowired
	private final TokenProvider tokenProvider;
	
	@Autowired
	private final Utility utility;



    
    public SignupResponseDTO signup(RegisterUserDTO input){
    	
    	try {
    	
    	if(Objects.nonNull(input)&&
    			Objects.nonNull(input.getEmail())&&
    			Objects.nonNull(input.getRole())&&
    			Objects.nonNull(input.getName())&&
    			Objects.nonNull(input.getPassword())&&
    			(input.getEmail().contains(".com"))&&
    			(input.getEmail().contains("@"))) {
    		
    		User existingUser = userRepository.findByEmail(input.getEmail()).orElse(null);
        	logger.info("Line 105");
    		RoleDTO role = userServiceFeignClient.getRoleByName(input.getRole());
    		logger.info("Line 107");
        	if(existingUser==null) {
        		
        		if(role==null) {
	        		
	        		return new SignupResponseDTO(null,null,null,null,Constants.INVALID_ROLE);
        			
        		}
        		
        		if((input.getAdminSecret()==null)&&
        				((role.getIsAdmin())||(role.getIsSuperAdmin()))) {
        			
        			return new SignupResponseDTO(null,null,null,null,Constants.ADMIN_SECRET_CANNOT_BE_NULL);
        			
        		}
        		if(input.getAdminSecret()!=null) {
        			
        			logger.info("Sending internal request to admin service to validate admin secret "+
        			input.getAdminSecret()+" for the email ID "+input.getEmail());
            		Boolean isValidated = adminServiceFeignClient.validateAdminSecret(input.getAdminSecret());
            		logger.info("validation of admin secret for the email ID "+input.getEmail()+" is "+isValidated);
            		if(!isValidated) {
            			return new SignupResponseDTO(null,null,null,null,Constants.INVALID_ADMIN_SECRET);
            		}	
        		}
        		logger.info("Line 132");
        		User user = new User();
        		UserDTO userDTO = new UserDTO();
        		
                user.setName(input.getName());
                userDTO.setName(input.getName());
                
                user.setEmail(input.getEmail());
                userDTO.setEmail(input.getEmail());
                
                String password = passwordEncoder.encode(input.getPassword());
                user.setPassword(password);
                userDTO.setPassword(password);
                
                user.setIsSuperAdmin((role.getIsSuperAdmin())||(role.getIsAdmin()));
                userDTO.setIsValidated(false);
                
                userDTO.setRoleId(role.getId());
                
                user.setIsCustomerUser(role.getIsCustomerUser());
                user.setIsAdmin(false); 
                
                logger.info("Sending internal request to user service to create user for email ID : "+userDTO.getEmail()
                +" and payload is : "+objectMapper.writeValueAsString(userDTO));
                String isSuccessful = userServiceFeignClient.createUser(userDTO);
                logger.info("User Creation for email : "+userDTO.getEmail()+" and Result : "+isSuccessful);
                
                if(isSuccessful.equals(Constants.USER_CREATED_SUCCESSFULLY)) {
                	
                	logger.info("Sending internal request to email service to send email with OTP to the email ID : "+
                			input.getEmail());
                	Boolean isEmailSentSuccessfully = emailServiceFeignClient.sendEmail(input.getEmail());
                	logger.info("Email OTP for email : "+userDTO.getEmail()+" and Result : "+isSuccessful);
                	
                	if(isEmailSentSuccessfully) {
                		
                		userRepository.save(user);
                        return utility.userPopulator(user);
                		
                	}else {	
                		return new SignupResponseDTO(null,null,null,null,Constants.ERROR_WHILE_SENDING_EMAIL);
                	}
                }else {
                	return new SignupResponseDTO(null,null,null,null,Constants.ERROR_WHILE_CREATING_USER);
                }	
        	}
        	else {
        		return new SignupResponseDTO(null,null,null,null,Constants.USER_EXISTS_ALREADY);
        	}	
    	}else {
    		return new SignupResponseDTO(null,null,null,null,Constants.INVALID_REQUEST_BODY);
    	}
    	
    	}catch(Exception e) {
    		logger.debug("Exception occured : "+e.getMessage());
    		return new SignupResponseDTO(null,null,null,null,e.getMessage());
    	}
    	
        
    }

    
    public Mono<LoginResponse> authenticate(LoginUserDTO input) {

    	User user = userRepository.findByEmail(input.getEmail()).orElse(null);
    	if((user!=null)&&(passwordEncoder.encode(input.getPassword()).equals(user.getPassword()))){
    		
    		
    	}
    	StringBuilder stringBuilder = new StringBuilder();
    	Mono<UserDetails> userdetails123 = userDetailsService.findByUsername(input.getEmail())
    			.filter(u -> { stringBuilder.append("Picking for user with email "+u.getUsername()+" and password "
    			    	+u.getPassword()+" authorities "+u.getAuthorities());
    				return passwordEncoder.matches(input.getPassword(), u.getPassword()); });

    	logger.info("Now heading for authentication login");
    	logger.info("String builder data is "+stringBuilder.toString());
    	return userDetailsService.findByUsername(input.getEmail())
                .filter(u -> {
                	logger.info("Picking for user with email "+u.getUsername()+" and password "
    			    	+u.getPassword()+" authorities "+u.getAuthorities());
                	return passwordEncoder.matches(input.getPassword(), u.getPassword());
                	
                })

                .map(tokenProvider::generateToken)
                .map(LoginResponse::new)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
    	
    }


	public Boolean verifyOTP(ValidateOtpDTO validateOtpDTO) {
		
		logger.info("Sending internal request to email service with email ID: "+validateOtpDTO.getEmail()+" and OTP : "+validateOtpDTO.getOtp());
		Boolean isValidationSuccessful = emailServiceFeignClient.validateOTP(validateOtpDTO);
		logger.info("OTP Validation for email : "+validateOtpDTO.getEmail()+" and Result : "+isValidationSuccessful);
		User user = userRepository.findByEmail(validateOtpDTO.getEmail()).orElse(null);
		
		if(isValidationSuccessful) {
			
			UserDTO userDTO = new UserDTO();
			userDTO.setEmail(validateOtpDTO.getEmail());
			userDTO.setIsValidated(true);
			
			logger.info("Sending internal request to user service with email ID: "+validateOtpDTO.getEmail()+" to update isValidated field to true");
			String isUpdateSuccessful = userServiceFeignClient.updateUser(userDTO);
			logger.info("User update request for email ID: "+validateOtpDTO.getEmail()+" and Result : "+isUpdateSuccessful);
			if(isUpdateSuccessful.equals(Constants.USER_CREATED_SUCCESSFULLY)) {
				
				SessionEntryDTO sessionEntry = new SessionEntryDTO();
				sessionEntry.setEmail(user.getEmail());
				sessionEntry.setPassword(user.getPassword());
				sessionEntry.setUserId(user.getId().toString());
				
				kafkaProducer.sendMessage(sessionEntry, signupSessionEntry);
				
				return true;
				
			}
			
			return false;
			
			
			
		}else {
			return false;
		}
		
	}


	public Boolean logout(String email, String authorizationHeader) {
		
		String jwtToken = authorizationHeader.substring(7);
		
		kafkaProducer.sendMessage(new SessionEntryDTO(null,email,null,jwtToken), 
				logoutSessionEntry);
 		
		return true;
	}
}