package com.example.account_service.config;

import com.example.account_service.jwt.JwtFilter;
import com.example.account_service.model.Role;
import com.example.account_service.service.AccountDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AccountDetailsService accountDetailsService;

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return
            httpSecurity
                    .csrf(csrf -> {
                        csrf.disable();
                    })
                    .httpBasic(http -> {
                    Customizer.withDefaults();
                })
                    .authorizeHttpRequests(request -> {
                          request.requestMatchers(HttpMethod.POST,"/api/v1/accounts/register").hasRole(Role.ADMIN.name())
                                  .requestMatchers(HttpMethod.POST , "/api/v1/accounts/login").permitAll()
                                  .requestMatchers("/api/v1/accounts/all").hasRole(Role.ADMIN.name())
                                  .anyRequest().authenticated();
                      })
                    .sessionManagement(session -> {
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                    })
                    .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class)
                    .build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(accountDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}