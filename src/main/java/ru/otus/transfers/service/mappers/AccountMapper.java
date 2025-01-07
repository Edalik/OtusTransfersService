package ru.otus.transfers.service.mappers;

import org.mapstruct.Mapper;
import ru.otus.transfers.service.dtos.account.AccountDTO;
import ru.otus.transfers.service.entities.Account;

@Mapper(imports = {Account.class, AccountDTO.class}, componentModel = "spring")
public interface AccountMapper {

    Account DTOtoEntity(AccountDTO accountDTO);

    AccountDTO entityToDTO(Account account);

}