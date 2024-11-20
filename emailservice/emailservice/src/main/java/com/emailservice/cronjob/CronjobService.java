package com.emailservice.cronjob;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.emailservice.entity.OTP;
import com.emailservice.repository.OTPRepository;

@Service
public class CronjobService {
	
	private static final Logger logger = LoggerFactory.getLogger(CronjobService.class);
	
	@Autowired
	private OTPRepository otpRepository;
	
	private static final String IST = "Asia/Kolkata";
	
	
	@Scheduled(cron = "0 */15 * * * *")
	public void killExpiredOTPs() {
		
		logger.info("Cronjob to kill expired OTPs has been started");
		
		List<OTP> otps = otpRepository.findActiveOtps();	
		
		for(OTP otp: otps) {
			
			ZonedDateTime presentTime = ZonedDateTime.now(ZoneId.of(IST));
			
			if(presentTime.isAfter(otp.getExpiryDate())) {
				otp.setIsUsed(true);
				otp.setLastModifiedDate(presentTime);
				otpRepository.save(otp);
				logger.info("OTP : "+otp.getOtp()+" with email : "+otp.getEmail()+" has been expired. It is marked inactive");
			}
			
		}
		
		logger.info("Cronjob to kill expired OTPs has been ended");
		
	}

}
