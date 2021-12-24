package com.yakymets.creditsystem.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Credit extends BaseEntity {

    @Column(name = "credit_name")
    private String name;

    @Column(name = "min_sum", nullable = false)
    private BigDecimal minSum;

    @Column(name = "max_sum", nullable = false)
    private BigDecimal maxSum;

    @Column(name = "months_duration", nullable = false)
    private int monthsDuration;

    @Column(name = "earning_percentage", nullable = false)
    private double earningPercentage;

    @Column(name = "earning_percentage_after_deadline", nullable = false)
    private double earningPercentageAfterDeadline;

    @OneToMany(mappedBy = "credit")
    private List<Contract> contracts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_provider_id")
    private CreditProvider creditProvider;
}
