package com.transactionservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transactionservice.dto.TopupDTO;
import com.transactionservice.dto.TopupResponseDTO;
import com.transactionservice.dto.TransactionDTO;
import com.transactionservice.service.TransactionService;
import com.transactionservice.util.Constants;
import com.transactionservice.util.Utility;

@RestController
public class TransactionController {
	
private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private Utility utility;
	
	@PostMapping("/transaction/topup")
    public TopupResponseDTO topup(@RequestBody TopupDTO topupDTO){

		
		try {
		
		if(utility.isRequestParamValid(topupDTO)) {
			
			logger.info("Request received to add topup amount "+topupDTO.getAmount()+" to "+topupDTO.getEmail()+" in transaction service with payload "+objectMapper.writeValueAsString(topupDTO));
			TopupResponseDTO topupResponse = transactionService.topup(topupDTO);
			logger.info("Request to add topup amount "+topupDTO.getAmount()+" to "+topupDTO.getEmail()+" is completed with payload "+objectMapper.writeValueAsString(topupResponse));
			return topupResponse;
			
		}else {
			logger.debug("Invalid request body : "+objectMapper.writeValueAsString(topupDTO));
			return new TopupResponseDTO(false,0.0,null,null,null,Constants.INVALID_REQUEST_BODY);
		}
				
			
		}catch(Exception e) {
			logger.debug("Exception occured : "+e.getMessage());
			return new TopupResponseDTO(false,0.0,null,null,null,Constants.EXCEPTION_OCCURRED);
		}
		
		
	}
	
	
	@PostMapping("/transaction/deduct")
    public TopupResponseDTO deduct(@RequestBody TopupDTO topupDTO){

		
		try {
		
		if(utility.isRequestParamValid(topupDTO)) {
			
			logger.info("Request received to deduct amount "+topupDTO.getAmount()+" from "+topupDTO.getEmail()+" in transaction service with payload "+objectMapper.writeValueAsString(topupDTO));
			TopupResponseDTO topupResponse = transactionService.deduct(topupDTO);
			logger.info("Request to deduct amount "+topupDTO.getAmount()+" from "+topupDTO.getEmail()+" is completed with payload "+objectMapper.writeValueAsString(topupResponse));
			return topupResponse;
			
		}else {
			logger.debug("Invalid request body : "+objectMapper.writeValueAsString(topupDTO));
			return new TopupResponseDTO(false,0.0,null,null,null,Constants.INVALID_REQUEST_BODY);
		}
				
			
		}catch(Exception e) {
			logger.debug("Exception occured : "+e.getMessage());
			return new TopupResponseDTO(false,0.0,null,null,null,Constants.EXCEPTION_OCCURRED);
		}
		
		
	}
	
	
	@GetMapping("/transaction/passbook")
    public List<TransactionDTO> getUserByEmail(@RequestParam("email") String email){
		
		try {
			
			logger.info("Request received to get passbook in transaction service with email "+email);
			List<TransactionDTO> passbook = transactionService.getPassbook(email);
			logger.info("Request to get the passbook is completed in transaction service with payload : +"+
					objectMapper.writeValueAsString(passbook));
			return passbook;
			
		}catch(Exception e) {
			logger.debug("Exception occured : "+e.getMessage());
			return null;
		}
		
	}
	

}
