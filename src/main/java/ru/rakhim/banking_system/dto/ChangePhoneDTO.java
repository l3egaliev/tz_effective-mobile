package ru.rakhim.banking_system.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePhoneDTO {
    private String oldPhone;
    @NotNull
    @Size(min = 10, max = 15, message = "Неверный формат номера")
    @Pattern(regexp = "[0-9]+", message = "Только цифры")
    private String newPhone;
}
