package com.apigateway.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "admin-service", url = "http://localhost:8088")
public interface AdminServiceFeignClient {
	
	@GetMapping("/admin/validateAdminSecret/{adminSecret}")
    public Boolean validateAdminSecret(@PathVariable("adminSecret") String adminSecret);

}
