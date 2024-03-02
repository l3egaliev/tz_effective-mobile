package ru.rakhim.banking_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.rakhim.banking_system.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/find")
@RequiredArgsConstructor
public class SearchUserController {
    private final UserService userService;

    @GetMapping()
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
