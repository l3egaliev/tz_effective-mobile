package ru.rakhim.banking_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rakhim.banking_system.dto.TransferResponseDTO;
import ru.rakhim.banking_system.model.Account;
import ru.rakhim.banking_system.repository.AccountRepository;

import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Transactional
@RequiredArgsConstructor
public class TransferService {
    private final AccountRepository accountRepository;
    private final Lock lock = new ReentrantLock();

    public TransferResponseDTO sendByUsername(String userToSend, String sender, double sum){
        lock.lock();
        try {
            Account senderUser = accountRepository.findByUsername(sender).orElseThrow(() -> new IllegalArgumentException("Sender not found"));
            Optional<Account> recipientOpt = accountRepository.findByUsername(userToSend);
            if (recipientOpt.isPresent()){
                Account recipient = recipientOpt.get();
                if (recipient.getUsername().equals(senderUser.getUsername())){
                    return TransferResponseDTO.builder()
                            .message("Вы не можете переводит сам себе!").build();
                }
                Double balance = senderUser.getBalance();
                if (sum > balance){
                    return TransferResponseDTO.builder()
                            .message("У вас не достаточно средств! Ваш баланс: "+balance).build();
                } else {
                    Double recipientBalance = recipient.getBalance();
                    recipient.setBalance(recipientBalance+sum);
                    recipient.setInitialDeposit(recipientBalance+sum);

                    senderUser.setBalance(balance-sum);
                    senderUser.setInitialDeposit(balance-sum);
                    accountRepository.save(senderUser);
                    accountRepository.save(recipient);
                    return TransferResponseDTO.builder()
                            .message("Успешный перевод. Остаток на балансе: "+balance).build();
                }
            }
            return TransferResponseDTO.builder()
                    .message("Пользователь не найден").build();
        } finally {
            lock.unlock();
        }
    }
}