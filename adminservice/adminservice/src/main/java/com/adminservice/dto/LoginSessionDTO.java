package com.adminservice.dto;

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
public class LoginSessionDTO {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("isActive")
	private Boolean isActive;
	
	@JsonProperty("token")
	private String token;
	
	@JsonProperty("creationDate")
	private ZonedDateTime creationDate;
	
	@JsonProperty("lastModifiedDate")
	private ZonedDateTime lastModifiedDate;

}
