package com.transactionservice.dto;

import java.io.Serializable;

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
public class SampleDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JsonProperty("to")
	private String to;
	
	@JsonProperty("subject")
	private String subject;
	
	@JsonProperty("body")
	private String body;
	
	@JsonProperty("subjectCharacterCount")
	private int subjectCharacterCount;
	
}

