package ru.rakhim.banking_system.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.rakhim.banking_system.model.Account;
import ru.rakhim.banking_system.model.User;
import ru.rakhim.banking_system.model.UserEmails;
import ru.rakhim.banking_system.service.UserContactsService;
import ru.rakhim.banking_system.service.UserService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {
    private final UserContactsService contactsService;
    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (contactsService.findByEmail(user.getEmails()).isPresent()){
            errors.rejectValue("email", "", "Email занят");
        }else if(contactsService.findByPhone(user.getPhones()).isPresent()){
            errors.rejectValue("phone", "","Такой номер уже используется");
        }
    }

    public void validateNewEmail(String newEmail, Errors err){
        if (contactsService.findByEmail(List.of(new UserEmails(newEmail))).isPresent()){
            err.rejectValue("newEmail", "", newEmail+" уже существует");
        }

    }

    public void validate(Account a, Errors err){
        if(userService.findByUsername(a.getUsername()).isPresent()){
            err.rejectValue("username", "","Логин уже занят");
        }
    }
}
