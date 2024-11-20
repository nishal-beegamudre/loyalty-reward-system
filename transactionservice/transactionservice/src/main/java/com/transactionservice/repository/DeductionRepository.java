package com.transactionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transactionservice.entity.Deduction;

public interface DeductionRepository extends JpaRepository<Deduction,String>   {

}
