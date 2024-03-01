package ru.rakhim.banking_system.dto;

import lombok.Data;

@Data
public class AuthenticationDTO {
    private String username;
    private String password;
}
