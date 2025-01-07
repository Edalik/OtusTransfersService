package ru.otus.transfers.service.dtos.transfer;

public record TransferDto(
        String id,
        String clientId,
        String targetClientId,
        String sourceAccount,
        String targetAccount,
        String message,
        Integer amount) {

}