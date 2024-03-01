package ru.rakhim.banking_system.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
//@AllArgsConstructor
public class JwtResponse {
    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;
    private final Boolean res;
    private final List<String> errors = new ArrayList<>();

    public JwtResponse(Boolean res, String accessToken, String refreshToken) {
        this.res = res;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public JwtResponse(Boolean res, String error) {
        this.res = res;
        this.errors.add(error);
    }
}
