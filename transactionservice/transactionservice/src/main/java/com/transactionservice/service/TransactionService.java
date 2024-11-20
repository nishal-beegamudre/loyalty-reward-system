package com.transactionservice.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transactionservice.dto.TopupDTO;
import com.transactionservice.dto.TopupResponseDTO;
import com.transactionservice.dto.TransactionDTO;
import com.transactionservice.dto.UserDTO;
import com.transactionservice.entity.ConsumptionStatusEnum;
import com.transactionservice.entity.Deduction;
import com.transactionservice.entity.Topup;
import com.transactionservice.entity.Transaction;
import com.transactionservice.entity.TransactionTypeEnum;
import com.transactionservice.feignclient.UserServiceFeignClient;
import com.transactionservice.repository.DeductionRepository;
import com.transactionservice.repository.TopupRepository;
import com.transactionservice.repository.TransactionRepository;
import com.transactionservice.util.Constants;

@Service
public class TransactionService {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
	
	@Autowired
	private UserServiceFeignClient userServiceFeignClient;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private TopupRepository topupRepository;
	
	@Autowired
	private DeductionRepository deductionRepository;
	
	@Value("${spring.topup.expiry.years}")
    private String expiryYears;

	public TopupResponseDTO topup(TopupDTO topupDTO) {
		
		try {
			
		logger.info("Request is being sent from transaction service to user service to fetch user for the email : "+topupDTO.getEmail());
		
		UserDTO user = userServiceFeignClient.getUserByEmail(topupDTO.getEmail());
		
		logger.info("Response has been received from user service to transaction service to fetch user for the email : "+topupDTO.getEmail());
		
		if(user!=null) {
			
			ZonedDateTime presentTime = ZonedDateTime.now(ZoneId.of(Constants.IST));
			double newBalance = user.getBalance() + topupDTO.getAmount();
			
			Topup topup = new Topup();
			topup.setAmount(topupDTO.getAmount());
			topup.setConsumptionStatus(ConsumptionStatusEnum.NOT_CONSUMED);
			topup.setEmail(topupDTO.getEmail());
			topup.setIsExpired(false);
			topup.setTransactionDate(presentTime);
			topup.setUserId(user.getId().toString());
			topup.setExpiryDate(presentTime.plusYears(Long.valueOf(expiryYears)));
			topup.setRemainingAmount(topupDTO.getAmount());
			topup.setLastModifiedDate(presentTime);
			
			topupRepository.save(topup);
			
			logger.info("New topup entry has been added with payload : "+objectMapper.writeValueAsString(topup));
			
			Transaction transaction = new Transaction();
			transaction.setAmount(topupDTO.getAmount());
			transaction.setEmail(topupDTO.getEmail());
			transaction.setIsSuccessful(true);
			transaction.setNewBalance(newBalance);
			transaction.setTransactionDate(presentTime);
			transaction.setTransactionType(TransactionTypeEnum.TOPUP);
			transaction.setUserId(user.getId().toString());

			transactionRepository.save(transaction);
			
			logger.info("New transaction entry has been added with payload : "+objectMapper.writeValueAsString(transaction));
			
			logger.info("A request to update balance is being sent from transaction service to user service via feign client for email : "+topupDTO.getEmail());
			
			userServiceFeignClient.updateBalance(new UserDTO(null,topupDTO.getEmail(),
					null,null,newBalance,null,null,null,null,null));
			
			logger.info("Response to update balance has been received from user service to transaction service via feign client for email : "+topupDTO.getEmail());
			
			return new TopupResponseDTO(true,newBalance,
					topupDTO.getEmail(),user.getId().toString(),
					transaction.getId().toString(),Constants.TOPUP_ADDED_SUCCESSFULLY);
			
		}else {
			
			logger.info("Topup request has been declined for the email "+topupDTO.getEmail()+" as user is not found for this email ID");
			
			return new TopupResponseDTO(false,0.0,
					topupDTO.getEmail(),null,
					null,Constants.USER_NOT_FOUND);
			
		}
		
		}catch(Exception e) {
			
			logger.info("Exception occurred "+e.getMessage());
			return new TopupResponseDTO(false,0.0,
					topupDTO.getEmail(),null,
					null,Constants.EXCEPTION_OCCURRED);
			
		}
		
		
		
	}

