package com.yakymets.creditsystem.services.interfaces;

import com.yakymets.creditsystem.services.DTO.ContractDTO;

import java.util.List;

public interface ContractService {
    ContractDTO addContract(ContractDTO contractDTO, String borrowerEmail);
    ContractDTO updateContract(ContractDTO contractDTO, Long contractId);
   // Long deleteContract(Long contractId);
    ContractDTO getContract(Long id);
    List<ContractDTO> getContractsByUserEmail(String userEmail, boolean isCreditProvider);
}
