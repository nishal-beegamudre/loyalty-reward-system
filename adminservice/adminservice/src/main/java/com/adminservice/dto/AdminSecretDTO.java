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
public class AdminSecretDTO {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("secret")
	private String secret;
	
	@JsonProperty("isUsed")
	private Boolean isUsed;
	
	@JsonProperty("creationDate")
	private ZonedDateTime creationDate;
	
	@JsonProperty("lastModifiedDate")
	private ZonedDateTime lastModifiedDate;

}
