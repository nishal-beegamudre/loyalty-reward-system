package com.apigateway.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic.signup}")
    private String signupSessionEntry; 

    @Value("${spring.kafka.topic.signin}")
    private String signinSessionEntry; 
    
    @Value("${spring.kafka.topic.logout}")
    private String logoutSessionEntry; 
    
    @Value("${spring.kafka.topic.modify}")
    private String modifySessionEntry; 

    @Bean
    public NewTopic signupSessionEntryTopic(){
        return TopicBuilder.name(signupSessionEntry)
                .build();
    }

    @Bean
    public NewTopic signinSessionEntryTopic(){
        return TopicBuilder.name(signinSessionEntry)
                .build();
    }
    
    @Bean
    public NewTopic logoutSessionEntryTopic(){
        return TopicBuilder.name(logoutSessionEntry)
                .build();
    }
    
    @Bean
    public NewTopic modifySessionEntryTopic(){
        return TopicBuilder.name(modifySessionEntry)
                .build();
    }
    
}

