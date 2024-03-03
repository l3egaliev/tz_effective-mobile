package ru.rakhim.banking_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rakhim.banking_system.dto.TransferResponseDTO;
import ru.rakhim.banking_system.model.Account;
import ru.rakhim.banking_system.repository.AccountRepository;
import ru.rakhim.banking_system.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TransferService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public TransferResponseDTO sendByUsername(String userToSend, String sender, double sum){
        Account senderUser = accountRepository.findByUsername(sender).get();
        Optional<Account> recipient = accountRepository.findByUsername(userToSend);
        if (recipient.isPresent()){
            if (recipient.get().getUsername().equals(senderUser.getUsername())){
                return TransferResponseDTO.builder()
                        .status("Вы не можете переводить сам себе!").build();
            }
            Double balance = senderUser.getBalance();
            if (sum > balance){
                return TransferResponseDTO.builder()
                        .status("У вас не достаточно денег для отправки! - "+balance).build();
            }else {
                Double recipientBalance = recipient.get().getBalance();
                recipient.get().setBalance(recipientBalance+sum);
                recipient.get().setInitialDeposit(recipientBalance+sum);

                senderUser.setBalance(balance-sum);
                senderUser.setInitialDeposit(balance-sum);
                accountRepository.save(senderUser);
                accountRepository.save(recipient.get());
                return TransferResponseDTO.builder()
                        .status("Перевод успешен. Остаток в вашем балансе - "+balance).build();
            }
        }
        return TransferResponseDTO.builder()
                .status("Пользователь не найден").build();
    }
}
