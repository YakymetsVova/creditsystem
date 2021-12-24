package com.yakymets.creditsystem.persistence.repositories;

import com.yakymets.creditsystem.persistence.entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
    List<Credit> findAllByCreditProviderEmail(String email);
}
