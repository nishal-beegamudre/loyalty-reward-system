package com.emailservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.emailservice.entity.OTP;

@Repository
public interface OTPRepository extends JpaRepository<OTP,String>  {

	@Query(value = "SELECT * FROM otps WHERE email=?1 AND is_used=false", nativeQuery=true)
	Optional<OTP> findByEmail(String email);
	
	@Query(value = "SELECT * FROM otps WHERE email=?1 AND is_used=false AND otp=?2", nativeQuery=true)
	Optional<OTP> findActiveEntryByEmailAndOtp(String email, String otp);
	
	@Query(value = "SELECT * FROM otps WHERE is_used=false", nativeQuery=true)
	List<OTP> findActiveOtps();
	
}
