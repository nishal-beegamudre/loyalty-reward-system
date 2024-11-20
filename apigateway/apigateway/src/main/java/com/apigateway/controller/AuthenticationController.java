package com.apigateway.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apigateway.dto.LoginResponseDTO;
import com.apigateway.dto.LoginUserDTO;
import com.apigateway.dto.RegisterUserDTO;
import com.apigateway.dto.SessionEntryDTO;
import com.apigateway.dto.SignupResponseDTO;
import com.apigateway.dto.ValidateOtpDTO;
import com.apigateway.entity.User;
import com.apigateway.kafka.producer.KafkaProducer;
import com.apigateway.service.AuthenticationService;
import com.apigateway.service.JwtService;
import com.apigateway.service.LoginResponse;
import com.apigateway.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@RequestMapping
@RestController
public class AuthenticationController {
	
	Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@Value("${security.jwt.expiration-time}")
    private long jwtExpiration;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
    private final JwtService jwtService;
    
	@Autowired
    private final AuthenticationService authenticationService;
	
	@Autowired
	private final KafkaProducer kafkaProducer;
	
    @Value("${spring.kafka.topic.signin}")
    private String signinSessionEntry; 

    public AuthenticationController(JwtService jwtService, 
    		AuthenticationService authenticationService,
    		KafkaProducer kafkaProducer) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/auth/signup")
    public SignupResponseDTO signup(@RequestBody RegisterUserDTO registerUserDto){
    	
    	SignupResponseDTO registeredUser = new SignupResponseDTO();
    	
    	try {
    	
    	logger.info("Sign up request received for email : "
    	+registerUserDto.getEmail()+"; role : "+registerUserDto.getRole()+"; name : "
    			+registerUserDto.getName());
    	
    	registeredUser = authenticationService.signup(registerUserDto);
    	
    	}catch(Exception e) {
    		logger.debug("Exception occured : "+e.getMessage());
    	}

        return registeredUser;
    }
    
    @PostMapping("/auth/verifyOTP")
    public Boolean verifyOTP(@RequestBody ValidateOtpDTO validateOtpDTO) {
    	
    	Boolean isSuccessful = false;
    	
    	try {
    	
    	logger.info("OTP Verification started for email : "+validateOtpDTO.getEmail()+
    			" and payload is "+objectMapper.writeValueAsString(validateOtpDTO));
    	
    	isSuccessful = authenticationService.verifyOTP(validateOtpDTO);
    	
    	logger.info("OTP Verification ended for email : "+validateOtpDTO.getEmail()+
    		" and result : "+isSuccessful);
    	
    	}catch(Exception e) {
    		logger.debug("Exception occured : "+e.getMessage());
    	}
    	
    	return isSuccessful;
       
    }

    @PostMapping("/auth/login")
    public Mono<LoginResponse> login(@RequestBody LoginUserDTO loginUserDto){
    	
    	try {
    	
    	if(Objects.nonNull(loginUserDto)&&
    			Objects.nonNull(loginUserDto.getEmail())&&
    			Objects.nonNull(loginUserDto.getPassword())) {
    	
    		logger.info("Login request received for email : "+loginUserDto.getEmail());
    		
    		Mono<LoginResponse> authenticatedUser = authenticationService.authenticate(loginUserDto);
			
			logger.info("Login request successful for email : "+loginUserDto.getEmail());
			
			return authenticatedUser;
    		
    	}else {
    		
    		logger.info("Login request received with Invalid Request Body : "
    		+objectMapper.writeValueAsString(loginUserDto));
            return Mono.just(LoginResponse(Constants.INVALID_REQUEST_BODY));

    		
    	}
    	
    	}catch(Exception e) {
    		logger.debug("Exception occured : "+e.getMessage());

    		return Mono.just(LoginResponse("Exception hsa been caught"+e.getMessage()));

    	}
    	
        
    }
    
    
    private LoginResponse LoginResponse(String string) {

		return LoginResponse(string);
	}

	@PostMapping("/logout/{email}")
    public Boolean logout(@PathVariable("email") String email,
    		@RequestHeader("Authorization") String authorizationHeader) {
    	
    	Boolean isSuccessful = authenticationService.logout(email,authorizationHeader);
    	
    	logger.info("Log out request for the email ID "+email+
    			" : Result is "+isSuccessful);
    	
    	return isSuccessful;
       
    }
    
    @GetMapping("/auth/test")
    public String test() {
    	
    	return "Working fine auth test";
    	
    }
    
    @GetMapping("/test")
    public String testWithoutAuth() {
    	
    	return "Working fine test";
    	
    }
    
}

