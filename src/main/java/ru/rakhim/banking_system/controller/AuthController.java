package ru.rakhim.banking_system.controller;

import io.swagger.v3.oas.annotations.headers.Header;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rakhim.banking_system.dto.AuthenticationDTO;
import ru.rakhim.banking_system.dto.CreateAccountResponseDto;
import ru.rakhim.banking_system.model.Account;
import ru.rakhim.banking_system.model.JwtResponse;
import ru.rakhim.banking_system.model.RefreshJwtRequest;
import ru.rakhim.banking_system.model.User;
import ru.rakhim.banking_system.service.AuthService;
import ru.rakhim.banking_system.utils.ErrorSender;
import ru.rakhim.banking_system.utils.MapperObject;
import ru.rakhim.banking_system.utils.UserValidator;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserValidator validator;
    private final AuthService service;

    public AuthController(UserValidator validator, AuthService service) {
        this.validator = validator;
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody @Valid CreateAccountResponseDto dto,
                                                                 BindingResult b){
        Account account = MapperObject.mapToAccount(dto);
        User user = MapperObject.mapToUser(dto);
        user.setBankAccount(account);
        validator.validate(user, b);
        validator.validate(account, b);
        if (b.hasErrors()){
            List<String> errors = ErrorSender.returnErrorsToClient(b);
            return ResponseEntity.badRequest().body(Map.of("message", errors));
        }
        service.register(user);
        return ResponseEntity.ok(Map.of("message", "Банковский аккаунт успешно создан"));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthenticationDTO authRequest){
        final JwtResponse token = service.login(authRequest);
        return ResponseEntity.ok(token);
    }
}
