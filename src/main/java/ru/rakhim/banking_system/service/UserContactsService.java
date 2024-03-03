package ru.rakhim.banking_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rakhim.banking_system.model.Account;
import ru.rakhim.banking_system.model.User;
import ru.rakhim.banking_system.model.UserEmails;
import ru.rakhim.banking_system.model.UserPhones;
import ru.rakhim.banking_system.repository.AccountRepository;
import ru.rakhim.banking_system.repository.UserEmailsRepository;
import ru.rakhim.banking_system.repository.UserPhonesRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserContactsService {
    private final UserEmailsRepository emailsRepository;
    private final UserPhonesRepository phonesRepository;
    private final AccountRepository accountRepository;

    public Optional<UserEmails> findByEmail(List<UserEmails> emails){
        return emails.stream()
                .map(UserEmails::getEmail)
                .findFirst()
                .flatMap(emailsRepository::findByEmail);
    }

    public UserEmails findByEmail(String email){
        return emailsRepository.findByEmail(email).orElse(null);
    }

    public Optional<UserPhones> findByPhone(List<UserPhones> phones){
        return phones.stream()
                .map(UserPhones::getPhone)
                .findFirst()
                .flatMap(phonesRepository::findByPhone);
    }

    public UserPhones findByPhone(String phone){
        return phonesRepository.findByPhone(phone).orElse(null);
    }

    @Transactional
    public void addEmail(String newEmail, String username){
        Account account = accountRepository.findByUsername(username).get();
        account.getUser().getEmails().add(new UserEmails(newEmail));
        UserEmails email = new UserEmails(newEmail);
        email.setUser(account.getUser());
        email.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        accountRepository.save(account);
        emailsRepository.save(email);
    }

    @Transactional
    public void changeEmail(String old, String newEmail){
        UserEmails email = emailsRepository.findByEmail(old).get();
        email.setEmail(newEmail);
        email.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        emailsRepository.save(email);
    }

    @Transactional
    public void addPhone(String newPhone, String username){
        Account account = accountRepository.findByUsername(username).get();
        account.getUser().getPhones().add(new UserPhones(newPhone));
        UserPhones phone = new UserPhones(newPhone);
        phone.setUser(account.getUser());
        phone.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        accountRepository.save(account);
        phonesRepository.save(phone);
    }

    @Transactional
    public void changePhone(String oldPhone, String newPhone) {
        UserPhones phones = phonesRepository.findByPhone(oldPhone).get();
        phones.setPhone(newPhone);
        phones.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        phonesRepository.save(phones);
    }

    public boolean isLastEmail(){
        List<UserEmails> emails = emailsRepository.findAll();
        return emails.size()==1;
    }

    @Transactional
    public void deleteEmail(String email){
        UserEmails emailToDelete = emailsRepository.findByEmail(email).get();
        emailsRepository.delete(emailToDelete);
    }

    public boolean isLastPhone() {
        List<UserPhones> phones = phonesRepository.findAll();
        return phones.size()==1;
    }

    @Transactional
    public void deletePhone(String phone){
        UserPhones phoneToDelete = phonesRepository.findByPhone(phone).get();
        phonesRepository.delete(phoneToDelete);
    }

    @Transactional
    public void saveContacts(User user){
        UserPhones phone = user.getPhones().get(0);
        UserEmails email = user.getEmails().get(0);

        phone.setUser(user);
        phone.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        email.setUser(user);
        email.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        emailsRepository.save(email);
        phonesRepository.save(phone);
    }
}
