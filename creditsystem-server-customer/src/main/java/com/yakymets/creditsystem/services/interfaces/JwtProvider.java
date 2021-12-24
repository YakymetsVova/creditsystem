package com.yakymets.creditsystem.services.interfaces;

import com.yakymets.creditsystem.services.DTO.TokenDTO;
import org.springframework.stereotype.Service;

@Service
public interface JwtProvider {
    TokenDTO generateToken(String email);
    boolean isValidToken(String token);
    String getEmailFromToken(String token);
}
