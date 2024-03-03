package ru.rakhim.banking_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rakhim.banking_system.dto.TransferDTO;
import ru.rakhim.banking_system.security.UserDetailsImpl;
import ru.rakhim.banking_system.service.TransferService;

import java.util.Map;

@RestController
@RequestMapping("/transfer")
@Tag(name = "Перевод денег")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PutMapping
    @Operation(description = "Можно переводить деньги через username/телефон/email",
    security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Map<String, Object>> sendMoney(@RequestBody TransferDTO dto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
        if (dto.getUsernameToSend() != null && !dto.getUsernameToSend().isEmpty()
        && dto.getSum() != 0){
            return ResponseEntity.ok(Map.of("response", transferService.sendByUsername(dto.getUsernameToSend(),
                    user.getUsername(), dto.getSum())));
        }
        return ResponseEntity.badRequest().body(Map.of("response", "Invalid request"));
    }
}