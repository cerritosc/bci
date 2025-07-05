package com.smartjob.bci.security.service;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartjob.bci.common.Constants;
import com.smartjob.bci.domain.Phone;
import com.smartjob.bci.domain.Role;
import com.smartjob.bci.domain.User;
import com.smartjob.bci.dto.AuthenticationRequest;
import com.smartjob.bci.dto.AuthenticationResponse;
import com.smartjob.bci.dto.PhoneDTO;
import com.smartjob.bci.dto.RegisterRequest;
import com.smartjob.bci.dto.UserDTO;
import com.smartjob.bci.dto.UserResponse;
import com.smartjob.bci.exception.GlobalException;
import com.smartjob.bci.repository.UserRepository;

@Slf4j
@Service
@Qualifier("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	return userRepository.findByEmail(username)
                .orElseThrow(() -> new GlobalException("Usuario no encontrado con email: " + username));
    }
    
    public UserResponse register(RegisterRequest request) {
    	
    	if (userRepository.existsByEmail(request.getEmail())) {
            throw new GlobalException(Constants.CORREO_EXISTS);
        }
    	
    	String userId = UUID.randomUUID().toString();
    	LocalDate now = LocalDate.now();
    	User user = User.builder()
			        	.id(userId)
			            .name(request.getName())
			            .email(request.getEmail())
			            .password(passwordEncoder.encode(request.getPassword()))
			            .role(Role.USER)
			            .isActive(true)
			            .created(now)
			            .lastLogin(now)
			            .build();
        String token = jwtService.generateToken(user);
        
        List<Phone> phones = Optional.ofNullable(request.getPhones())
        	    .orElse(Collections.emptyList())
        	    .stream()
        	    .map(t -> Phone.builder()
        	        .number(t.getNumber())
        	        .cityCode(t.getCitycode())
        	        .countryCode(t.getCountrycode())
        	        .user(user)
        	        .build())
        	    .collect(Collectors.toList());

        user.setPhones(phones);
        user.setToken(token);
        userRepository.save(user);
        
        return UserResponse.builder()
                .id(userId)
                .created(now)
                .modified(null)
                .lastLogin(now)
                .token(token)
                .isActive(true)
                .build();
    }
    
    public User findByEmail(String username) throws UsernameNotFoundException {
    	return userRepository.findByEmail(username)
                .orElseThrow(() -> new GlobalException("Usuario no encontrado con email: " + username));
    }
    
    public UserResponse updateUser(String email, UserDTO userDTO) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new GlobalException("User not found"));

        LocalDate now = LocalDate.now();
        user.setName(userDTO.getName());
        user.setModified(now);

        userRepository.save(user);
        
        return UserResponse.builder()
                .id(user.getId())
                .created(user.getCreated())
                .modified(now)
                .lastLogin(user.getLastLogin())
                .token(user.getToken())
                .isActive(user.isActive())
                .build();
    }
    
}