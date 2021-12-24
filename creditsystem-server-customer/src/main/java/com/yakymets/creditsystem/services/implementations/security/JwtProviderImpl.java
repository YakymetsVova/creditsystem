package com.yakymets.creditsystem.services.implementations.security;

import com.yakymets.creditsystem.persistence.entities.UserRole;
import com.yakymets.creditsystem.services.DTO.TokenDTO;
import com.yakymets.creditsystem.services.interfaces.JwtProvider;
import com.yakymets.creditsystem.services.interfaces.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtProviderImpl implements JwtProvider {

    private final String jwtSecret = "somesupersecretvalue";
    private final UserService userService;

    @Autowired
    public JwtProviderImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public TokenDTO generateToken(String email) {
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        UserRole userRole = userService.findUserByEmail(email).getUserRole();
        String token = Jwts.builder()
                .setSubject(email)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .claim("userRole", userRole.name())
                .compact();
        return new TokenDTO(token);
    }

    @Override
    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
