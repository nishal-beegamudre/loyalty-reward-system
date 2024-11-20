package com.adminservice.kafka.consumer;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.adminservice.dto.SessionEntryDTO;
import com.adminservice.entity.LoginSession;
import com.adminservice.entity.SignupSession;
import com.adminservice.repository.AdminSecretRepository;
import com.adminservice.repository.LoginSessionRepository;
import com.adminservice.repository.SignupSessionRepository;
import com.adminservice.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumer {
	
	@Value("${spring.kafka.topic.signup}")
    private String signupSessionEntry; 

    @Value("${spring.kafka.topic.signin}")
    private String signinSessionEntry; 
    
    @Value("${spring.kafka.topic.logout}")
    private String logoutSessionEntry; 
    
    @Value("${spring.kafka.topic.modify}")
    private String modifySessionEntry; 
    
    @Autowired
	private AdminSecretRepository adminSecretRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
	
	@Autowired
	private LoginSessionRepository loginSessionRepository;
	
	@Autowired
	private SignupSessionRepository signupSessionRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	
	@KafkaListener(topics = "${spring.kafka.topic.signup}")
	public void consumeSignup(String session){
        
		try {
			SessionEntryDTO sessionEntry = objectMapper.readValue(session, SessionEntryDTO.class);
		logger.info("Kafka message is received at Admin Service - Topic is "+signupSessionEntry+" and message is "+objectMapper.writeValueAsString(sessionEntry));
		
        SignupSession signupSession = new SignupSession();
        signupSession.setEmail(sessionEntry.getEmail());
        signupSession.setCreationDate(ZonedDateTime.now(ZoneId.of(Constants.IST)));
        signupSession.setIsActive(true);
        signupSession.setLastModifiedDate(ZonedDateTime.now(ZoneId.of(Constants.IST)));
        signupSession.setPassword(sessionEntry.getPassword());
        signupSession.setToken(sessionEntry.getToken());
        signupSession.setUserId(sessionEntry.getUserId());
        
        signupSessionRepository.save(signupSession);
        
        logger.info("Signup Session has been created for the email ID "+sessionEntry.getEmail());
        
		}catch(Exception e) {
        	logger.debug("Exception occurred "+e.getMessage());
        }
        
        
    }
	
	@KafkaListener(topics = "${spring.kafka.topic.signin}")
	public void consumeSignin(String session){
        
		try {
			SessionEntryDTO sessionEntry = objectMapper.readValue(session, SessionEntryDTO.class);
			logger.info("Kafka message is received at Admin Service - Topic is "+signinSessionEntry+" and message is "+objectMapper.writeValueAsString(sessionEntry));
	        
	        LoginSession loginSession = new LoginSession();
	        loginSession.setEmail(sessionEntry.getEmail());
	        loginSession.setCreationDate(ZonedDateTime.now(ZoneId.of(Constants.IST)));
	        loginSession.setIsActive(true);
	        loginSession.setLastModifiedDate(ZonedDateTime.now(ZoneId.of(Constants.IST)));
	        loginSession.setPassword(sessionEntry.getPassword());
	        loginSession.setToken(sessionEntry.getToken());
	        loginSession.setUserId(sessionEntry.getUserId());
	        
	        loginSessionRepository.save(loginSession);
	        
	        logger.info("Login Session has been created for the email ID "+sessionEntry.getEmail());
	        
			}catch(Exception e) {
	        	logger.debug("Exception occurred "+e.getMessage());
	        }
		
		
    }
	
	@KafkaListener(topics = "${spring.kafka.topic.logout}")
	public void consumeLogout(String session){
        
		try {
			SessionEntryDTO sessionEntry = objectMapper.readValue(session, SessionEntryDTO.class);
			logger.info("Kafka message is received at Admin Service - Topic is "+logoutSessionEntry+" and message is "+objectMapper.writeValueAsString(sessionEntry));
	        
	        List<LoginSession> loginSessions = loginSessionRepository.getLoginSessionsByToken(sessionEntry.getToken());
	        List<SignupSession> signupSessions = signupSessionRepository.getSignupSessionsByToken(sessionEntry.getToken());
	        
	        if(!loginSessions.isEmpty()) {
	        	LoginSession loginSession = loginSessions.get(0);
	        	loginSession.setIsActive(false);
	        	loginSessionRepository.save(loginSession);
	        }
	        
	        if(!signupSessions.isEmpty()) {
	        	SignupSession signupSession = signupSessions.get(0);
	        	signupSession.setIsActive(false);
	        	signupSessionRepository.save(signupSession);
	        }
	        
	        
	        logger.info("Session has been logged off for the email ID "+sessionEntry.getEmail());
	        
			}catch(Exception e) {
	        	logger.debug("Exception occurred "+e.getMessage());
	        }
		
    }
	
	@KafkaListener(topics = "${spring.kafka.topic.modify}")
	public void consumeModify(String session){
        
		try {
			SessionEntryDTO sessionEntry = objectMapper.readValue(session, SessionEntryDTO.class);
			logger.info("Kafka message is received at Admin Service - Topic is "+modifySessionEntry+" and message is "+objectMapper.writeValueAsString(sessionEntry));
	        
	        List<LoginSession> loginSessions = loginSessionRepository.getLoginSessionsByToken(sessionEntry.getToken());
	        List<SignupSession> signupSessions = signupSessionRepository.getSignupSessionsByToken(sessionEntry.getToken());
	        
	        if(!loginSessions.isEmpty()) {
	        	LoginSession loginSession = loginSessions.get(0);
	        	loginSession.setLastModifiedDate(ZonedDateTime.now(ZoneId.of(Constants.IST)));
	        	loginSessionRepository.save(loginSession);
	        }
	        
	        if(!signupSessions.isEmpty()) {
	        	SignupSession signupSession = signupSessions.get(0);
	        	signupSession.setLastModifiedDate(ZonedDateTime.now(ZoneId.of(Constants.IST)));
	        	signupSessionRepository.save(signupSession);
	        }
	        
	        
	        logger.info("Session has been modified for the email ID "+sessionEntry.getEmail());
	        
			}catch(Exception e) {
	        	logger.debug("Exception occurred "+e.getMessage());
	        }
		
    }


}
