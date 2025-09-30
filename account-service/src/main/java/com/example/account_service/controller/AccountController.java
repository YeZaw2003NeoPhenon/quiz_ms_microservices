package com.example.account_service.controller;

import com.example.account_service.dto.AccountRequest;
import com.example.account_service.dto.AccountResponse;
import com.example.account_service.dto.LoginRequest;
import com.example.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<AccountResponse> register(@RequestBody AccountRequest request){
        return ResponseEntity.created(URI.create("/register")).body(accountService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(accountService.login(loginRequest));
    }

    @GetMapping("/validate")
    public ResponseEntity<AccountResponse> validate(@RequestHeader("Authorization") String authHeader){
        return ResponseEntity.ok(accountService.validate(authHeader));
    }

    @GetMapping("/all-accounts")
    public ResponseEntity<List<AccountResponse>> allAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

}