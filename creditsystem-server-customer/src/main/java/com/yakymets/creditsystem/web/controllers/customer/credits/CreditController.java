package com.yakymets.creditsystem.web.controllers.customer.credits;

import com.yakymets.creditsystem.services.DTO.CreditDTO;
import com.yakymets.creditsystem.services.interfaces.CreditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/customer/credits")
public class CreditController {
    private final CreditService creditService;
    Logger logger = LoggerFactory.getLogger(CreditController.class);

    @Autowired
    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CreditDTO> getAll(@RequestParam(required = false, name = "max-sum") BigDecimal maxSum,
                                  @RequestParam(required = false, name = "min-sum") BigDecimal minSum,
                                  @RequestParam(required = false, name = "months-duration") Integer monthsDuration,
                                  @RequestParam(required = false, name = "earning-percentage") Double earningPercentage,
                                  @RequestParam(required = false, name = "earning-percentage-after-deadline") Double earningPercentageAfterDeadline) {
        List<CreditDTO> credits = creditService.getFilteredCredits(maxSum, minSum, monthsDuration, earningPercentage, earningPercentageAfterDeadline);
        logger.info("Got all credits for customer");
        return credits;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CreditDTO get(@PathVariable(value = "id") Long userId) {
        CreditDTO creditById = creditService.getCreditById(userId);
        logger.info("Got credit for customer");
        return creditById;
    }
}
