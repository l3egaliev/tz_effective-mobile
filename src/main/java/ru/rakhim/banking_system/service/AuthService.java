package ru.rakhim.banking_system.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.rakhim.banking_system.dto.AuthenticationDTO;
import ru.rakhim.banking_system.model.JwtResponse;
import ru.rakhim.banking_system.model.User;
import ru.rakhim.banking_system.security.JwtProvider;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    public void register(User user){
        user.getBankAccount().setPassword(encoder.encode(user.getBankAccount().getPassword()));
        userService.save(user);
    }

    public JwtResponse login(@NonNull AuthenticationDTO authRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new JwtResponse(false, "Неправильный пароль или логин!");
        }
        final UserDetails user = (UserDetails) authentication.getPrincipal();
        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);
        refreshStorage.put(user.getUsername(), refreshToken);
        return new JwtResponse(true, accessToken, refreshToken);
    }

    @GetMapping("logout")
    public Map<Object, Object> logout() {
        refreshStorage.clear();
        return Map.of("res", true);
    }
}