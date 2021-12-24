package com.yakymets.creditsystem.services.implementations;

import com.yakymets.creditsystem.persistence.entities.CreditProvider;
import com.yakymets.creditsystem.persistence.entities.Customer;
import com.yakymets.creditsystem.persistence.entities.User;
import com.yakymets.creditsystem.persistence.entities.UserRole;
import com.yakymets.creditsystem.persistence.repositories.CreditProviderRepository;
import com.yakymets.creditsystem.persistence.repositories.CustomerRepository;
import com.yakymets.creditsystem.services.DTO.LoginDTO;
import com.yakymets.creditsystem.services.DTO.RegisterDTO;
import com.yakymets.creditsystem.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final CreditProviderRepository creditProviderRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(CreditProviderRepository creditProviderRepository, CustomerRepository customerRepository) {
        this.creditProviderRepository = creditProviderRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public LoginDTO findUserByEmail(String email) {
        User user = creditProviderRepository.findCreditProviderByEmail(email);
        if (user == null) {
            user = customerRepository.findCustomerByEmail(email);
            if (user == null) return null;
        }
        return new LoginDTO(user);
    }

    @Override
    public LoginDTO findUserByEmailAndPassword(String email, String password) {
        LoginDTO loginDTO = findUserByEmail(email);
        if (loginDTO != null) {
            if (passwordEncoder.matches(password, loginDTO.getPassword())) {
                return loginDTO;
            }
        }
        return null;
    }

    @Override
    public Long saveUser(RegisterDTO user) {
        if (findUserByEmail(user.getEmail()) != null) {
            return null;
        }
        if (user.getUserRole() == UserRole.Customer) {
            Customer customer = new Customer();
            customer.setUserRole(user.getUserRole());
            customer.setPasswordHash(passwordEncoder.encode(user.getPassword()));
            customer.setEmail(user.getEmail());
            customer.setFirstName(user.getFirstName());
            customer.setLastName(user.getLastName());
            Customer save = customerRepository.save(customer);
            return save.getId();
        }
        CreditProvider creditProvider = new CreditProvider();
        creditProvider.setEmail(user.getEmail());
        creditProvider.setUserRole(user.getUserRole());
        creditProvider.setPasswordHash(passwordEncoder.encode(user.getPassword()));
        CreditProvider saved = creditProviderRepository.save(creditProvider);
        return saved.getId();
    }
}
