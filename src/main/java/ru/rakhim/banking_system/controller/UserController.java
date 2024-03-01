package ru.rakhim.banking_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rakhim.banking_system.dto.ChangeEmailDTO;
import ru.rakhim.banking_system.dto.ChangePhoneDTO;
import ru.rakhim.banking_system.dto.CreateAccountResponseDto;
import ru.rakhim.banking_system.model.*;
import ru.rakhim.banking_system.security.UserDetailsImpl;
import ru.rakhim.banking_system.service.UserContactsService;
import ru.rakhim.banking_system.service.UserService;
import ru.rakhim.banking_system.utils.ErrorSender;
import ru.rakhim.banking_system.utils.MapperObject;
import ru.rakhim.banking_system.utils.UserValidator;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final UserContactsService contactsService;

    @PostMapping("/update/email")
    public ResponseEntity<Map<String, Object>> changeEmail(@RequestBody @Valid ChangeEmailDTO dto,
                                                           BindingResult br){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        if (dto.getOldEmail() == null || dto.getOldEmail().isEmpty() &&
        dto.getNewEmail() != null && !dto.getNewEmail().isEmpty()){
            List<String> errors = checkNewEmail(dto, br);
            if(!errors.isEmpty()){
                return ResponseEntity.badRequest().body(Map.of("message", errors));
            }
            userService.addEmail(dto.getNewEmail(), userDetails.getUsername());
            return ResponseEntity.ok(Map.of("message", "Новый email успешно добавлен"));
        } else if(dto.getNewEmail() == null || dto.getNewEmail().isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("message", "newEmail не может быть пустым"));
        }else{
            User user = new User();
            user.getEmails().add(new UserEmails(dto.getOldEmail()));
            if (contactsService.findByEmail(user.getEmails()).isEmpty()){
                return ResponseEntity.badRequest().body(Map.of("message", dto.getOldEmail()+" не существует"));
            }
            List<String> errors = checkNewEmail(dto, br);
            if(!errors.isEmpty()){
                return ResponseEntity.badRequest().body(Map.of("message", errors));
            }
            userService.changeEmail(dto.getOldEmail(), dto.getNewEmail());
            return ResponseEntity.ok(Map.of("message", "email "+dto.getOldEmail()+" успешно изменен"));
        }
    }

    @PostMapping("/update/phone")
    public ResponseEntity<Map<String, Object>> changeEmail(@RequestBody @Valid ChangePhoneDTO dto,
                                                           BindingResult br){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        if (dto.getOldPhone() == null || dto.getOldPhone().isEmpty() &&
                dto.getNewPhone() != null && !dto.getNewPhone().isEmpty()){
            List<String> errors = checkNewPhone(dto, br);
            if(!errors.isEmpty()){
                return ResponseEntity.badRequest().body(Map.of("message", errors));
            }
            userService.addPhone(dto.getNewPhone(), userDetails.getUsername());
            return ResponseEntity.ok(Map.of("message", "Новый телефон успешно добавлен"));
        } else if(dto.getNewPhone() == null || dto.getNewPhone().isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("message", "newPhone не может быть пустым"));
        }else{
            User user = new User();
            user.getPhones().add(new UserPhones(dto.getOldPhone()));
            if (contactsService.findByPhone(user.getPhones()).isEmpty()){
                return ResponseEntity.badRequest().body(Map.of("message", dto.getOldPhone()+" не существует"));
            }
            List<String> errors = checkNewPhone(dto, br);
            if(!errors.isEmpty()){
                return ResponseEntity.badRequest().body(Map.of("message", errors));
            }
            userService.changePhone(dto.getOldPhone(), dto.getNewPhone());
            return ResponseEntity.ok(Map.of("message", "телефон "+dto.getOldPhone()+" успешно изменен"));
        }
    }

    private List<String> checkNewEmail(ChangeEmailDTO dto, BindingResult br){
        userValidator.validateNewEmail(dto.getNewEmail(), br);
        if (br.hasErrors()){
            return ErrorSender.returnErrorsToClient(br);
        }
        return Collections.emptyList();
    }

    private List<String> checkNewPhone(ChangePhoneDTO dto, BindingResult br){
        userValidator.validateNewPhone(dto.getNewPhone(), br);
        if (br.hasErrors()){
            return ErrorSender.returnErrorsToClient(br);
        }
        return Collections.emptyList();
    }
}
