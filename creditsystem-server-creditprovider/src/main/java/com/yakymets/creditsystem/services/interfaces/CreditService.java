package com.yakymets.creditsystem.services.interfaces;

import com.yakymets.creditsystem.services.DTO.CreditDTO;

import java.math.BigDecimal;
import java.util.List;

public interface CreditService {
    List<CreditDTO> getAllCredits();
    CreditDTO getCreditById(Long id);
    List<CreditDTO> getAllCreditsByCreditProviderEmail(String email);
    List<CreditDTO> getFilteredCredits(BigDecimal maxSum, BigDecimal minSum, Integer monthsDuration, Double earningPercentage, Double earningPercentageAfterDeadline);
    CreditDTO updateCredit(Long id, CreditDTO creditDTO);

    Long deleteCredit(Long id);

    CreditDTO createCredit(CreditDTO creditDTO, String creditProviderEmail);
}
