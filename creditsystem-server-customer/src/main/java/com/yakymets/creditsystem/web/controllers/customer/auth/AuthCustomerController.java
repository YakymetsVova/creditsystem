package com.yakymets.creditsystem.web.controllers.customer.auth;


import com.yakymets.creditsystem.services.DTO.RegisterDTO;
import com.yakymets.creditsystem.services.DTO.TokenDTO;
import com.yakymets.creditsystem.services.DTO.LoginDTO;
import com.yakymets.creditsystem.services.interfaces.JwtProvider;
import com.yakymets.creditsystem.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/auth")
public class AuthCustomerController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(AuthCustomerController.class);
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthCustomerController(UserService userService, JwtProvider jwtProvider){
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginDTO loginDTO) {
        LoginDTO user = userService.findUserByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        logger.info("User " + user.getEmail() + " has been logged");
        return jwtProvider.generateToken(user.getEmail());
    }

    @PostMapping("/register")
    public Long register(@RequestBody RegisterDTO registerDTO) {
        Long savedUserId = userService.saveUser(registerDTO);
        logger.info("New user has been registered");
        return savedUserId;
    }
}
