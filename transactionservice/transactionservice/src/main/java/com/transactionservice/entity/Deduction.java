package com.transactionservice.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name="deductions")
public class Deduction implements Serializable {

	@Id
	@GenericGenerator(name = "deduction-key-generator", strategy = "com.transactionservice.keygenerator.DeductionKeyGenerator")
	@GeneratedValue(generator = "deduction-key-generator")
	@JsonProperty("id")
	private String id;
	
	@Column(name="amount")
	@JsonProperty("amount")
	private Double amount;
	
	@Column(name="user_id")
	@JsonProperty("userId")
	private String userId;
	
	@Column(name="email")
	@JsonProperty("email")
	private String email;
	
	@Column(name="transaction_date")
	@JsonProperty("transactionDate")
	@TimeZoneStorage(TimeZoneStorageType.NATIVE)
	private ZonedDateTime transactionDate;
	

	@Column(name="topup_ids")
	@JsonProperty("topupIds")
	private String topupIds;
	
}
