package ru.rakhim.banking_system.utils;

import ru.rakhim.banking_system.dto.CreateAccountResponseDto;
import ru.rakhim.banking_system.model.Account;
import ru.rakhim.banking_system.model.User;

public class MapperObject {

    public static User mapToUser(CreateAccountResponseDto dto){
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        return user;
    }

    public static Account mapToAccount(CreateAccountResponseDto dto){
        Account account = new Account();
        account.setSum(dto.getSum());
        account.setUsername(dto.getUsername());
        account.setPassword(dto.getPassword());
        return account;
    }
}
