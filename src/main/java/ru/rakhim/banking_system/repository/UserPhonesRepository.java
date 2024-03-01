package ru.rakhim.banking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rakhim.banking_system.model.UserPhones;

import java.util.Optional;

@Repository
public interface UserPhonesRepository extends JpaRepository<UserPhones, Integer> {
    Optional<UserPhones> findByPhone(String p);
}
