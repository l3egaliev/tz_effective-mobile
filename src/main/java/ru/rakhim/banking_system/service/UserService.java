package ru.rakhim.banking_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rakhim.banking_system.dao.UserDAO;
import ru.rakhim.banking_system.dto.UserResponseDTO;
import ru.rakhim.banking_system.model.Account;
import ru.rakhim.banking_system.model.User;
import ru.rakhim.banking_system.model.UserPhones;
import ru.rakhim.banking_system.repository.AccountRepository;
import ru.rakhim.banking_system.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final UserContactsService contactsService;
    private final UserDAO userDAO;

    public Optional<User> findByUsername(String username){
        Optional<Account> acc = accountRepository.findByUsername(username);
         if (acc.isPresent()){
             return Optional.of(acc.get().getUser());
         }else {
             return Optional.empty();
         }
    }

    public List<UserResponseDTO> findByDateOfBirth(String date){
        date += " 00:00:00";
       List<UserResponseDTO> res = new ArrayList<>();
       List<User> founded = userDAO.findByDateOfBirth(Timestamp.valueOf(date));
       founded.forEach(u -> {
           res.add(UserResponseDTO.builder()
                   .fio(u.getFio())
                   .dateOfBirth(u.getDateOfBirth())
                   .emails(List.of(u.getEmails().toString()))
                   .phones(List.of(u.getPhones().toString()))
                   .sum(u.getBankAccount().getSum())
                   .username(u.getBankAccount().getUsername()).build());
       });
       return res;
    }

    public UserResponseDTO findUserByPhone(String p){
        UserPhones userPhones = contactsService.findByPhone(p);
        if(userPhones != null){
            return UserResponseDTO.builder()
                    .username(userPhones.getUser().getBankAccount().getUsername())
                    .sum(userPhones.getUser().getBankAccount().getSum())
                    .dateOfBirth(userPhones.getUser().getDateOfBirth())
                    .fio(userPhones.getUser().getFio())
                    .phones(List.of(userPhones.toString()))
                    .emails(List.of(userPhones.getUser().getEmails().toString())).build();
        }
        return null;
    }

    public List<UserResponseDTO> findByFio(String f){
        List<UserResponseDTO> response = new ArrayList<>();
        List<User> users = userRepository.findAllByFioStartingWithIgnoreCase(f);
        users.forEach(u -> {
            response.add(UserResponseDTO.builder()
                    .fio(u.getFio())
                    .emails(List.of(u.getEmails().toString()))
                    .phones(List.of(u.getPhones().toString()))
                    .dateOfBirth(u.getDateOfBirth())
                    .sum(u.getBankAccount().getSum())
                    .username(u.getBankAccount().getUsername()).build());
        });
        return response;
    }

    @Transactional
    public void save(User user){
        Account account = user.getBankAccount();
        account.setUser(user);
        account.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(user);
        accountRepository.save(account);
        contactsService.saveContacts(user);
    }
}
