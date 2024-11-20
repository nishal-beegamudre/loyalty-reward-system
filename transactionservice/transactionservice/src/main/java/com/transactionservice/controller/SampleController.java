package com.transactionservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transactionservice.dto.SampleDTO;
import com.transactionservice.service.SampleService;


@RestController
public class SampleController {
	
	@Autowired
	private SampleService sampleService;
	
	@PostMapping("/emailRead")
	public SampleDTO topup(@RequestParam("subject") String subject){
		
		SampleDTO readSample = sampleService.readMail(subject);
		
		return readSample;
		
	}

}
