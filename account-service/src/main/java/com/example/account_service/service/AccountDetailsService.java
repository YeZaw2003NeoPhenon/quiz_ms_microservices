package com.example.account_service.service;


import com.example.account_service.dao.AccountDao;
import com.example.account_service.model.Account;
import com.example.account_service.model.AccountDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {

    private final AccountDao accountDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

       Account account = accountDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Account not found!"));
       return new AccountDetails(account);
    }

}
