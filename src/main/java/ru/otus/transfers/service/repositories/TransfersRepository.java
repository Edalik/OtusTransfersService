package ru.otus.transfers.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.transfers.service.entities.Transfer;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransfersRepository extends JpaRepository<Transfer, String> {

    Optional<Transfer> findByIdAndClientId(String id, String clientId);

    List<Transfer> findAllByClientId(String clientId);

}