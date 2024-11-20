package com.transactionservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data @Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopupResponseDTO {
	
	@JsonProperty("status")
	private Boolean status;
	
	@JsonProperty("newBalance")
	private double newBalance;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("transactionId")
	private String transactionId;
	
	@JsonProperty("message")
	private String message;

}
