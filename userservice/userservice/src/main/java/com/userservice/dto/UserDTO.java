package com.userservice.dto;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.userservice.entity.Role;

import jakarta.persistence.Column;
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
public class UserDTO {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("balance")
	private Double balance;
	
	@JsonProperty("roleId")
	private String roleId;
	
	@JsonProperty("isValidated")
	private Boolean isValidated;
	
	@JsonProperty("isActive")
	private Boolean isActive;
	
	@JsonProperty("creationDate")
	private ZonedDateTime creationDate;
	
	@JsonProperty("lastModifiedDate")
	private ZonedDateTime lastModifiedDate;

}
