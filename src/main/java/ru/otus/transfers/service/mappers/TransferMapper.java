package ru.otus.transfers.service.mappers;

import org.mapstruct.Mapper;
import ru.otus.transfers.service.dtos.transfer.TransferDto;
import ru.otus.transfers.service.entities.Transfer;

@Mapper(imports = {Transfer.class, TransferDto.class}, componentModel = "spring")
public interface TransferMapper {

    Transfer DTOtoEntity(TransferDto transferDto);

    TransferDto entityToDTO(Transfer transfer);

}