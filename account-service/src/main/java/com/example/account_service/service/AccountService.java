package com.example.account_service.service;

import com.example.account_service.dao.AccountDao;
import com.example.account_service.dto.AccountRequest;
import com.example.account_service.dto.AccountResponse;
import com.example.account_service.dto.LoginRequest;
import com.example.account_service.jwt.JwtFilter;
import com.example.account_service.jwt.JwtUtils;
import com.example.account_service.model.Account;
import com.example.account_service.model.AccountDetails;
import com.example.account_service.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountDao accountDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

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
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      loginRequest.getEmail(),
                      loginRequest.getPassword()));
     AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();
     if(accountDetails == null){
         throw new RuntimeException("Credentials are invalid for authentication");
     }

     Role role = accountDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).map(Role::valueOf).findAny().get();
     return jwtUtils.generateToken(accountDetails.getUsername(), role);
    }

    public AccountResponse validate(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if(!jwtUtils.validateToken(token)){
            throw new RuntimeException("Invalid or expired token");
        }
        return jwtUtils.extractAccDetailsFromToken(token);
    }

    public List<AccountResponse> getAllAccounts(){
        return accountDao
                .findAll()
                .stream()
                .map(res -> new AccountResponse(res.getEmail(),res.getRole()))
                .collect(Collectors.toList());
    }

}
