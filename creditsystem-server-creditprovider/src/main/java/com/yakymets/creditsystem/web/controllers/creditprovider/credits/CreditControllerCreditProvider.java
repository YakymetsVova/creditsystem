package com.yakymets.creditsystem.web.controllers.creditprovider.credits;

import com.yakymets.creditsystem.services.DTO.CreditDTO;
import com.yakymets.creditsystem.services.interfaces.CreditService;
import com.yakymets.creditsystem.services.interfaces.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-provider/credits")
public class CreditControllerCreditProvider {
    private final CreditService creditService;
    private final JwtProvider jwtProvider;

    @Autowired
    public CreditControllerCreditProvider(CreditService creditService, JwtProvider jwtProvider) {
        this.creditService = creditService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CreditDTO> getAll(@RequestHeader("Authorization") String token) {
        System.out.println(token.substring(7));
        String email = jwtProvider.getEmailFromToken(token.substring(7));
        return creditService.getAllCreditsByCreditProviderEmail(email);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CreditDTO get(@PathVariable(value = "id") Long id) {
        return creditService.getCreditById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CreditDTO update(@PathVariable(value = "id") Long id, @RequestBody CreditDTO creditDTO) {
        return creditService.updateCredit(id, creditDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(creditService.deleteCredit(id), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreditDTO addCredit(@RequestHeader("Authorization") String token, @RequestBody CreditDTO creditDTO) {
        String email = jwtProvider.getEmailFromToken(token.substring(7));
        return creditService.createCredit(creditDTO, email);
    }
}
