package com.transactionservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transactionservice.entity.Transaction;

public interface TransactionRepository  extends JpaRepository<Transaction,String>  {

	List<Transaction> findAllByEmail(String email);

}
