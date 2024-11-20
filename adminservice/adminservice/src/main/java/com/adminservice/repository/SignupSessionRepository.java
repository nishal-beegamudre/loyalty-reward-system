package com.adminservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adminservice.entity.SignupSession;

public interface SignupSessionRepository extends JpaRepository<SignupSession,String>  {
	
	@Query(value = "SELECT * FROM signup_sessions WHERE email=?1", nativeQuery=true)
	public List<SignupSession> getSignupSessionsByEmail(String email);
	
	@Query(value = "SELECT * FROM signup_sessions WHERE is_active=true", nativeQuery=true)
	public List<SignupSession> getActiveSignupSessions();
	
	@Query(value = "SELECT * FROM signup_sessions WHERE token=?1", nativeQuery=true)
	public List<SignupSession> getSignupSessionsByToken(String token);

}
