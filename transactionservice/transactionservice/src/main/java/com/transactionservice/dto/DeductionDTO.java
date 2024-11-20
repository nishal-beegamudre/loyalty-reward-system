package com.transactionservice.dto;

import java.time.ZonedDateTime;

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
public class DeductionDTO {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("amount")
	private Double amount;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("transactionDate")
	private ZonedDateTime transactionDate;
	
	@JsonProperty("topupIds")
	private String topupIds;

}
