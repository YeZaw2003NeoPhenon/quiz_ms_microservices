package com.example.account_service.service;

import com.example.account_service.dao.AccountDao;
import com.example.account_service.dto.AccountRequest;
import com.example.account_service.dto.AccountResponse;
import com.example.account_service.dto.LoginRequest;
import com.example.account_service.jwt.JwtUtils;
import com.example.account_service.model.Account;
import com.example.account_service.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
class AccountServiceTest {

    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private AccountService accountService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    private Account account;

    private AccountRequest request;

    private AccountResponse accountResponse;

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp(){
        account = new Account("neo@gmail.com", "neoneo", Role.ADMIN);
        request = new AccountRequest("neo@gmail.com", "neoneo", Role.ADMIN);
        accountResponse = new AccountResponse("neo@gmail.com", Role.ADMIN);
        loginRequest = new LoginRequest("neo@gmail.com","neoneo");
    }

    @Test
    void testRegisteration(){
        when(accountDao.findByEmail(any())).thenReturn(Optional.empty());
        when(accountDao.save(any())).thenReturn(account);
        accountResponse = accountService.register(request);

        assertThat(accountResponse.getEmail()).isEqualTo("neo@gmail.com");
        assertThat(accountResponse.getRole()).isEqualTo(Role.ADMIN);
        verify(accountDao).findByEmail(any());
        verify(accountDao).save(any());
    }

    @Test
    void testLogin(){
        when(accountDao.findByEmail(any())).thenReturn(Optional.of(account));
        when(jwtUtils.generateToken(account.getEmail(), account.getRole())).thenReturn("mocked-jwt-token");
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        String loginRes = accountService.login(loginRequest);
        assertThat(loginRes).isEqualTo("mocked-jwt-token");
        verify(accountDao).findByEmail(any());
        verify(passwordEncoder).matches(any(), any());
        verify(jwtUtils).generateToken(account.getEmail(), account.getRole());
    }

    @Test
     void testValidate(){
        when(jwtUtils.validateToken("mocked-jwt-token")).thenReturn(true);
        when(jwtUtils.extractAccDetailsFromToken("mocked-jwt-token")).thenReturn(accountResponse);

        accountResponse = accountService.validate("Bearer mocked-jwt-token");
        assertThat(accountResponse.getEmail()).isEqualTo("neo@gmail.com");
        verify(jwtUtils).validateToken("mocked-jwt-token");
        verify(jwtUtils).extractAccDetailsFromToken("mocked-jwt-token");
    }

}