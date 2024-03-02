package ru.rakhim.banking_system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Table(name = "user_emails", schema = "bank")
@Entity
public class UserEmails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User user;

    public UserEmails(String email){
        this.email = email;
    }

    @Override
    public String toString() {
        return email;
    }
}
