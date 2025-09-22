package com.example.quiz_service.feign;

import com.example.quiz_service.dto.AccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("ACCOUNT-SERVICE")
public interface AccountInterface {
    @GetMapping("/api/v1/accounts/validate") ResponseEntity<AccountResponse> validate(@RequestHeader("Authorization") String token);
}
