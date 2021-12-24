package com.yakymets.creditsystem.services.interfaces;

import com.yakymets.creditsystem.services.DTO.LoginDTO;
import com.yakymets.creditsystem.services.DTO.RegisterDTO;

public interface UserService {
    LoginDTO findUserByEmail(String email);
    LoginDTO findUserByEmailAndPassword(String email, String password);
    Long saveUser(RegisterDTO user);
}
