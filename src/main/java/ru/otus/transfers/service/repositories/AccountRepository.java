package ru.otus.transfers.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.transfers.service.entities.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByIdAndClientId(String id, String clientId);

    List<Account> findAllByClientId(String clientId);

    Optional<Account> findByAccountNumberAndClientId(String accountNumber, String clientId);

    boolean existsByAccountNumber(String accountNumber);

    @Query("select a.isBlocked from Account a where a.accountNumber = :accountNumber")
    Optional<Boolean> isBlocked(String accountNumber);

    @Modifying
    @Query("update Account a set a.balance = a.balance + :amount where a.accountNumber = :accountNumber")
    void changeBalance(String accountNumber, Integer amount);

}