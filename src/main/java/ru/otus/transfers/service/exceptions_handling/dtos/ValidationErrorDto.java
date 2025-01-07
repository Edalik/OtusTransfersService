package ru.otus.transfers.service.exceptions_handling.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ValidationErrorDto {

    private String code;

    private String message;

    private List<ValidationFieldErrorDto> errors;

    private LocalDateTime dateTime;

    public ValidationErrorDto(String code, String message, List<ValidationFieldErrorDto> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
        this.dateTime = LocalDateTime.now();
    }

}