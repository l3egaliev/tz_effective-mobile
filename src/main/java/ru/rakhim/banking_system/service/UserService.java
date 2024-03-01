package ru.rakhim.banking_system.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rakhim.banking_system.model.Account;
import ru.rakhim.banking_system.model.User;
import ru.rakhim.banking_system.model.UserEmails;
import ru.rakhim.banking_system.model.UserPhones;
import ru.rakhim.banking_system.repository.AccountRepository;
import ru.rakhim.banking_system.repository.UserEmailsRepository;
import ru.rakhim.banking_system.repository.UserPhonesRepository;
import ru.rakhim.banking_system.repository.UserRepository;
import ru.rakhim.banking_system.security.JwtProvider;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final UserEmailsRepository emailsRepository;
    private final UserPhonesRepository phonesRepository;

    public Optional<User> findByUsername(String username){
        Optional<Account> acc = accountRepository.findByUsername(username);
         if (acc.isPresent()){
             return Optional.of(acc.get().getUser());
         }else {
             return Optional.empty();
         }
    }

    @Transactional
    public void save(User user){
        Account account = user.getBankAccount();
        account.setUser(user);
        account.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(user);
        accountRepository.save(account);
    }
}
