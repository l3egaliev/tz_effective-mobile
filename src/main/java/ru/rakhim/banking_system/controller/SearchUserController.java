package ru.rakhim.banking_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.headers.HeadersSecurityMarker;
import org.springframework.web.bind.annotation.*;
import ru.rakhim.banking_system.filter.JwtFilter;
import ru.rakhim.banking_system.security.JwtProvider;
import ru.rakhim.banking_system.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/find")
@RequiredArgsConstructor
public class SearchUserController {
    private final UserService userService;

    @GetMapping()
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Map<String, Object>> findUser(
            @RequestParam(name = "date", required = false) String dateOfBirth,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "fio", required = false) String fio,
            @RequestParam(name = "email", required = false) String email){
        if (dateOfBirth != null && !dateOfBirth.isEmpty()){
            return findByDate(dateOfBirth);
        }
        return ResponseEntity.badRequest().body(Map.of());
    }

    private ResponseEntity<Map<String, Object>> findByDate(String date){
        return ResponseEntity.ok(Map.of("response", userService.findByDateOfBirth(date)));
    }
}
