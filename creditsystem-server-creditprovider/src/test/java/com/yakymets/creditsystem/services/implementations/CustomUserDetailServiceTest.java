package com.yakymets.creditsystem.services.implementations;

import com.yakymets.creditsystem.persistence.entities.UserRole;
import com.yakymets.creditsystem.services.DTO.LoginDTO;
import com.yakymets.creditsystem.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailServiceTest {
    private UserService userService;
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        userDetailsService = new CustomUserDetailService(userService);
    }

    @Test
    void loadUserByUsername_returnsRightUserDetails() {
        String email = "mail@gmail.com";
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserRole(UserRole.Customer);
        loginDTO.setEmail(email);
        loginDTO.setPassword("password");
        Mockito.when(userService.findUserByEmail(email)).thenReturn(loginDTO);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertEquals(loginDTO.getEmail(), userDetails.getUsername());
        assertEquals(loginDTO.getPassword(), userDetails.getPassword());
    }
}