package ru.otus.transfers.service.exceptions_handling.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationFieldErrorDto {

    private String field;

    private String message;

}