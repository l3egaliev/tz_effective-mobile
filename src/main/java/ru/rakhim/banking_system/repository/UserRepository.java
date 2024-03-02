package ru.rakhim.banking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rakhim.banking_system.model.User;
import ru.rakhim.banking_system.model.UserEmails;
import ru.rakhim.banking_system.model.UserPhones;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByFioStartingWithOrderByFio(String fio);
}
