package com.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userservice.entity.User;

public interface UserRepository extends JpaRepository<User,String>   {

	Optional<User> findByEmail(String email);

}
