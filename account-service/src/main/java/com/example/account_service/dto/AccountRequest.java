package com.example.account_service.dto;

import com.example.account_service.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {

    private String email;
    private String password;
    private Role role;
}
