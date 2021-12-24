package com.yakymets.creditsystem.services.implementations;

import com.yakymets.creditsystem.persistence.entities.Contract;
import com.yakymets.creditsystem.persistence.entities.Credit;
import com.yakymets.creditsystem.persistence.entities.CreditProvider;
import com.yakymets.creditsystem.persistence.entities.Customer;
import com.yakymets.creditsystem.persistence.repositories.ContractRepository;
import com.yakymets.creditsystem.persistence.repositories.ContractRepositoryCustom;
import com.yakymets.creditsystem.persistence.repositories.CreditRepository;
import com.yakymets.creditsystem.persistence.repositories.CustomerRepository;
import com.yakymets.creditsystem.services.DTO.ContractDTO;
import com.yakymets.creditsystem.services.DTO.CreditDTO;
import com.yakymets.creditsystem.services.interfaces.ContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class ContractServiceImplTest {

    private CustomerRepository customerRepository;
    private CreditRepository creditRepository;
    private ContractRepository contractRepository;
    private ContractRepositoryCustom contractRepositoryCustom;
    private ContractService contractService;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        creditRepository = Mockito.mock(CreditRepository.class);
        contractRepository = Mockito.mock(ContractRepository.class);
        contractRepositoryCustom = Mockito.mock(ContractRepositoryCustom.class);
        contractService = new ContractServiceImpl(customerRepository, creditRepository, contractRepository, new DateOperationsServiceImpl(), contractRepositoryCustom);
    }

    @Test
    void addContract_returnsAddedContract() {
        String borrowerEmail = "mail@gmail.com";
        Customer customer = new Customer();
        customer.setId(0L);
        customer.setEmail(borrowerEmail);
        Credit credit = new Credit();
        Long id = 0L;
        credit.setId(id);
        credit.setName("creditCoolName");
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setCredit(new CreditDTO(credit));
        contractDTO.setOwedSum(new BigDecimal(1000));
        Mockito.when(customerRepository.findCustomerByEmail(borrowerEmail)).thenReturn(customer);
        Mockito.when(creditRepository.getById(contractDTO.getCredit().getId())).thenReturn(credit);
        Mockito.when(contractRepository.save(any(Contract.class))).thenReturn(any(Contract.class));

        ContractDTO result = contractService.addContract(contractDTO, borrowerEmail);

        assertNotNull(result);
        assertEquals(customer.getEmail(), result.getCustomer().getEmail());
        assertEquals(credit.getName(), result.getCredit().getName());
    }

    @Test
    void updateContract_whenContractNotExists_throwsNoSuchElementException() {
        Mockito.when(contractRepository.findById(0L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> contractService.updateContract(new ContractDTO(), 0L));
    }

    @Test
    void getContract_whenContractExists_returnsRightContract() {
        Long id = 0L;
        Contract contract = new Contract();
        contract.setOwedSum(new BigDecimal(1000));
        contract.setPayedSum(new BigDecimal(500));
        contract.setId(id);

        String borrowerEmail = "mail@gmail.com";
        Customer customer = new Customer();
        customer.setId(0L);
        customer.setEmail(borrowerEmail);

        contract.setCredit(new Credit());
        contract.setCustomer(customer);

        Mockito.when(contractRepository.findById(id)).thenReturn(Optional.of(contract));

        ContractDTO resultContract = contractService.getContract(id);
        assertEquals(contract.getOwedSum(), resultContract.getOwedSum());
        assertEquals(contract.getPayedSum(), resultContract.getPayedSum());
    }

    @Test
    void getContract_whenContractNotExists_throwsNoSuchElementException() {
        Mockito.when(contractRepository.findById(0L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> contractService.getContract(0L));
    }


    @Test
    void getContractsByUserEmail_whenCustomer_returnsCustomerContracts() {
        String userEmail = "customer@gmail.com";
        Contract firstContract = new Contract();
        Contract secondContract = new Contract();
        Customer customer = new Customer();
        customer.setEmail(userEmail);
        customer.setId(0L);

        List<Contract> contracts = Arrays.asList(firstContract, secondContract);
        contracts.forEach(contract -> contract.setCustomer(customer));
        contracts.forEach(contract -> contract.setCredit(new Credit()));

        Mockito.when(contractRepository.findAllByCustomerEmail(userEmail)).thenReturn(contracts);

        List<ContractDTO> contractsByUserEmail = contractService.getContractsByUserEmail(userEmail, false);

        assertEquals(contracts.size(), contractsByUserEmail.size());
        assertEquals(contracts.get(0).getCustomer().getEmail(), contractsByUserEmail.get(0).getCustomer().getEmail());
    }

    @Test
    void getContractsByUserEmail_whenCreditProvider_returnsCreditProviderContracts() {
        String userEmail = "creditprovider@gmail.com";

        Contract firstContract = new Contract();
        Contract secondContract = new Contract();
        CreditProvider creditProvider = new CreditProvider();
        creditProvider.setEmail(userEmail);
        List<Contract> contracts = Arrays.asList(firstContract, secondContract);
        firstContract.setCredit(new Credit());
        secondContract.setCredit(new Credit());

        Customer customer = new Customer();
        customer.setEmail(userEmail);
        customer.setId(0L);
        contracts.forEach(contract -> contract.setCustomer(customer));

        contracts.forEach(contract -> contract.getCredit().setCreditProvider(creditProvider));
        Mockito.when(contractRepositoryCustom.findAllByCreditProviderEmail(userEmail)).thenReturn(contracts);

        List<ContractDTO> contractsByUserEmail = contractService.getContractsByUserEmail(userEmail, true);

        assertEquals(contracts.size(), contractsByUserEmail.size());
    }
}