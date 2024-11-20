package com.adminservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adminservice.entity.AdminSecret;

public interface AdminSecretRepository extends JpaRepository<AdminSecret,String>  {

	@Query(value = "SELECT * FROM admin_secrets WHERE secret=?1 AND is_used=false", nativeQuery=true)
	public List<AdminSecret> getActiveAdminSecretBySecret(String secret);
	
}
