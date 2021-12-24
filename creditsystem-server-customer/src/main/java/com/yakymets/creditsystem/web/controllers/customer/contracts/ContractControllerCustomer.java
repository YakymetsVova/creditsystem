package com.yakymets.creditsystem.web.controllers.customer.contracts;

import com.yakymets.creditsystem.services.DTO.ContractDTO;
import com.yakymets.creditsystem.services.interfaces.ContractService;
import com.yakymets.creditsystem.services.interfaces.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/customer/contracts")
public class ContractControllerCustomer {
    private final ContractService contractService;
    private final JwtProvider jwtProvider;
    Logger logger = LoggerFactory.getLogger(ContractControllerCustomer.class);

    @Autowired
    public ContractControllerCustomer(ContractService contractService, JwtProvider jwtProvider) {
        this.contractService = contractService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ContractDTO> getAll(@RequestHeader("Authorization") String token) {
        String email = jwtProvider.getEmailFromToken(token.substring(7));
        List<ContractDTO> contractsByUserEmail = contractService.getContractsByUserEmail(email, false);
        logger.info("Got contracts by user email");
        return contractsByUserEmail;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractDTO get(@PathVariable(value = "id") Long id) {
        ContractDTO contract = contractService.getContract(id);
        logger.info("Got contract");
        return contract;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContractDTO create(@RequestHeader("Authorization") String token, @RequestBody ContractDTO contractDTO) {
        String email = jwtProvider.getEmailFromToken(token.substring(7));
        ContractDTO addContract = contractService.addContract(contractDTO, email);
        logger.info("Created contract");
        return addContract;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractDTO update(@PathVariable(value = "id") Long id, @RequestBody ContractDTO contractDTO) {
        ContractDTO updateContract = contractService.updateContract(contractDTO, id);
        logger.info("updated contract");
        return updateContract;
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchElementFoundException(
            NoSuchElementException exception
    ) {
        logger.error("No such contract");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

}
