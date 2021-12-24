package com.yakymets.creditsystem.services.implementations;

import com.yakymets.creditsystem.persistence.entities.Credit;
import com.yakymets.creditsystem.persistence.entities.CreditProvider;
import com.yakymets.creditsystem.persistence.repositories.CreditProviderRepository;
import com.yakymets.creditsystem.persistence.repositories.CreditRepository;
import com.yakymets.creditsystem.services.DTO.CreditDTO;
import com.yakymets.creditsystem.services.interfaces.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;
    private final CreditProviderRepository creditProviderRepository;

    @Autowired
    public CreditServiceImpl(CreditRepository creditRepository, CreditProviderRepository creditProviderRepository) {
        this.creditRepository = creditRepository;
        this.creditProviderRepository = creditProviderRepository;
    }

    @Override
    public List<CreditDTO> getAllCredits() {
        return creditRepository.findAll().stream().map(CreditDTO::new).collect(Collectors.toList());
    }

    @Override
    public CreditDTO getCreditById(Long id) {
        return new CreditDTO(creditRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    @Override
    public List<CreditDTO> getAllCreditsByCreditProviderEmail(String email) {
        return creditRepository.findAllByCreditProviderEmail(email)
                .stream().map(CreditDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditDTO> getFilteredCredits(BigDecimal maxSum,
                                              BigDecimal minSum,
                                              Integer monthsDuration,
                                              Double earningPercentage,
                                              Double earningPercentageAfterDeadline) {

        Stream<CreditDTO> creditDTOStream = creditRepository.findAll().stream().map(CreditDTO::new);
        if (maxSum != null)
            creditDTOStream = creditDTOStream.filter(credit -> credit.getMaxSum().compareTo(maxSum) <= 0);
        if (minSum != null)
            creditDTOStream = creditDTOStream.filter(credit -> credit.getMinSum().compareTo(minSum) >= 0);
        if (monthsDuration != null)
            creditDTOStream = creditDTOStream.filter(credit -> credit.getMonthsDuration() == monthsDuration);
        if (earningPercentage != null)
            creditDTOStream = creditDTOStream.filter(credit -> credit.getEarningPercentage() <= earningPercentage);
        if (earningPercentageAfterDeadline != null)
            creditDTOStream = creditDTOStream.filter(credit -> credit.getEarningPercentageAfterDeadline()
                    <= earningPercentageAfterDeadline);
        return creditDTOStream.collect(Collectors.toList());
    }

    @Override
    public CreditDTO updateCredit(Long id, CreditDTO creditDTO) {
        Credit credit = creditRepository.findById(id).orElseThrow(NoSuchElementException::new);
        credit.setName(creditDTO.getName());
        credit.setMinSum(creditDTO.getMinSum());
        credit.setMaxSum(creditDTO.getMaxSum());
        credit.setEarningPercentage(creditDTO.getEarningPercentage());
        credit.setEarningPercentageAfterDeadline(creditDTO.getEarningPercentageAfterDeadline());
        credit.setMonthsDuration(creditDTO.getMonthsDuration());
        creditRepository.save(credit);
        return new CreditDTO(credit);
    }

    @Override
    public Long deleteCredit(Long id) {
        creditRepository.deleteById(id);
        return id;
    }

    @Override
    public CreditDTO createCredit(CreditDTO creditDTO, String creditProviderEmail) {
        Credit credit = new Credit();
        CreditProvider creditProvider = creditProviderRepository.findCreditProviderByEmail(creditProviderEmail);
        credit.setName(creditDTO.getName());
        credit.setMinSum(creditDTO.getMinSum());
        credit.setMaxSum(creditDTO.getMaxSum());
        credit.setEarningPercentage(creditDTO.getEarningPercentage());
        credit.setEarningPercentageAfterDeadline(creditDTO.getEarningPercentageAfterDeadline());
        credit.setMonthsDuration(creditDTO.getMonthsDuration());
        creditProvider.addCredit(credit);
        creditProviderRepository.save(creditProvider);
        return new CreditDTO(credit);
    }
}
