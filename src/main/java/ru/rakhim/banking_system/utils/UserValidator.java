package ru.rakhim.banking_system.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.rakhim.banking_system.model.Account;
import ru.rakhim.banking_system.model.User;
import ru.rakhim.banking_system.service.UserService;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {
    private final UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userService.findByEmail(user.getEmail()).isPresent()){
            errors.rejectValue("email", "", "Email занят");
        }else if(userService.findByPhone(user.getPhone()).isPresent()){
            errors.rejectValue("phone", "","Такой номер уже используется");
        }
    }

    public void validate(Account a, Errors err){
        if(userService.findByUsername(a.getUsername()).isPresent()){
            err.rejectValue("username", "","Логин уже занят");
        }
    }
}
