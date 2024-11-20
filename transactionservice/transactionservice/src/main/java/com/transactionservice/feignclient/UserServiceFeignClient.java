package com.transactionservice.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.transactionservice.dto.UserDTO;

@FeignClient(name = "user-service", url = "localhost:8087")
public interface UserServiceFeignClient {
	
	@PutMapping("/user/updateBalance")
    public Boolean updateBalance(@RequestBody UserDTO userDTO);
	
	@GetMapping("/user/getUserByEmail")
    public UserDTO getUserByEmail(@RequestParam("email") String email);

	@GetMapping("/user/findAllById")
	public List<UserDTO> findAllById(@RequestBody List<Long> userIds);

	@PutMapping("/user/updateBalanceInBulk")
	public Boolean updateBalanceInBulk(@RequestBody List<UserDTO> usersToBeUpdated);

}
