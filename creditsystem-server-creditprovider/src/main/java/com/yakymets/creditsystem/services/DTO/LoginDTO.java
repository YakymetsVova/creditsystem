package com.yakymets.creditsystem.services.DTO;

import com.yakymets.creditsystem.persistence.entities.User;
import com.yakymets.creditsystem.persistence.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String email;
    private String password;
    private UserRole userRole;

    public LoginDTO() {

    }

    public LoginDTO(User user) {
        this.email = user.getEmail();
        this.password = user.getPasswordHash();
        this.userRole = user.getUserRole();
    }
}


