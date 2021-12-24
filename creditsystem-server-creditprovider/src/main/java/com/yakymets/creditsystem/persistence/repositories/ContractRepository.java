package com.yakymets.creditsystem.persistence.repositories;

import com.yakymets.creditsystem.persistence.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findAllByCustomerEmail(String email);
}
