package ru.rakhim.banking_system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.*;
import jakarta.websocket.OnMessage;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Data
public class CreateAccountResponseDto {
    @NotNull
    @NotEmpty
    @Size(min = 4, max = 100, message = "ФИО от 4 до 100 символов")
    private String fio;
    @NotNull(message = "Формат даты - ГГГГ-мм-дд")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp dateOfBirth;
    @NotNull
    @Size(min = 3, max = 40, message = "Логин от 3 до 40 символов")
    private String username;
    @NotNull
    @Size(min = 8, max = 32, message = "Пароль от 8 до 32 символов")
    private String password;
    @NotNull(message = "На счета изначально должно быть какая-то сумма")
    @Min(1)
    private double initialDeposit;
    @NotNull
    @Size(min = 10, max = 15, message = "Неверный формат номера")
    @Pattern(regexp = "[0-9]+", message = "Только цифры")
    private String phone;
    @Email(message = "email должен быть валидным")
    @NotNull
    private String email;
}
