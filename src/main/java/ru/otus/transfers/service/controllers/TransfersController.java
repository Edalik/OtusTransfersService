package ru.otus.transfers.service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.transfers.service.dtos.transfer.ExecuteTransferDtoRq;
import ru.otus.transfers.service.dtos.transfer.TransferDto;
import ru.otus.transfers.service.dtos.transfer.TransfersPageDto;
import ru.otus.transfers.service.exceptions_handling.exceptions.ResourceNotFoundException;
import ru.otus.transfers.service.mappers.TransferMapper;
import ru.otus.transfers.service.services.TransfersService;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfers")
public class TransfersController {

    public static final String CLIENT_ID = "client-id";

    private final TransfersService transfersService;

    private final TransferMapper transferMapper;

    @GetMapping
    public TransfersPageDto getAllTransfers(@RequestHeader(name = CLIENT_ID) String clientId) {
        return new TransfersPageDto(
                transfersService
                        .getAllTransfers(clientId)
                        .stream()
                        .map(transferMapper::entityToDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public TransferDto getTransferById(@RequestHeader(name = CLIENT_ID) String clientId, @PathVariable String id) {
        return transferMapper.entityToDTO(transfersService.getTransferById(id, clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Перевод не найден")));
    }

    @PostMapping
    public void executeTransfer(
            @RequestHeader(name = CLIENT_ID) String clientId,
            @RequestBody ExecuteTransferDtoRq executeTransferDtoRq
    ) {
        transfersService.execute(clientId, executeTransferDtoRq);
    }

}