package com.apigateway.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.apigateway.dto.ValidateOtpDTO;

@FeignClient(name = "email-service", url = "http://localhost:8084")
public interface EmailServiceFeignClient {
	
	@PostMapping("/email/sendEmail/{emailId}")
    public Boolean sendEmail(@PathVariable("emailId") String emailId);
	
	@PutMapping("/email/validateOTP")
	public Boolean validateOTP(@RequestBody ValidateOtpDTO validateOtpDTO);

}
