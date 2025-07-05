package com.smartjob.bci.service;

import com.smartjob.bci.dto.PhoneDTO;
import com.smartjob.bci.dto.RegisterRequest;
import com.smartjob.bci.dto.UserResponse;
import com.smartjob.bci.domain.User;
import com.smartjob.bci.exception.GlobalException;
import com.smartjob.bci.repository.UserRepository;
import com.smartjob.bci.security.service.JwtService;
import com.smartjob.bci.security.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTests {

    @Mock
    private UserRepository usuarioRepository;

    @InjectMocks
    private UserDetailsServiceImpl usuarioService;

    private RegisterRequest request;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        request = new RegisterRequest();
        request.setEmail("test@correo.com");
        request.setName("Carlos");
        request.setPassword("Contra123!");
    }

    @Test
    void createWithSuccess() {
    	RegisterRequest user = RegisterRequest.builder()
    								.name("Nombre Apellido")
    								.email("nombre.apellido@smartjob.com")
    								.password("ClaveSegura123.")
    								.build();
    	PhoneDTO phone = PhoneDTO.builder()
    						.countrycode("1")
    						.citycode("818")
    						.number("12345678")
    						.build();
    	user.setPhones(Arrays.asList(phone));
        when(usuarioRepository.existsByEmail("nombre.apellido@smartjob.com")).thenReturn(false);
        when(usuarioRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        UserResponse response = usuarioService.register(user);

        assertNotNull(response);
        assertNotNull("test@correo.com", response.getToken());
        verify(usuarioRepository).save(any(User.class));
    }

    @Test
    void createWithEmailNotFound() {
        when(usuarioRepository.existsByEmail("test@correo.com")).thenReturn(true);

        assertThrows(GlobalException.class, () -> usuarioService.register(request));

        verify(usuarioRepository, never()).save(any());
    }
} 