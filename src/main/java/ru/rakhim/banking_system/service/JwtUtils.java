package ru.rakhim.banking_system.service;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.rakhim.banking_system.model.JwtAuthentication;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {
    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

//    private static Set<GrantedAuthority> getRoles(Claims claims) {
//        final List<String> roles = claims.get("roles", List.class);
//        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
//    }
}
