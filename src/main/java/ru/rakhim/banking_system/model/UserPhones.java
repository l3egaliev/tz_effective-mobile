package ru.rakhim.banking_system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_phones", schema = "bank")
@Data
@NoArgsConstructor
public class UserPhones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id")
    private Integer id;
    @Column(name = "phone")
    private String phone;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User user;

    public UserPhones(String phone){
        this.phone = phone;
    }
}
