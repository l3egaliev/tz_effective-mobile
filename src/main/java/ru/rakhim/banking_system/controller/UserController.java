package ru.rakhim.banking_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rakhim.banking_system.dto.ChangeEmailDTO;
import ru.rakhim.banking_system.dto.CreateAccountResponseDto;
import ru.rakhim.banking_system.model.Account;
import ru.rakhim.banking_system.model.JwtAuthentication;
import ru.rakhim.banking_system.model.User;
import ru.rakhim.banking_system.security.UserDetailsImpl;
import ru.rakhim.banking_system.service.UserService;
import ru.rakhim.banking_system.utils.ErrorSender;
import ru.rakhim.banking_system.utils.MapperObject;
import ru.rakhim.banking_system.utils.UserValidator;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;

    @PostMapping("/update/email")
    public ResponseEntity<Map<String, Object>> changeEmail(@RequestBody @Valid ChangeEmailDTO dto,
                                                           BindingResult br){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        if (dto.getOldEmail() == null || dto.getOldEmail().isEmpty()){
            userValidator.validate(new User(dto.getNewEmail()), br);
            if (br.hasErrors()){
                List<String> errors = ErrorSender.returnErrorsToClient(br);
                return ResponseEntity.badRequest().body(Map.of("message", errors));
            }
            userService.addEmail(dto.getNewEmail(), userDetails.getUsername());
            return ResponseEntity.ok(Map.of("message", "Новый email успешно добавлен"));
        }
        return ResponseEntity.ok(Map.of("message", ""));
    }
}
