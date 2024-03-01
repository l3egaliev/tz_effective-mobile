package ru.rakhim.banking_system.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateAccountResponseDto {
    @NotNull
    @Size(min = 3, max = 40, message = "Логин от 3 до 40 символов")
    private String username;
    @NotNull
    @Size(min = 8, max = 32, message = "Пароль от 8 до 32 символов")
    private String password;
    @NotNull(message = "На счета изначально должно быть какая-то сумма")
    @Min(1)
    private double sum;
    @NotNull
    @Size(min = 10, max = 15, message = "Неверный формат номера")
    @Pattern(regexp = "[0-9]+", message = "Только цифры")
    private String phone;
    @Email(message = "email должен быть валидным")
    @NotNull
    private String email;
}
