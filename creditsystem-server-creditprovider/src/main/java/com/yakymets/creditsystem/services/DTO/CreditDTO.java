package com.yakymets.creditsystem.services.DTO;

import com.yakymets.creditsystem.persistence.entities.Credit;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class CreditDTO {
    private Long id;

    private String name;

    private BigDecimal minSum;

    private BigDecimal maxSum;

    private int monthsDuration;

    private double earningPercentage;

    private double earningPercentageAfterDeadline;

    public CreditDTO(Credit credit) {
        this.name = credit.getName();
        this.id = credit.getId();
        this.maxSum = credit.getMaxSum();
        this.minSum = credit.getMinSum();
        this.monthsDuration = credit.getMonthsDuration();
        this.earningPercentage = credit.getEarningPercentage();
        this.earningPercentageAfterDeadline = credit.getEarningPercentageAfterDeadline();
    }

    public CreditDTO() {

    }
}


