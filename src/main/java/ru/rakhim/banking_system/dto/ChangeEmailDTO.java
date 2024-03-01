package ru.rakhim.banking_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeEmailDTO {
    public String oldEmail;
    @Email(message = "email должен быть валидным")
    @NotNull
    public String newEmail;
}
