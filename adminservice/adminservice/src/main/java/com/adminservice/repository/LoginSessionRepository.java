package com.adminservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adminservice.entity.LoginSession;

public interface LoginSessionRepository extends JpaRepository<LoginSession,String>  {
	
	@Query(value = "SELECT * FROM login_sessions WHERE email=?1", nativeQuery=true)
	public List<LoginSession> getLoginSessionsByEmail(String email);
	
	@Query(value = "SELECT * FROM login_sessions WHERE is_active=true", nativeQuery=true)
	public List<LoginSession> getActiveLoginSessions();
	
	@Query(value = "SELECT * FROM login_sessions WHERE token=?1", nativeQuery=true)
	public List<LoginSession> getLoginSessionsByToken(String token);

}
