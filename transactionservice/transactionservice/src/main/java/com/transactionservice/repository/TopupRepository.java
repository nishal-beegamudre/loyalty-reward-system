package com.transactionservice.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.transactionservice.entity.Topup;

public interface TopupRepository extends JpaRepository<Topup,String>   {

	@Query(value = "SELECT * FROM topups WHERE is_expired=false"+
	" AND email=?1 AND consumption_status_enum<>?2"+
			" ORDER BY expiry_date ASC", nativeQuery=true)
	List<Topup> findActiveNotFullyConsumedTopupsByEmail(String email, String fullyConsumed);

	@Query(value = "SELECT * FROM topups WHERE is_expired=false AND expiry_date>=?1", nativeQuery=true)
	List<Topup> findExpiredTopups(ZonedDateTime presentTime);

}
