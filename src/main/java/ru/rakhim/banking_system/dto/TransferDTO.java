package ru.rakhim.banking_system.dto;

import lombok.Data;

@Data
public class TransferDTO {
    private String usernameToSend;
    private Double sum;
}
