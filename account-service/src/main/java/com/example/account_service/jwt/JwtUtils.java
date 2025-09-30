package com.example.account_service.jwt;

import com.example.account_service.dto.AccountResponse;
import com.example.account_service.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    private final String secretKey = "nomatterhowsureitisitwilleventuallybesluggishnomatterhowsureitisitwilleventuallybesluggishyoutmindisyourvivaciouskingdown";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    public String generateToken(String email, Role role){
        return Jwts.builder()
                .subject(email)
                .claim("role", role.name())
                .issuer("/api/v1/accounts/login")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSecretKey())
                .compact();
    }

    public boolean validateToken(String token, String email) {
        String extractedEmail = extractEmail(token);
        return !isTokenExpired(token) && extractedEmail.equals(email);
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public String extractEmail(String token){
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token){
        return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
    }

    public AccountResponse extractAccDetailsFromToken(String token){
        Claims claims = getClaims(token);
        String roleStr = claims.get("role", String.class);
        Role role = Role.valueOf(roleStr);
        return new AccountResponse(claims.getSubject(),role);
    }

    private boolean isTokenExpired(String token) {
        Date today = Date.from(Instant.now());
        return getClaims(token).getExpiration().before(today);
    }

    public SecretKey getSecretKey(){
        byte[] decodedPasswords = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(decodedPasswords);
    }

}
