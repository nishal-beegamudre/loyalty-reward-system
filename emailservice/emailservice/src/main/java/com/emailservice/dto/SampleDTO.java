package com.emailservice.dto;

import java.io.Serializable;

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

	private String to;
	
	
	private String subject;
	
	
	private String body;
	
	
	private int subjectCharacterCount;
	
}
