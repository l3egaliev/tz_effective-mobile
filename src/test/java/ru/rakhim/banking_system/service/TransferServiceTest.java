package ru.rakhim.banking_system.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.rakhim.banking_system.dto.TransferResponseDTO;
import ru.rakhim.banking_system.model.Account;
import ru.rakhim.banking_system.repository.AccountRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransferServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransferService transferService;

    public TransferServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testing method sendByUsername with successful response")
    public void testSendByUsername_SuccessfulTransfer() {
        String sender = "sender";
        String recipientUsername = "recipient";
        double balance = 100.0;
        double transferAmount = 50.0;

        Account senderAccount = new Account();
        senderAccount.setUsername(sender);
        senderAccount.setBalance(balance);

        Account recipientAccount = new Account();
        recipientAccount.setUsername(recipientUsername);
        recipientAccount.setBalance(50.0);

        when(accountRepository.findByUsername(sender)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByUsername(recipientUsername)).thenReturn(Optional.of(recipientAccount));
        TransferResponseDTO response = transferService.sendByUsername(recipientUsername, sender, transferAmount);

        assertEquals("Успешный перевод. Остаток на балансе: 50.0", response.getMessage());
    }
}
