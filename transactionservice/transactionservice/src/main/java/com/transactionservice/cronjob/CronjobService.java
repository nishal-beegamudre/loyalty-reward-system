package com.transactionservice.cronjob;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transactionservice.dto.UserDTO;
import com.transactionservice.entity.Topup;
import com.transactionservice.entity.Transaction;
import com.transactionservice.entity.TransactionTypeEnum;
import com.transactionservice.feignclient.UserServiceFeignClient;
import com.transactionservice.repository.TopupRepository;
import com.transactionservice.repository.TransactionRepository;
import com.transactionservice.service.TransactionService;
import com.transactionservice.util.Constants;

@Service
public class CronjobService {
	
	private static final Logger logger = LoggerFactory.getLogger(CronjobService.class);
	
	@Autowired
	private TopupRepository topupRepository;
	
	@Autowired
	private UserServiceFeignClient userServiceFeignClient;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Scheduled(cron="0 0 * * * *")
	public void killExpiredTopups() {
		
		try {
		
		logger.info("Cronjob to kill expired topups has been started");
		
		ZonedDateTime presentTime = ZonedDateTime.now(ZoneId.of(Constants.IST));
		
		List<Topup> topups = topupRepository.findExpiredTopups(presentTime);
		
		List<Long> userIds = new ArrayList<Long>();
		
		for(Topup topup: topups) {
			userIds.add(Long.valueOf(topup.getUserId()));
		}
		
		List<UserDTO> users = userServiceFeignClient.findAllById(userIds);
		Map<String,Double> userIdToBalanceMap = new HashMap<String,Double>();
		
		List<UserDTO> usersToBeUpdated = new ArrayList<UserDTO>();
		
		for(UserDTO user: users) {
			
			userIdToBalanceMap.put(user.getId(), user.getBalance())	;
			
		}
		
		List<Topup> updatingTopups = new ArrayList<Topup>();
		
		for(Topup topup: topups) {
			
			topup.setIsExpired(true);
			updatingTopups.add(topup);
			
			logger.info("Topup entry for email ID "+topup.getEmail()+" with amount "+topup.getAmount()+" has been expired and payload is "+objectMapper.writeValueAsString(topup));
			
			double newBalance = userIdToBalanceMap.get(topup.getId()) - topup.getAmount() ;
			
			Transaction transaction = new Transaction();
			transaction.setAmount(topup.getAmount());
			transaction.setEmail(topup.getEmail());
			transaction.setIsSuccessful(true);
			transaction.setNewBalance(newBalance);
			transaction.setTransactionDate(ZonedDateTime.now(ZoneId.of(Constants.IST)));
			transaction.setTransactionType(TransactionTypeEnum.EXPIRY);
			transaction.setUserId(topup.getUserId().toString());
			

			transactionRepository.save(transaction);
			
			logger.info("A new transaction to capture the expiry of topup entry for email ID "+topup.getEmail()+" with amount "+topup.getAmount()+" has been created with payload : "+objectMapper.writeValueAsString(transaction));
			
			
			usersToBeUpdated.add(new UserDTO(topup.getUserId(),topup.getEmail(),"","",
					newBalance,null,null,null,null,null));
			
			
		}
		logger.info("A request is being sent from transaction service to user service to update balance in bulk");
		
		Boolean isSuccessful = userServiceFeignClient.updateBalanceInBulk(usersToBeUpdated);
		
		logger.info("Response has been received from user service to transaction service to update balance in bulk with result "+isSuccessful);
		
		logger.info("Cronjob to kill expired topups has been ended");
		
		}catch(Exception e) {
			logger.info("Exception occurred "+e.getMessage());
			
		}
		
		
	}

}
