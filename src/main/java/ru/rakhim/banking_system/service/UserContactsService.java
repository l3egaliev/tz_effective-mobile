package ru.rakhim.banking_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rakhim.banking_system.model.User;
import ru.rakhim.banking_system.model.UserEmails;
import ru.rakhim.banking_system.model.UserPhones;
import ru.rakhim.banking_system.repository.UserEmailsRepository;
import ru.rakhim.banking_system.repository.UserPhonesRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserContactsService {
    private final UserEmailsRepository emailsRepository;
    private final UserPhonesRepository phonesRepository;

    @Transactional
    public Optional<UserEmails> findByEmail(List<UserEmails> emails){
        for (UserEmails e: emails){
            return emailsRepository.findByEmail(e.getEmail());
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<UserPhones> findByPhone(List<UserPhones> phones){
        for (UserPhones p : phones){
            return phonesRepository.findByPhone(p.getPhone());
        }
        return Optional.empty();
    }
}
