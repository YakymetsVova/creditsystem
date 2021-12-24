package com.yakymets.creditsystem.services.implementations;

import com.yakymets.creditsystem.persistence.entities.Contract;
import com.yakymets.creditsystem.persistence.entities.Credit;
import com.yakymets.creditsystem.persistence.entities.Customer;
import com.yakymets.creditsystem.persistence.repositories.ContractRepository;
import com.yakymets.creditsystem.persistence.repositories.ContractRepositoryCustom;
import com.yakymets.creditsystem.persistence.repositories.CreditRepository;
import com.yakymets.creditsystem.persistence.repositories.CustomerRepository;
import com.yakymets.creditsystem.services.DTO.ContractDTO;
import com.yakymets.creditsystem.services.interfaces.DateOperationsService;
import com.yakymets.creditsystem.services.interfaces.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {
    private final CustomerRepository customerRepository;
    private final CreditRepository creditRepository;
    private final ContractRepository contractRepository;
    private final DateOperationsService dateOperationsService;
    private final ContractRepositoryCustom contractRepositoryCustom;

    @Autowired
    public ContractServiceImpl(CustomerRepository customerRepository, CreditRepository creditRepository, ContractRepository contractRepository, DateOperationsService dateOperationsService, ContractRepositoryCustom contractRepositoryCustom) {
        this.customerRepository = customerRepository;
        this.creditRepository = creditRepository;
        this.contractRepository = contractRepository;
        this.dateOperationsService = dateOperationsService;
        this.contractRepositoryCustom = contractRepositoryCustom;
    }

    @Override
    public ContractDTO addContract(ContractDTO contractDTO, String borrowerEmail) {
        Customer customer = customerRepository.findCustomerByEmail(borrowerEmail);
        Credit credit = creditRepository.getById(contractDTO.getCredit().getId());
        Contract contract = new Contract();
        contract.setCredit(credit);
        contract.setCustomer(customer);
        contract.setPayedSum(new BigDecimal(0));
        contract.setDeadline(dateOperationsService.addMonths(new Date(), credit.getMonthsDuration()));
        contract.setOwedSum(contractDTO.getOwedSum());
        contractRepository.save(contract);
        return new ContractDTO(contract);
    }

    @Override
    public ContractDTO updateContract(ContractDTO contractDTO, Long contractId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(NoSuchElementException::new);
        contract.setOwedSum(contractDTO.getOwedSum());
        contract.setPayedSum(contractDTO.getPayedSum());
        contractRepository.save(contract);
        return new ContractDTO(contract);
    }


    @Override
    public ContractDTO getContract(Long id) {
        return new ContractDTO(contractRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    @Override
    public List<ContractDTO> getContractsByUserEmail(String userEmail, boolean isCreditProvider) {
        if (isCreditProvider) {
            return contractRepositoryCustom.findAllByCreditProviderEmail(userEmail)
                    .stream().map(ContractDTO::new).collect(Collectors.toList());
        }
        return contractRepository.findAllByCustomerEmail(userEmail).stream().map(ContractDTO::new).collect(Collectors.toList());
    }



}
