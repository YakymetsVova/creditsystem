package com.yakymets.creditsystem.services.DTO;

import com.yakymets.creditsystem.persistence.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    private String email;
    private String password;
    private UserRole userRole;
    private String firstName;
    private String lastName;
}
