package com.transactionservice.util;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.transactionservice.dto.TopupDTO;

@Service
public class Utility {
	
	
	public Boolean isRequestParamValid(TopupDTO topupRequest) {

		if(Objects.nonNull(topupRequest)) {
			Boolean isValid = true;
			isValid = ((Objects.nonNull(topupRequest.getEmail()))
					&&(topupRequest.getEmail().contains(".com"))&&
	    			(topupRequest.getEmail().contains("@"))
					&&(Objects.nonNull(topupRequest.getAmount()))
					&&(topupRequest.getAmount()>=0.0))?true:false;
			return isValid;
			
		}
		
		return false;
		
	}

}
