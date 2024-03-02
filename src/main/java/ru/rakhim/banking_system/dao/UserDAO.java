package ru.rakhim.banking_system.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.rakhim.banking_system.model.Account;
import ru.rakhim.banking_system.model.User;
import ru.rakhim.banking_system.model.UserEmails;
import ru.rakhim.banking_system.model.UserPhones;

import java.sql.Timestamp;
import java.util.List;

@Component
public class UserDAO {
    private final JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findByDateOfBirth(Timestamp date){
        String sql = "SELECT * FROM bank.users where date_of_birth >= ?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{date}, new BeanPropertyRowMapper<>(User.class));
        users.forEach(u -> {
            u.setBankAccount(findAccountByUserId(u.getUserId()));
            u.setEmails(findEmailsByUserId(u.getUserId()));
            u.setPhones(findPhonesByUserId(u.getUserId()));
        });
        return users;
    }

    private Account findAccountByUserId(int id){
        String sql = "SELECT * FROM bank.bank_account where user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Account.class))
                .stream().findAny().orElse(null);
    }

    private List<UserEmails> findEmailsByUserId(int id){
        String sql = "SELECT * FROM bank.user_emails where user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(UserEmails.class));
    }

    private List<UserPhones> findPhonesByUserId(int id){
        String sql = "SELECT * FROM bank.user_phones where user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(UserPhones.class));
    }
}
