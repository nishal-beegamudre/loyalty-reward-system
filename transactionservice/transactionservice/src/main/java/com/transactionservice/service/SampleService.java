package com.transactionservice.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.transactionservice.dto.SampleDTO;

@Service
public class SampleService {

	@Cacheable(value = "sample1234", key = "#subject")
    public SampleDTO readMail(String subject) {
    	
    	SampleDTO sampleDTO = new SampleDTO();
    	sampleDTO.setBody("No Body");
    	sampleDTO.setSubject("No Subject");
    	sampleDTO.setTo("Nothing");
    	sampleDTO.setSubjectCharacterCount(3);
    	
    	return sampleDTO;
    	
    }
	
}
