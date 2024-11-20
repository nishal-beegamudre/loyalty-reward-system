package com.apigateway.kafka.producer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.apigateway.dto.SessionEntryDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaProducer {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Value("${spring.kafka.topic.signup}")
    private String signupSessionEntry; 

    @Value("${spring.kafka.topic.signin}")
    private String signinSessionEntry; 
    
    @Value("${spring.kafka.topic.logout}")
    private String logoutSessionEntry; 
    
    @Value("${spring.kafka.topic.modify}")
    private String modifySessionEntry; 
    
    
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    
    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(SessionEntryDTO sessionEntry,String topicName){		
		
    	try {

    	logger.info("Kafka message is being sent from API Gateway Service - Topic is "+topicName+" and message is "+objectMapper.writeValueAsString(sessionEntry));

    	String message = objectMapper.writeValueAsString(sessionEntry);
    	
		/*Message<SessionEntryDTO> message = MessageBuilder
		.withPayload(sessionEntry)	
		.setHeader(KafkaHeaders.TOPIC, topicName)
		.build();*/

		kafkaTemplate.send(topicName,message);
		
    	}catch(Exception e) {
    		logger.debug("Exception caught "+e.getMessage());
    	}

    }


}
