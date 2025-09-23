package com.example.account_service.service;

import com.example.account_service.dao.AccountDao;
import com.example.account_service.model.Account;
import com.example.account_service.model.AccountDetails;
import com.example.account_service.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
class AccountDetailsServiceTest {

    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private AccountDetailsService accountDetailsService;

    private Account account;

    @BeforeEach
    void setUp(){
        account = new Account("neo@gmail.com", "neoneo", Role.ADMIN);
    }

    @Test
    void testLoadUserByEmail(){
      when(accountDao.findByEmail(anyString())).thenReturn(Optional.of(account));
      UserDetails accountDetails = (AccountDetails) accountDetailsService.loadUserByUsername("neo@gmail.com");
      assertThat(accountDetails.getUsername()).isEqualTo("neo@gmail.com");
      assertThat(accountDetails.getPassword()).isEqualTo("neoneo");
      assertThat(accountDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).findAny().orElse(null)).isEqualTo("ROLE_ADMIN");
    }

}