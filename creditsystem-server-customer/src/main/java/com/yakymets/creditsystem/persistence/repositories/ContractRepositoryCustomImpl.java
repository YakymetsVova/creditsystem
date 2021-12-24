package com.yakymets.creditsystem.persistence.repositories;

import com.yakymets.creditsystem.persistence.entities.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ContractRepositoryCustomImpl implements ContractRepositoryCustom {
    private final ContractRepository contractRepository;

    @Autowired
    public ContractRepositoryCustomImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    public List<Contract> findAllByCreditProviderEmail(String email) {
        return contractRepository.findAll()
                .stream()
                .filter(credit -> credit.getCredit().getCreditProvider().getEmail().equals(email))
                .collect(Collectors.toList());
    }
}
