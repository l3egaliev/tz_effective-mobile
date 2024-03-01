package ru.rakhim.banking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rakhim.banking_system.model.UserEmails;

import java.util.Optional;

@Repository
public interface UserEmailsRepository extends JpaRepository<UserEmails, Integer> {
    Optional<UserEmails> findByEmail(String email);
}
