package com.yakymets.creditsystem.persistence.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class User extends BaseEntity {
    private String email;
    private String passwordHash;
    private UserRole userRole;
}
