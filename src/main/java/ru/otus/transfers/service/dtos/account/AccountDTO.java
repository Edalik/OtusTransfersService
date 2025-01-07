package ru.otus.transfers.service.dtos.account;

public record AccountDTO(
        String id,
        String accountNumber,
        String clientId,
        Integer balance,
        boolean isBlocked) {

}