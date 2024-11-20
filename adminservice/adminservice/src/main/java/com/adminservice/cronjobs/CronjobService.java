package com.adminservice.cronjobs;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.adminservice.entity.LoginSession;
import com.adminservice.entity.SignupSession;
import com.adminservice.kafka.consumer.KafkaConsumer;
import com.adminservice.repository.LoginSessionRepository;
import com.adminservice.repository.SignupSessionRepository;
import com.adminservice.util.Constants;

@Service
public class CronjobService {
	
	private static final Logger logger = LoggerFactory.getLogger(CronjobService.class);
	
	@Autowired
	private LoginSessionRepository loginSessionRepository;
	
	@Autowired
	private SignupSessionRepository signupSessionRepository;
	
	@Scheduled(cron = "0 0 * * * ?")
	public void killInactiveSessions() {
		
		logger.info("Cronjob to kill inactive sessions has been started");
		
		List<LoginSession> loginSessions = loginSessionRepository.getActiveLoginSessions();	
		List<SignupSession> signupSessions = signupSessionRepository.getActiveSignupSessions();
		
		List<LoginSession> idleLoginSessions = new ArrayList<LoginSession>();
		List<SignupSession> idleSignupSessions = new ArrayList<SignupSession>();
		
		for(LoginSession session: loginSessions) {
			
			Boolean isIdle = isSessionIdle(session.getCreationDate());
			
			if(isIdle) {
				session.setIsActive(false);
				idleLoginSessions.add(session);
				logger.info("Login session with email : "+session.getEmail()+" has been bound to make inactive");
			}
			
		}
		loginSessionRepository.saveAll(idleLoginSessions);
		logger.info("All above mentioned login sessions have been made as inactive");
		
		for(SignupSession session: signupSessions) {
			
			Boolean isIdle = isSessionIdle(session.getCreationDate());
			
			if(isIdle) {
				session.setIsActive(false);
				idleSignupSessions.add(session);
				logger.info("Signup session with email : "+session.getEmail()+" has been bound to make inactive");
			}
			
		}
		signupSessionRepository.saveAll(idleSignupSessions);
		logger.info("All above mentioned signup sessions have been made as inactive");
		
		logger.info("Cronjob to kill inactive sessions has been ended");
		
	}

	private Boolean isSessionIdle(ZonedDateTime creationDate) {
		
		ZonedDateTime presentTime = ZonedDateTime.now(ZoneId.of(Constants.IST));
		Duration duration = Duration.between(creationDate, presentTime);
		
		if(duration.getSeconds()>3600L) {
			return true;
		}else {
			return false;
		}
	}

}
