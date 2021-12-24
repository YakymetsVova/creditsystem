package com.yakymets.creditsystem.web.controllers.creditprovider.auth;


import com.yakymets.creditsystem.services.DTO.LoginDTO;
import com.yakymets.creditsystem.services.DTO.RegisterDTO;
import com.yakymets.creditsystem.services.DTO.TokenDTO;
import com.yakymets.creditsystem.services.interfaces.JwtProvider;
import com.yakymets.creditsystem.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/credit-provider/auth")
public class AuthCreditProviderController {

    private final UserService userService;

    private final JwtProvider jwtProvider;

    @Autowired
    public AuthCreditProviderController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginDTO loginDTO) {
        LoginDTO user = userService.findUserByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        return jwtProvider.generateToken(user.getEmail());
    }

    @PostMapping("/register")
    public Long register(@RequestBody RegisterDTO registerDTO) {
        return userService.saveUser(registerDTO);
    }
}
