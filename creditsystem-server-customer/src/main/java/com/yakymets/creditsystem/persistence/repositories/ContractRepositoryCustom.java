package com.yakymets.creditsystem.persistence.repositories;

import com.yakymets.creditsystem.persistence.entities.Contract;

import java.util.List;

public interface ContractRepositoryCustom {
    List<Contract> findAllByCreditProviderEmail(String email);
}
