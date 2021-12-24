package com.yakymets.creditsystem.services.implementations.security;

import com.yakymets.creditsystem.persistence.entities.UserRole;
import com.yakymets.creditsystem.services.DTO.LoginDTO;
import com.yakymets.creditsystem.services.DTO.TokenDTO;
import com.yakymets.creditsystem.services.interfaces.JwtProvider;
import com.yakymets.creditsystem.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class JwtProviderImplTest {
    private UserService userService;
    private JwtProvider jwtProvider;
    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        jwtProvider = new JwtProviderImpl(userService);
    }

    @Test
    void generateToken_whenUserExists_generatesToken() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserRole(UserRole.Customer);
        String email = "customer@gmail.com";

        Mockito.when(userService.findUserByEmail(email)).thenReturn(loginDTO);

        TokenDTO tokenDTO = jwtProvider.generateToken(email);

        assertNotNull(tokenDTO);
        assertNotNull(tokenDTO.getToken());
    }

    @Test
    void isValidToken_whenTokenValid_returnsTrue() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserRole(UserRole.Customer);
        String email = "customer@gmail.com";

        Mockito.when(userService.findUserByEmail(email)).thenReturn(loginDTO);

        String token = jwtProvider.generateToken("customer@gmail.com").getToken();
        boolean isValidToken = jwtProvider.isValidToken(token);

        assertTrue(isValidToken);
    }

    @Test
    void isValidToken_whenTokenInvalid_returnsFalse() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserRole(UserRole.Customer);
        String token = "123";

        boolean isValidToken = jwtProvider.isValidToken(token);

        assertFalse(isValidToken);
    }

    @Test
    void getEmailFromToken() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserRole(UserRole.Customer);
        String email = "customer@gmail.com";

        Mockito.when(userService.findUserByEmail(email)).thenReturn(loginDTO);

        TokenDTO tokenDTO = jwtProvider.generateToken(email);
        String decodedEmail = jwtProvider.getEmailFromToken(tokenDTO.getToken());

        assertEquals(email, decodedEmail);
    }
}