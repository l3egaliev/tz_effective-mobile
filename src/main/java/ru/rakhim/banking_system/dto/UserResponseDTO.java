package ru.rakhim.banking_system.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class UserResponseDTO {
    private String fio;
    private Timestamp dateOfBirth;
    private String username;
    private Double sum;
    private List<String> emails;
    private List<String> phones;
}