package ru.otus.transfers.service.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.transfers.service.dtos.transfer.ExecuteTransferDtoRq;
import ru.otus.transfers.service.entities.Account;
import ru.otus.transfers.service.entities.Transfer;
import ru.otus.transfers.service.exceptions_handling.dtos.ValidationFieldError;
import ru.otus.transfers.service.exceptions_handling.exceptions.BusinessLogicException;
import ru.otus.transfers.service.exceptions_handling.exceptions.ValidationException;
import ru.otus.transfers.service.repositories.TransfersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransfersService {

    private final TransfersRepository transfersRepository;

    private final AccountService accountService;

    public Optional<Transfer> getTransferById(String id, String clientId) {
        return transfersRepository.findByIdAndClientId(id, clientId);
    }

    public List<Transfer> getAllTransfers(String clientId) {
        return transfersRepository.findAllByClientId(clientId);
    }


    @Transactional
    public void execute(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        validateExecuteTransferDtoRq(executeTransferDtoRq);
        executeTransfer(clientId, executeTransferDtoRq);
    }

    private void validateExecuteTransferDtoRq(ExecuteTransferDtoRq executeTransferDtoRq) {
        List<ValidationFieldError> errors = new ArrayList<>();
        if (executeTransferDtoRq.sourceAccount().length() != 12) {
            errors.add(new ValidationFieldError("sourceAccount", "Длина поля счет отправителя должна составлять 12 символов"));
        }
        if (executeTransferDtoRq.targetAccount().length() != 12) {
            errors.add(new ValidationFieldError("targetAccount", "Длина поля счет получателя должна составлять 12 символов"));
        }
        if (executeTransferDtoRq.amount() <= 0) {
            errors.add(new ValidationFieldError("amount", "Сумма перевода должна быть больше 0"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("EXECUTE_TRANSFER_VALIDATION_ERROR", "Проблемы заполнения полей перевода", errors);
        }
    }

    @Transactional
    protected void executeTransfer(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        String sourceAccountNumber = executeTransferDtoRq.sourceAccount();
        String targetAccountNumber = executeTransferDtoRq.targetAccount();
        Integer transferAmount = executeTransferDtoRq.amount();

        Account sourceAccount = accountService.findByAccountNumberAndClientId(sourceAccountNumber, clientId)
                .orElseThrow(() -> new BusinessLogicException("Счет отправителя не найден", ""));

        if (sourceAccount.isBlocked()) {
            throw new BusinessLogicException("Счет отправителя заблокирован", "");
        }

        if (!accountService.existsByAccountNumber(targetAccountNumber)) {
            throw new BusinessLogicException("Счет получателя не найден", "");
        }
        if (accountService.isBlocked(targetAccountNumber)) {
            throw new BusinessLogicException("Счет получателя заблокирован", "");
        }

        if (sourceAccount.getBalance() - transferAmount < 0) {
            throw new BusinessLogicException("Недостаточно средств", "");
        }

        Transfer transfer = Transfer.builder()
                .clientId(clientId)
                .targetClientId(executeTransferDtoRq.targetClientId())
                .sourceAccount(sourceAccountNumber)
                .targetAccount(targetAccountNumber)
                .message(executeTransferDtoRq.message())
                .amount(transferAmount)
                .build();

        transfersRepository.save(transfer);

        accountService.makeTransfer(sourceAccountNumber, targetAccountNumber, transferAmount);
    }

}