package com.yakymets.creditsystem.services.implementations;

import com.yakymets.creditsystem.persistence.entities.Credit;
import com.yakymets.creditsystem.persistence.entities.CreditProvider;
import com.yakymets.creditsystem.persistence.repositories.CreditProviderRepository;
import com.yakymets.creditsystem.persistence.repositories.CreditRepository;
import com.yakymets.creditsystem.services.DTO.CreditDTO;
import com.yakymets.creditsystem.services.interfaces.CreditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreditServiceImplTest {
    private CreditRepository creditRepository;
    private CreditProviderRepository creditProviderRepository;
    private CreditService creditService;

    @BeforeEach
    void setUp() {
        creditProviderRepository = Mockito.mock(CreditProviderRepository.class);
        creditRepository = Mockito.mock(CreditRepository.class);
        creditService = new CreditServiceImpl(creditRepository, creditProviderRepository);
    }

    @Test
    void getAllCredits_returnsRightCredits() {
        Credit first = new Credit();
        first.setMaxSum(new BigDecimal(1200));
        Credit second = new Credit();
        second.setMaxSum(new BigDecimal(1300));
        Credit third = new Credit();
        third.setMaxSum(new BigDecimal(1400));
        List<Credit> creditList = Arrays.asList(first, second, third);
        Mockito.when(creditRepository.findAll()).thenReturn(creditList);

        List<CreditDTO> allCreditsDTO = creditService.getAllCredits();

        assertEquals(creditList.size(), allCreditsDTO.size());
        assertEquals(creditList.get(0).getMaxSum(), allCreditsDTO.get(0).getMaxSum());
    }

    @Test
    void getCreditById_whenCreditFound_returnsCredit() {
        Credit mockedCredit = new Credit();
        Long id = 0L;
        mockedCredit.setMaxSum(new BigDecimal(1200));
        mockedCredit.setId(id);
        Mockito.when(creditRepository.findById(id)).thenReturn(Optional.of(mockedCredit));

        CreditDTO creditById = creditService.getCreditById(id);

        assertEquals(mockedCredit.getId(), creditById.getId());
        assertEquals(mockedCredit.getMaxSum(), creditById.getMaxSum());
    }

    @Test
    void getCreditById_whenCreditNotFount_throwsNoSuchElementException() {
        Long id = 0L;
        Mockito.when(creditRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> creditService.getCreditById(id));
    }

    @Test
    void getAllCreditsByCreditProviderEmail_returnsAllCreditsByCreditProviderEmail() {
        String email = "test@gmail.com";
        Credit first = new Credit();
        first.setMaxSum(new BigDecimal(1200));
        Credit second = new Credit();
        second.setMaxSum(new BigDecimal(1300));
        Credit third = new Credit();
        third.setMaxSum(new BigDecimal(1400));
        List<Credit> creditList = Arrays.asList(first, second, third);
        Mockito.when(creditRepository.findAllByCreditProviderEmail(email)).thenReturn(creditList);

        List<CreditDTO> allCreditsByCreditProviderEmail = creditService.getAllCreditsByCreditProviderEmail(email);

        assertEquals(creditList.size(), allCreditsByCreditProviderEmail.size());
        assertEquals(creditList.get(0).getMaxSum(), allCreditsByCreditProviderEmail.get(0).getMaxSum());
    }

    @Test
    void getFilteredCredits_returnsFilteredCredits() {
        BigDecimal maxSum = new BigDecimal(100000);
        BigDecimal minSum = new BigDecimal(10000);
        int monthsDuration = 12;
        double earningPercentage = 15d;
        double earningPercentageAfterDeadline = 25d;
        Credit first = new Credit();
        first.setMaxSum(new BigDecimal(100000));
        first.setMinSum(new BigDecimal(10000));
        first.setMonthsDuration(12);
        first.setEarningPercentage(15);
        first.setEarningPercentageAfterDeadline(25);

        Credit second = new Credit();
        second.setMaxSum(new BigDecimal(50000));
        second.setMinSum(new BigDecimal(20000));
        second.setMonthsDuration(22);
        second.setEarningPercentage(22);
        second.setEarningPercentageAfterDeadline(50);
        List<Credit> creditList = Arrays.asList(first, second);
        Mockito.when(creditRepository.findAll()).thenReturn(creditList);

        List<CreditDTO> filteredCredits = creditService.getFilteredCredits(maxSum, minSum, monthsDuration, earningPercentage, earningPercentageAfterDeadline);

        assertEquals(1, filteredCredits.size());
        assertEquals(first.getMaxSum(), filteredCredits.get(0).getMaxSum());
    }

    @Test
    void updateCredit_returnsUpdatedCredit() {
        Long id = 0L;

        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setMaxSum(new BigDecimal(100000));
        creditDTO.setMinSum(new BigDecimal(10000));
        creditDTO.setMonthsDuration(12);
        creditDTO.setEarningPercentage(15);
        creditDTO.setEarningPercentageAfterDeadline(25);
        creditDTO.setName("Super cool name");
        creditDTO.setId(id);

        Credit creditToReturn = new Credit();
        creditToReturn.setMaxSum(new BigDecimal(50000));
        creditToReturn.setMinSum(new BigDecimal(20000));
        creditToReturn.setMonthsDuration(22);
        creditToReturn.setEarningPercentage(22);
        creditToReturn.setEarningPercentageAfterDeadline(50);

        Mockito.when(creditRepository.findById(id)).thenReturn(Optional.of(creditToReturn));
        Mockito.when(creditRepository.save(ArgumentMatchers.any(Credit.class))).thenReturn(null);

        CreditDTO updatedCredit = creditService.updateCredit(id, creditDTO);
        assertEquals(creditDTO.getMaxSum(), updatedCredit.getMaxSum());
    }

    @Test
    void updateCredit_throwsNoSuchElementException() {
        Long id = 0L;
        Mockito.when(creditRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> creditService.updateCredit(id, null));
    }


    @Test
    void deleteCredit_returnsDeletedCreditId() {
        Long id = 0L;
        Mockito.doNothing().when(creditRepository).deleteById(id);

        Long deletedCreditId = creditService.deleteCredit(id);

        assertEquals(id, deletedCreditId);
    }

    @Test
    void createCredit_returnsCreatedDTO() {
        Long id = 0L;
        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setMaxSum(new BigDecimal(100000));
        creditDTO.setMinSum(new BigDecimal(10000));
        creditDTO.setMonthsDuration(12);
        creditDTO.setEarningPercentage(15);
        creditDTO.setEarningPercentageAfterDeadline(25);
        creditDTO.setName("Super cool name");
        creditDTO.setId(id);
        String creditProviderEmail = "privat24@gmail.com";
        CreditProvider creditProvider = new CreditProvider();
        creditProvider.setEmail(creditProviderEmail);
        Mockito.when(creditProviderRepository.findCreditProviderByEmail(creditProviderEmail)).thenReturn(creditProvider);
        CreditDTO createdCredit = creditService.createCredit(creditDTO, creditProviderEmail);

        assertEquals(creditDTO.getName(), createdCredit.getName());
        assertEquals(creditDTO.getMaxSum(), createdCredit.getMaxSum());
        assertEquals(creditDTO.getMinSum(), createdCredit.getMinSum());
    }
}