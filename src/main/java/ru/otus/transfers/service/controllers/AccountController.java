package ru.otus.transfers.service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.transfers.service.dtos.account.AccountDTO;
import ru.otus.transfers.service.dtos.account.AccountPageDto;
import ru.otus.transfers.service.exceptions_handling.exceptions.ResourceNotFoundException;
import ru.otus.transfers.service.mappers.AccountMapper;
import ru.otus.transfers.service.services.AccountService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    public static final String CLIENT_ID = "client-id";

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    @GetMapping
    public AccountPageDto getAllTransfers(@RequestHeader(name = CLIENT_ID) String clientId) {
        List<AccountDTO> accountDTOs = accountService
                .getAllTransfers(clientId)
                .stream()
                .map(accountMapper::entityToDTO)
                .toList();

        return new AccountPageDto(accountDTOs);
    }

    @GetMapping("/{id}")
    public AccountDTO getAccountById(@RequestHeader(name = CLIENT_ID) String clientId, @PathVariable String id) {
        return accountMapper.entityToDTO(accountService.getAccountById(id, clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Аккаунт не найден")));
    }

}