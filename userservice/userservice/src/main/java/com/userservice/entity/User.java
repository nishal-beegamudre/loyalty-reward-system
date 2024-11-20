package com.userservice.entity;

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
@Table(name="users")
public class User implements Serializable {
	
	@Id
	@GenericGenerator(name = "user-key-generator", strategy = "com.userservice.keygenerator.UserKeyGenerator")
	@GeneratedValue(generator = "user-key-generator")
	@JsonProperty("id")
	private String id;
	
	@Column(name="email")
	@JsonProperty("email")
	private String email;
	
	@Column(name="password")
	@JsonProperty("password")
	private String password;
	
	@Column(name="name")
	@JsonProperty("name")
	private String name;
	
	@Column(name="balance")
	@JsonProperty("balance")
	private Double balance;
	
	@Column(name="role_id")
	@JsonProperty("roleId")
	private String roleId;
	
	@Column(name="is_validated")
	@JsonProperty("isValidated")
	private Boolean isValidated;
	
	@Column(name="is_active")
	@JsonProperty("isActive")
	private Boolean isActive;
	
	@Column(name="creation_date")
	@JsonProperty("creationDate")
	@TimeZoneStorage(TimeZoneStorageType.NATIVE)
	private ZonedDateTime creationDate;
	
	@Column(name="last_modified_date")
	@JsonProperty("lastModifiedDate")
	@TimeZoneStorage(TimeZoneStorageType.NATIVE)
	private ZonedDateTime lastModifiedDate;

}
