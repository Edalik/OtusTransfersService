package ru.otus.transfers.service.exceptions_handling.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDto {

    private String code;

    private String message;

    private LocalDateTime dateTime;

    public ErrorDto(String code, String message) {
        this.code = code;
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }

}