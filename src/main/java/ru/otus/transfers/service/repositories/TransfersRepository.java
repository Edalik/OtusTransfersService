package ru.otus.transfers.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.transfers.service.entities.Transfer;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransfersRepository extends JpaRepository<Transfer, String> {

    @Query("select t from Transfer t where t.id = :id and t.clientId = :clientId or t.targetClientId = :clientId")
    Optional<Transfer> findByIdAndClientId(String id, String clientId);

    @Query("select t from Transfer t where t.clientId = :clientId or t.targetClientId = :clientId")
    List<Transfer> findAllByClientId(String clientId);

}