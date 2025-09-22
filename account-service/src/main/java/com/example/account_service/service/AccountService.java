package com.example.account_service.service;

import com.example.account_service.dao.AccountDao;
import com.example.account_service.dto.AccountRequest;
import com.example.account_service.dto.AccountResponse;
import com.example.account_service.dto.LoginRequest;
import com.example.account_service.jwt.JwtFilter;
import com.example.account_service.jwt.JwtUtils;
import com.example.account_service.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountDao accountDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AccountResponse register(AccountRequest request){

        if (accountDao.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        Account account = new Account();
        account.setEmail(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setRole(request.getRole());

        Account createdAcc = accountDao.save(account);

        return new AccountResponse(createdAcc.getEmail(), createdAcc.getRole());
    }

    public String login(LoginRequest loginRequest){
       Account account =  accountDao.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new RuntimeException("Account not found!"));

       if(!passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())){
           throw new RuntimeException("Invalid Credentials");
       }

       return jwtUtils.generateToken(account.getEmail(), account.getRole());
    }

    public AccountResponse validate(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if(!jwtUtils.validateToken(token)){
            throw new RuntimeException("Invalid or expired token");
        }
        return jwtUtils.extractAccDetailsFromToken(token);
    }


}
