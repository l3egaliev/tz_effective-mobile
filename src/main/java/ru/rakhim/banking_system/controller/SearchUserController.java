package ru.rakhim.banking_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
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
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "date", required = false) @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}",
                    message = "Формат даты - ГГГГ-ММ-ДД(1990-02-20)") String dateOfBirth,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "fio", required = false) String fio,
            @RequestParam(name = "email", required = false) String email){
        if (dateOfBirth != null && !dateOfBirth.isEmpty()){
            return findByDate(dateOfBirth);
        }else if(phone != null && !phone.isEmpty()){
            return findByPhone(phone);
        }else if(fio != null && !fio.isEmpty()){
            return findByFio(fio);
        }else if(email != null && !email.isEmpty()){
            return findByEmail(email);
        }else if(page != null && pageSize != null && page != 0 && pageSize != 0){
            return ResponseEntity.ok(Map.of("response", userService.findAllByPagination(page, pageSize)));
        }
        return ResponseEntity.badRequest().body(Map.of("response", "Invalid request!"));
    }

    private ResponseEntity<Map<String, Object>> findByDate(String date){
        return ResponseEntity.ok(Map.of("response", userService.findByDateOfBirth(date)));
    }

    private  ResponseEntity<Map<String, Object>> findByFio(String fio){
        return ResponseEntity.ok(Map.of("response", userService.findByFio(fio)));
    }

    private ResponseEntity<Map<String, Object>> findByPhone(String phone){
        if (userService.findUserByPhone(phone).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("response", userService.findUserByPhone(phone)));
        }
        return ResponseEntity.badRequest().body(Map.of("response", "Ничего не найдено"));
    }

    private ResponseEntity<Map<String, Object>> findByEmail(String e){
        if(userService.findByEmail(e).isPresent()){
            return ResponseEntity.ok(Map.of("response", userService.findByEmail(e)));
        }
        return ResponseEntity.badRequest().body(Map.of("response", "Ничего не найдено"));
    }
}
