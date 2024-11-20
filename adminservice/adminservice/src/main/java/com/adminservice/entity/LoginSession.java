package com.adminservice.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data @Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="login_sessions")
public class LoginSession implements Serializable {
	
	@Id
	@GenericGenerator(name = "login-session-key-generator", strategy = "com.adminservice.keygenerator.LoginSessionKeyGenerator")
	@GeneratedValue(generator = "login-session-key-generator")
	@JsonProperty("id")
	private String id;
	
	@Column(name="user_id")
	@JsonProperty("userId")
	private String userId;
	
	@Column(name="email")
	@JsonProperty("email")
	private String email;
	
	@Column(name="password")
	@JsonProperty("password")
	private String password;
	
	@Column(name="is_active")
	@JsonProperty("isActive")
	private Boolean isActive;
	
	@Column(name="token")
	@JsonProperty("token")
	private String token;
	
	@Column(name="creation_date")
	@JsonProperty("creationDate")
	@TimeZoneStorage(TimeZoneStorageType.NATIVE)
	private ZonedDateTime creationDate;
	
	@Column(name="last_modified_date")
	@JsonProperty("lastModifiedDate")
	@TimeZoneStorage(TimeZoneStorageType.NATIVE)
	private ZonedDateTime lastModifiedDate;

}