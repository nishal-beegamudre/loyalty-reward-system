package com.apigateway.dto;

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
public class RoleDTO {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("isAdmin")
	private Boolean isAdmin;
	
	@JsonProperty("isSuperAdmin")
	private Boolean isSuperAdmin;
	
	@JsonProperty("isCustomerUser")
	private Boolean isCustomerUser;
	
	@JsonProperty("creationDate")
	private ZonedDateTime creationDate;

}

