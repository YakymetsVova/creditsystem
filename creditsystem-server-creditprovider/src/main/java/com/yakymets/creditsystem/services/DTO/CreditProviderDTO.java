package com.yakymets.creditsystem.services.DTO;

import com.yakymets.creditsystem.persistence.entities.CreditProvider;
import com.yakymets.creditsystem.persistence.entities.Customer;
import com.yakymets.creditsystem.persistence.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditProviderDTO {
    private long id;

    private UserRole userRole;
    private String passwordHash;
    private String email;

    public CreditProviderDTO(CreditProvider creditProvider) {
        this.id = creditProvider.getId();
        this.email = creditProvider.getEmail();
        this.userRole = creditProvider.getUserRole();
        this.passwordHash = creditProvider.getPasswordHash();
    }

    public CreditProviderDTO() {

    }
}
