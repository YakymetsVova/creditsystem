package com.yakymets.creditsystem.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Entity
@Getter
@Setter
public class Contract extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "credit_id", nullable = false)
    private Credit credit;

    @Column(name = "payed_sum")
    private BigDecimal payedSum;

    @Column(name = "deadline", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date deadline;

    @Column(name = "owed_sum")
    private BigDecimal owedSum;

    @Column(name = "sum_to_pay")
    @Transient
    private BigDecimal sumToPay;

    @PostLoad
    private void postLoad() {
        this.sumToPay = deadline.before(new Date())
                ? owedSum.divide(new BigDecimal(100), 3, RoundingMode.CEILING)
                .multiply(BigDecimal.valueOf(credit.getEarningPercentageAfterDeadline())).add(owedSum).subtract(payedSum)
                : owedSum.divide(new BigDecimal(100), 3, RoundingMode.CEILING)
                .multiply(BigDecimal.valueOf(credit.getEarningPercentage())).add(owedSum).subtract(payedSum);
    }

}
