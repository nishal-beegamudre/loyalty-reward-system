package com.apigateway.dto;

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
public class RegisterUserDTO {
	
	@JsonProperty("email")
	private String email;
    
	@JsonProperty("password")
    private String password;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("role")
    private String role;
    
    @JsonProperty("adminSecret")
    private String adminSecret;

}
