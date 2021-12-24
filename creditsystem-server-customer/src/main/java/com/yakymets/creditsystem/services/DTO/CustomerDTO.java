package com.yakymets.creditsystem.services.DTO;


import com.yakymets.creditsystem.persistence.entities.Customer;
import com.yakymets.creditsystem.persistence.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {
    private long id;

    private UserRole userRole;

    private String firstName;

    private String lastName;

    private String email;

    private String passwordHash;


    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.userRole = customer.getUserRole();
        this.passwordHash = customer.getPasswordHash();
    }

    public CustomerDTO() {

    }
}
