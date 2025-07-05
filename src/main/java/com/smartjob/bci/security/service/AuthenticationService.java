package com.smartjob.bci.security.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smartjob.bci.domain.User;
import com.smartjob.bci.dto.AuthenticationRequest;
import com.smartjob.bci.dto.AuthenticationResponse;
import com.smartjob.bci.exception.GlobalException;
import com.smartjob.bci.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthenticationService {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService jwtService;
    
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new GlobalException("Usuario no encontrado"));

        String jwtToken = jwtService.generateToken(user);
        user.setLastLogin(LocalDate.now());
        userRepository.save(user);
        return new AuthenticationResponse(jwtToken);
    }

}