package ru.otus.transfers.service.dtos.transfer;

public record ExecuteTransferDtoRq(
        String targetClientId,
        String sourceAccount,
        String targetAccount,
        String message,
        Integer amount) {

}