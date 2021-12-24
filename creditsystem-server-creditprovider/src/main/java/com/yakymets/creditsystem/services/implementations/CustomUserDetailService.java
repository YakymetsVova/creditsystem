package com.yakymets.creditsystem.services.implementations;

import com.yakymets.creditsystem.services.DTO.CustomUserDetails;
import com.yakymets.creditsystem.services.DTO.LoginDTO;
import com.yakymets.creditsystem.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {


    private final UserService userService;

    @Autowired
    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginDTO loginDTO = userService.findUserByEmail(username);
        return CustomUserDetails.fromUserDTOToCustomUserDetails(loginDTO);
    }
}
