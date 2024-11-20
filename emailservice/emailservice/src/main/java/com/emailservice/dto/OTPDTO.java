package com.emailservice.dto;

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
public class OTPDTO {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("otp")
	private String otp;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("isUsed")
	private Boolean isUsed;
	
	@JsonProperty("creationDate")
	private ZonedDateTime creationDate;
	
	@JsonProperty("lastModifiedDate")
	private ZonedDateTime lastModifiedDate;
	
	@JsonProperty("expiryDate")
	private ZonedDateTime expiryDate;

}
