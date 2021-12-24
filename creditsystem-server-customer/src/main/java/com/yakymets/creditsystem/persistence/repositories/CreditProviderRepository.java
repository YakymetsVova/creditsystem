package com.yakymets.creditsystem.persistence.repositories;

import com.yakymets.creditsystem.persistence.entities.CreditProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditProviderRepository extends JpaRepository<CreditProvider, Long> {
    CreditProvider findCreditProviderByEmail(String email);
}
