package ru.otus.transfers.service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.transfers.service.entities.Account;
import ru.otus.transfers.service.repositories.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Optional<Account> getAccountById(String id, String clientId) {
        return accountRepository.findByIdAndClientId(id, clientId);
    }

    public List<Account> getAllTransfers(String clientId) {
        return accountRepository.findAllByClientId(clientId);
    }

    public Optional<Account> findByAccountNumberAndClientId(String accountNumber, String clientId) {
        return accountRepository.findByAccountNumberAndClientId(accountNumber, clientId);
    }

    public boolean existsByAccountNumber(String accountNumber) {
        return accountRepository.existsByAccountNumber(accountNumber);
    }

    public boolean isBlocked(String accountNumber) {
        return accountRepository.isBlocked(accountNumber).orElse(true);
    }

    public void makeTransfer(String sourceAccountNumber, String targetAccountNumber, Integer amount) {
        accountRepository.changeBalance(sourceAccountNumber, -amount);
        accountRepository.changeBalance(targetAccountNumber, amount);
    }

}