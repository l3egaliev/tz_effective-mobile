package ru.rakhim.banking_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.rakhim.banking_system.model.User;
import ru.rakhim.banking_system.service.UserService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/find")
@RequiredArgsConstructor
@Tag(name="Контроллер для поиска пользователей")
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
        }else if(phone != null && !phone.isEmpty()){
            return findByPhone(phone);
        }
        return ResponseEntity.badRequest().body(Map.of());
    }

    private ResponseEntity<Map<String, Object>> findByDate(String date){
        return ResponseEntity.ok(Map.of("response", userService.findByDateOfBirth(date)));
    }

    private ResponseEntity<Map<String, Object>> findByPhone(String phone){
        if (userService.findUserByPhone(phone) != null) {
            return ResponseEntity.badRequest().body(Map.of("message", userService.findUserByPhone(phone)));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "Ничего не найдено"));
    }
}
