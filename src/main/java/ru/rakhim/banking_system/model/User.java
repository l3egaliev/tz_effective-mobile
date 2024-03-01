package ru.rakhim.banking_system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", schema = "bank")
@Data
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "fio")
    private String fio;
    @Column(name = "date_of_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dateOfBirth;
    @OneToOne(mappedBy = "user")
    private Account bankAccount;
    @OneToMany(mappedBy = "user")
    private List<UserEmails> emails = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<UserPhones> phones = new ArrayList<>();
}
