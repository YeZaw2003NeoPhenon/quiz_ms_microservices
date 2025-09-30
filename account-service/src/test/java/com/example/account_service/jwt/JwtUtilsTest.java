package com.example.account_service.jwt;

import com.example.account_service.dto.AccountResponse;
import com.example.account_service.model.Role;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class JwtUtilsTest {

    private final JwtUtils jwtUtils = new JwtUtils();

    @Test
    void testGenerateAndValidateToken(){
        String email = "neo@gmail.com";
        Role role = Role.ADMIN;

       String token = jwtUtils.generateToken(email,role);
       assertThat(jwtUtils.validateToken(token)).isTrue();
       assertThat(jwtUtils.extractEmail(token)).isEqualTo(email);
    }


    @Test
    void testValidateToken(){
        String email = "neo@gmail.com";
        Role role = Role.ADMIN;
        String token = jwtUtils.generateToken(email,role);

        assertThat(jwtUtils.validateToken(token, "wrong@gmail.com")).isFalse();
    }


     @Test
     void testExtractAccDetailsFromToken(){
        String email = "neo@gmail.com";
        Role role = Role.ADMIN;
        String token = jwtUtils.generateToken(email,role);

       AccountResponse accountResponse = jwtUtils.extractAccDetailsFromToken(token);

       assertThat(accountResponse.getEmail()).isEqualTo(email);
       assertThat(accountResponse.getRole()).isEqualTo(role);
    }

    @Test
    void testInvalidToken() {
        String invalidToken = "invalid.token.value";
        assertThat(jwtUtils.validateToken(invalidToken));
    }

    @Test
    void testGetSecretKey() {
        SecretKey key = jwtUtils.getSecretKey();
        assertNotNull(key);
    }

}