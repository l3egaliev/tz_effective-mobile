package ru.rakhim.banking_system.utils;

import ru.rakhim.banking_system.dto.CreateAccountResponseDto;
import ru.rakhim.banking_system.model.Account;
import ru.rakhim.banking_system.model.User;
import ru.rakhim.banking_system.model.UserEmails;
import ru.rakhim.banking_system.model.UserPhones;

import java.sql.Timestamp;

public class MapperObject {

    public static User mapToUser(CreateAccountResponseDto dto){
        User user = new User();
        user.setFio(dto.getFio());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.getEmails().add(new UserEmails(dto.getEmail()));
        user.getPhones().add(new UserPhones(dto.getPhone()));
        return user;
    }

    public static Account mapToAccount(CreateAccountResponseDto dto){
        Account account = new Account();
        account.setInitialDeposit(dto.getInitialDeposit());
        account.setBalance(dto.getInitialDeposit());
        account.setUsername(dto.getUsername());
        account.setPassword(dto.getPassword());
        return account;
    }
}
