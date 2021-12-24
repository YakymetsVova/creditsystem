package com.yakymets.creditsystem.web.controllers.creditprovider.contracts;

import com.yakymets.creditsystem.services.DTO.ContractDTO;
import com.yakymets.creditsystem.services.interfaces.ContractService;
import com.yakymets.creditsystem.services.interfaces.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-provider/contracts")
public class ContractControllerCreditProvider {
    private final ContractService contractService;
    private final JwtProvider jwtProvider;

    @Autowired
    public ContractControllerCreditProvider(ContractService contractService, JwtProvider jwtProvider) {
        this.contractService = contractService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ContractDTO> getAll(@RequestHeader("Authorization") String token) {
        String email = jwtProvider.getEmailFromToken(token.substring(7));
        return contractService.getContractsByUserEmail(email, true);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractDTO get(@PathVariable(value = "id") Long id) {
        return contractService.getContract(id);
    }
}
