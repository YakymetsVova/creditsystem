package com.yakymets.creditsystem.services.implementations;

import com.yakymets.creditsystem.persistence.entities.CreditProvider;
import com.yakymets.creditsystem.persistence.entities.Customer;
import com.yakymets.creditsystem.persistence.entities.UserRole;
import com.yakymets.creditsystem.persistence.repositories.CreditProviderRepository;
import com.yakymets.creditsystem.persistence.repositories.CustomerRepository;
import com.yakymets.creditsystem.services.DTO.LoginDTO;
import com.yakymets.creditsystem.services.DTO.RegisterDTO;
import com.yakymets.creditsystem.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class UserServiceImplTest {

    private CreditProviderRepository mockedCreditProviderRepository;
    private CustomerRepository mockedCustomerRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        mockedCreditProviderRepository = Mockito.mock(CreditProviderRepository.class);
        mockedCustomerRepository = Mockito.mock(CustomerRepository.class);
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserServiceImpl(mockedCreditProviderRepository, mockedCustomerRepository);
    }

    @Test
    void findUserByEmail_creditProviderExists_returnsCreditProvider() {
        CreditProvider creditProvider = new CreditProvider();
        creditProvider.setEmail("1234@gmail.com");
        creditProvider.setUserRole(UserRole.CreditProvider);
        creditProvider.setPasswordHash("123");
        Mockito.when(mockedCreditProviderRepository.findCreditProviderByEmail("1234@gmail.com")).thenReturn(creditProvider);

        LoginDTO userByEmail = userService.findUserByEmail("1234@gmail.com");

        assertEquals(creditProvider.getEmail(), userByEmail.getEmail());
        assertEquals(creditProvider.getUserRole(), userByEmail.getUserRole());
        assertEquals(UserRole.CreditProvider, userByEmail.getUserRole());
    }

    @Test
    void findUserByEmail_customerExists_returnsCustomer() {
        Customer customer = new Customer();
        customer.setEmail("1234@gmail.com");
        customer.setUserRole(UserRole.CreditProvider);
        customer.setPasswordHash("123");
        Mockito.when(mockedCreditProviderRepository.findCreditProviderByEmail("1234@gmail.com")).thenReturn(null);
        Mockito.when(mockedCustomerRepository.findCustomerByEmail("1234@gmail.com")).thenReturn(customer);

        LoginDTO userByEmail = userService.findUserByEmail("1234@gmail.com");

        assertEquals(customer.getEmail(), userByEmail.getEmail());
        assertEquals(customer.getUserRole(), userByEmail.getUserRole());
        assertEquals(UserRole.CreditProvider, userByEmail.getUserRole());
    }

    @Test
    void findUserByEmail_userNotExists_returnsNull() {
        Mockito.when(mockedCreditProviderRepository.findCreditProviderByEmail("1234@gmail.com")).thenReturn(null);
        Mockito.when(mockedCustomerRepository.findCustomerByEmail("1234@gmail.com")).thenReturn(null);

        LoginDTO userByEmail = userService.findUserByEmail("1234@gmail.com");

        assertNull(userByEmail);
    }


    @Test
    void findUserByEmailAndPassword_userExists_returnsUser() {
        CreditProvider creditProvider = new CreditProvider();
        creditProvider.setEmail("1234@gmail.com");
        creditProvider.setUserRole(UserRole.CreditProvider);
        creditProvider.setPasswordHash("$2a$12$aE0NBtoQm2PY3BLbGhj/reYBW.D3W322vau8Z/2teoravQBMRiBsC");
        Mockito.when(mockedCreditProviderRepository.findCreditProviderByEmail("1234@gmail.com")).thenReturn(creditProvider);

        LoginDTO userByEmailAndPassword = userService.findUserByEmailAndPassword("1234@gmail.com", "password");

        assertNotNull(userByEmailAndPassword);
        assertEquals(creditProvider.getEmail(), userByEmailAndPassword.getEmail());
        assertEquals(creditProvider.getUserRole(), userByEmailAndPassword.getUserRole());
    }

    @Test
    void saveUser_returnsSavedCustomerId() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setPassword("password123");
        registerDTO.setUserRole(UserRole.Customer);
        registerDTO.setFirstName("Petro");
        registerDTO.setLastName("Petrenko");
        registerDTO.setEmail("petro@gmail.com");
        Customer customer = new Customer();
        customer.setId(0L);
        Mockito.when(mockedCustomerRepository.save(any(Customer.class))).thenReturn(customer);

        Long id = userService.saveUser(registerDTO);

        assertEquals(0, id);
    }

    @Test
    void saveUser_returnsSavedCreditProviderId() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setPassword("password123");
        registerDTO.setUserRole(UserRole.CreditProvider);
        registerDTO.setEmail("petro@gmail.com");
        CreditProvider creditProvider = new CreditProvider();
        creditProvider.setId(0L);
        Mockito.when(mockedCreditProviderRepository.save(any(CreditProvider.class))).thenReturn(creditProvider);
        Long id = userService.saveUser(registerDTO);
        assertEquals(0, id);
    }
}