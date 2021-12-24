package com.yakymets.creditsystem.services.DTO;

import com.yakymets.creditsystem.persistence.entities.Contract;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ContractDTO {

    private Long id;

    private CustomerDTO customer;

    private CreditDTO credit;

    private BigDecimal payedSum;

    private Date deadline;

    private BigDecimal owedSum;

    private BigDecimal sumToPay;

    public ContractDTO(Contract contract){
        this.id = contract.getId();
        this.customer = new CustomerDTO(contract.getCustomer());
        this.credit = new CreditDTO(contract.getCredit());
        this.payedSum = contract.getPayedSum();
        this.deadline = contract.getDeadline();
        this.owedSum = contract.getOwedSum();
        this.sumToPay = contract.getSumToPay();
    }

    public ContractDTO() {

    }

}
