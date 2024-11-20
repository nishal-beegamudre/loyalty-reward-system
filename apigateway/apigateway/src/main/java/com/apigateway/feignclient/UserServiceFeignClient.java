package com.apigateway.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.apigateway.dto.RoleDTO;
import com.apigateway.dto.UserDTO;

@FeignClient(name = "user-service", url = "http://localhost:8087")  // Replace with actual service URL
public interface UserServiceFeignClient {
	
	@GetMapping("/user/getRoleByName")
	RoleDTO getRoleByName(@RequestParam("name") String name);
	
	@PostMapping("/user/createUser")
	public String createUser(@RequestBody UserDTO userDTO);
	
	@PutMapping("/user/updateUser")
	public String updateUser(@RequestBody UserDTO userDTO);
	
}
