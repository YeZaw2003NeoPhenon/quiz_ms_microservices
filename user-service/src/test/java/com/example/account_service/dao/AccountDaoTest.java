package com.example.account_service.dao;

import com.example.account_service.model.Account;
import com.example.account_service.model.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class AccountDaoTest {

    @Autowired
    private AccountDao dao;

    private Account account;

    @BeforeEach
    void setUp(){
        account = new Account("neo@gmail.com", "neoneo", Role.ADMIN);
        dao.save(account);
    }

    @Test
    void testFindByEmail(){
       Account account = dao.findByEmail("neo@gmail.com").get();
       assertThat(account.getEmail()).isEqualTo("neo@gmail.com");
       assertThat(account.getRole()).isEqualTo(Role.ADMIN);
       assertThat(account.getPassword()).isEqualTo("neoneo");
    }

    @AfterEach
    void tearDown(){
        dao.deleteAll();
    }

}