	public TopupResponseDTO deduct(TopupDTO topupDTO) {
		
		try {
			
			logger.info("Request is being sent from transaction service to user service to fetch user for the email : "+topupDTO.getEmail());
		
			UserDTO user = userServiceFeignClient.getUserByEmail(topupDTO.getEmail());
			
			logger.info("Response has been received from user service to transaction service to fetch user for the email : "+topupDTO.getEmail());
		
		if(user!=null) {
			
			if(user.getBalance()>=topupDTO.getAmount()) {
				
				List<Topup> topups = topupRepository.findActiveNotFullyConsumedTopupsByEmail
						(topupDTO.getEmail(),ConsumptionStatusEnum.FULLY_CONSUMED.toString());
				
				ZonedDateTime presentTime = ZonedDateTime.now(ZoneId.of(Constants.IST));
				
				double remainingBalance = topupDTO.getAmount();
				double newBalance = user.getBalance() - topupDTO.getAmount();
				
				StringBuilder topupIdsBuilder = new StringBuilder();
				List<Topup> fullyConsumedTopups = new ArrayList<Topup>();
				List<Topup> partiallyConsumedTopups = new ArrayList<Topup>();
				List<Topup> topupsToBeUpdated = new ArrayList<Topup>();
				
				double balanceToBeDeducted = 0.0;
				
				for(Topup topup: topups) {
					
					remainingBalance = remainingBalance - topup.getRemainingAmount();
					topupIdsBuilder.append(topup.getId().toString()+";");
					if(remainingBalance<0.0) {
						balanceToBeDeducted = remainingBalance + topup.getRemainingAmount();
						partiallyConsumedTopups.add(topup);
						break;
					}else if(remainingBalance==0.0) {
						fullyConsumedTopups.add(topup);
						break;
					}
					fullyConsumedTopups.add(topup);
					
				}
				
				for(Topup topup: fullyConsumedTopups) {
					
					topup.setRemainingAmount(0.0);
					topup.setConsumptionStatus(ConsumptionStatusEnum.FULLY_CONSUMED);
					topup.setLastModifiedDate(presentTime);
					
					logger.info("Topup with ID : "+topup.getId()+" for email : "+topup.getEmail()+" has been fully consumed");
					
					topupsToBeUpdated.add(topup);
				}
				
				for(Topup topup: partiallyConsumedTopups) {
					
					double amount = topup.getAmount() - balanceToBeDeducted;
					
					topup.setRemainingAmount(amount);
					topup.setConsumptionStatus(ConsumptionStatusEnum.PARTIALLY_CONSUMED);
					topup.setLastModifiedDate(presentTime);
					
					logger.info("Topup with ID : "+topup.getId()+" for email : "+topup.getEmail()+
							" has been partially consumed by deducting "+balanceToBeDeducted+", remaining balance is "
							+amount);
					
					topupsToBeUpdated.add(topup);
					
				}
				
				topupRepository.saveAll(topupsToBeUpdated);
				
				Deduction deduction = new Deduction();
				deduction.setAmount(topupDTO.getAmount());
				deduction.setEmail(topupDTO.getEmail());
				deduction.setTransactionDate(presentTime);
				deduction.setUserId(user.getId().toString());
				deduction.setTopupIds(topupIdsBuilder.toString());
				
				deductionRepository.save(deduction);
				
				logger.info("New deduction entry has been added with payload : "+objectMapper.writeValueAsString(deduction));
				
				Transaction transaction = new Transaction();
				transaction.setAmount(topupDTO.getAmount());
				transaction.setEmail(topupDTO.getEmail());
				transaction.setIsSuccessful(true);
				transaction.setNewBalance(newBalance);
				transaction.setTransactionDate(presentTime);
				transaction.setTransactionType(TransactionTypeEnum.DEDUCT);
				transaction.setUserId(user.getId().toString());

				transactionRepository.save(transaction);
				
				logger.info("New transaction entry has been added with payload : "+objectMapper.writeValueAsString(transaction));
				
				logger.info("A request to update balance is being sent from transaction service to user service via feign client for email : "+topupDTO.getEmail());
				
				userServiceFeignClient.updateBalance(new UserDTO(null,topupDTO.getEmail(),
						null,null,newBalance,null,null,null,null,null));
				
				logger.info("Response to update balance has been received from user service to transaction service via feign client for email : "+topupDTO.getEmail());
				
				return new TopupResponseDTO(true,newBalance,
						topupDTO.getEmail(),user.getId().toString(),
						transaction.getId().toString(),Constants.AMOUNT_DEDUCTED_SUCCESSFULLY);
				
				
			}else {
				
				logger.info("Deduction request has been declined for the email "+topupDTO.getEmail()+" as enough balance is not found for this email ID");
				
				return new TopupResponseDTO(false,0.0,
						topupDTO.getEmail(),null,
						null,Constants.ENOUGH_BALANCE_NOT_FOUND);
				
				
			}
			
			
			
			
			
			
		}else {
			
			logger.info("Deduction request has been declined for the email "+topupDTO.getEmail()+" as user is not found for this email ID");
			
			return new TopupResponseDTO(false,0.0,
					topupDTO.getEmail(),null,
					null,Constants.USER_NOT_FOUND);
			
		}
		
		}catch(Exception e) {
			
			logger.info("Exception occurred "+e.getMessage());
			return new TopupResponseDTO(false,0.0,
					topupDTO.getEmail(),null,
					null,Constants.EXCEPTION_OCCURRED);
			
		}
		
		
	}

	@Cacheable(value="passbook",key="#email")
	public List<TransactionDTO> getPassbook(String email) {
		
		logger.info("Request is being sent from transaction service to user service to fetch user for the email : "+email);
		
		UserDTO user = userServiceFeignClient.getUserByEmail(email);
		
		logger.info("Response has been received from user service to transaction service to fetch user for the email : "+email);
		
		if(user!=null) {
			
			List<Transaction> transactions = transactionRepository.findAllByEmail(email);
			List<TransactionDTO> transactionList = new ArrayList<TransactionDTO>();
			
			for(Transaction txn: transactions) {
				
				transactionList.add(new TransactionDTO(txn.getId(),txn.getAmount(),txn.getNewBalance(),txn.getIsSuccessful(),
						txn.getUserId(),txn.getEmail(),txn.getTransactionDate(),txn.getTransactionType()));
				
				
			}
			
			logger.info("Passbook request has been fulfilled for the email "+email);
			
			return transactionList;
			
		}else {
			
			logger.info("Passbook request has been declined for the email "+email+" as user is not found for this email ID");
			
			return null;
			
		}
		
		
	}

}
